package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.zeromq.ZMQ;

import com.google.gson.Gson;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an interaction with the server.
 * 
 * @author David Jarrett
 * @version 04/16/2018
 */
public class Server {

	public static final String DEFAULT_IP_PORT = "tcp://localhost:5555";

	public static final String MSG_ID = "id";
	public static final String MSG_USER_NAME = "user_name";
	public static final String MSG_NAME = "name";
	public static final String MSG_DESC = "description";
	public static final String MSG_CODE = "code";
	public static final String MSG_TAGS = "tags";

	public static final String RESPONSE = "resp";
	public static final String SUCCESS = "success";

	public static final String COMMAND_ADD = "add";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_DELETE_ALL = "delete_all";
	public static final String COMMAND_DUMP = "dump";
	public static final String NEW_USER = "new_user";
	public static final String DELETE_USER = "delete_user";
	public static final String COMMAND_TERMINATE = "terminate";
	public static final String COMMAND_UPDATE = "update";

	private boolean isActive;

	private ZMQ.Context context;
	private ZMQ.Socket socket;
	private String userName;

	/**
	 * Creates a new Server object that is connected to the default IP and port.
	 * Uses admin as the username.
	 * 
	 * @preconditions: None
	 * @postconditions: The Server object will be connected to the server and ready
	 *                  to accept commands.
	 */
	public Server() {
		this(Server.DEFAULT_IP_PORT, "admin");
	}

	/**
	 * Creates a new Server object connected to the specified ip/port, using the
	 * provided username.
	 * 
	 * @preconditions: ipPort != null && userName != null
	 * @postconditions: The Server object will be connected to the server and ready
	 *                  to accept commands.
	 * 
	 * @param ipPort
	 *            The ip/port combination to use.
	 * @param userName
	 *            The username of the person logging in to the server.
	 */
	public Server(String ipPort, String userName) {
		Objects.requireNonNull(ipPort, "The ipPort cannot be null.");
		Objects.requireNonNull(userName, "The username cannot be null.");
		this.context = ZMQ.context(1);
		socket = context.socket(ZMQ.REQ);
		socket.setSendTimeOut(5000);
		socket.setReceiveTimeOut(5000);
		socket.connect(ipPort);
		this.userName = userName;
		this.isActive = true;
	}

	/**
	 * Returns a list containing all CodeSnippets on the server.
	 * 
	 * @preconditions: The server must be active.
	 * 
	 * @return A list containing all CodeSnippets on the server.
	 */
	@SuppressWarnings("unchecked")
	public List<CodeSnippet> getAllSnippetsFromServer() {
		if (!this.isActive) {
			throw new IllegalStateException("The server is no longer active.");
		}

		Map<String, String> message = new HashMap<>();
		message.put(MSG_ID, COMMAND_DUMP);
		message.put(MSG_USER_NAME, this.userName);

		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message, HashMap.class);

		this.socket.send(jsonMessage.getBytes(), 0);
		byte[] reply = this.socket.recv(0);

		if (reply == null) {
			return new ArrayList<>();
		}

		Map<String, String> responseMap = gson.fromJson(new String(reply), HashMap.class);
		String data = responseMap.get(RESPONSE);

		if (data.contains("[Errno") || data.equals("") || data.equals("invalid_user")) {
			return new ArrayList<>();
		}

		this.deactivateServer();
		return this.parseServerSnippetObjects(data);
	}

	private List<CodeSnippet> parseServerSnippetObjects(String data) {
		final int SNIPPET_NAME_INDEX = 1;
		final int DESCRIPTION_INDEX = 2;
		final int CODE_INDEX = 3;
		final int TAG_INDEX = 4;
		List<CodeSnippet> snippets = new ArrayList<>();
		String[] snippetRecords = data.split("\n");

		for (String record : snippetRecords) {
			String[] components = record.split(",");
			String snippetName = components[SNIPPET_NAME_INDEX];
			String description = components[DESCRIPTION_INDEX];
			String code = components[CODE_INDEX];
			List<StringProperty> tags = new ArrayList<>();
			for (int i = TAG_INDEX; i < components.length; i++) {
				tags.add(new SimpleStringProperty(components[i]));
			}
			CodeSnippet newSnippet = new CodeSnippet(snippetName, description, code, tags);
			snippets.add(newSnippet);
		}

		return snippets;
	}

	/**
	 * Updates an existing CodeSnippet on the server. The request fails if the
	 * snippet doesn't exist.
	 * 
	 * @preconditions: snippet != null && the server is active
	 * 
	 * @param snippet
	 *            The CodeSnippet to update on the server.
	 * @return true if the update was successful, false otherwise.
	 */
	public boolean updateSnippet(CodeSnippet snippet) {
		return this.snippetCommand(snippet, COMMAND_UPDATE);
	}

	/**
	 * Adds a new CodeSnippet to the server.
	 * 
	 * @preconditions: snippet != null
	 * 
	 * @param snippet
	 *            The CodeSnippet to add to the server.
	 * @return true if the add was successful, false otherwise.
	 */
	public boolean addSnippet(CodeSnippet snippet) {
		return this.snippetCommand(snippet, COMMAND_ADD);
	}

	/**
	 * Deletes a CodeSnippet from the server.
	 * 
	 * @preconditions: snippet != null
	 * 
	 * @param snippet
	 *            The CodeSnippet to remove from the server.
	 * @return true if the delete was successful, false otherwise.
	 */
	public boolean deleteSnippet(CodeSnippet snippet) {
		return this.snippetCommand(snippet, COMMAND_DELETE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean snippetCommand(CodeSnippet snippet, String command) {
		if (!this.isActive) {
			throw new IllegalStateException("The server is no longer active.");
		}

		Objects.requireNonNull(snippet, "The CodeSnippet cannot be null.");
		Objects.requireNonNull(command, "The command cannot be null.");

		Map message = new HashMap();
		message.put(MSG_ID, command);
		message.put(MSG_USER_NAME, this.userName);
		message.put(MSG_NAME, snippet.getName());
		message.put(MSG_DESC, snippet.getDescription());
		message.put(MSG_CODE, snippet.getCode().getCodeText());

		List<StringProperty> tags = snippet.getTags();
		List<String> tagStrings = new ArrayList<>();
		for (StringProperty tagProperty : tags) {
			tagStrings.add(tagProperty.get());
		}

		message.put(MSG_TAGS, tagStrings);

		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message, HashMap.class);

		this.socket.send(jsonMessage.getBytes(), 0);
		byte[] reply = this.socket.recv(0);

		if (reply == null) {
			this.deactivateServer();
			return false;
		}

		Map<String, String> responseMap = gson.fromJson(new String(reply), HashMap.class);
		String response = responseMap.get(RESPONSE);

		this.deactivateServer();
		return response.equals(SUCCESS);
	}

	/**
	 * Deletes all CodeSnippets from the server.
	 * 
	 * @preconditions: None
	 * 
	 * @return true if the delete was successful, false otherwise.
	 */
	public boolean deleteAllSnippets() {
		if (!this.isActive) {
			throw new IllegalStateException("The server is no longer active.");
		}

		Map<String, String> message = new HashMap<>();
		message.put(MSG_ID, COMMAND_DELETE_ALL);
		message.put(MSG_USER_NAME, this.userName);

		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message, HashMap.class);

		this.socket.send(jsonMessage.getBytes(), 0);
		byte[] reply = this.socket.recv(0);

		if (reply == null) {
			return false;
		}

		Map<String, String> responseMap = gson.fromJson(new String(reply), HashMap.class);
		String data = responseMap.get(RESPONSE);

		this.deactivateServer();
		return true;
	}

	/**
	 * Adds a new user to the server.
	 * 
	 * @preconditions: username != null
	 * 
	 * @return true if the add was successful, false otherwise.
	 */
	public boolean addNewUser(String username) {
		Objects.requireNonNull(username, "The username cannot be null.");

		Map<String, String> message = new HashMap<>();
		message.put(MSG_ID, NEW_USER);
		message.put(MSG_USER_NAME, username);

		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message, HashMap.class);

		this.socket.send(jsonMessage.getBytes(), 0);
		byte[] reply = this.socket.recv(0);

		if (reply == null) {
			return false;
		}

		Map<String, String> responseMap = gson.fromJson(new String(reply), HashMap.class);
		String data = responseMap.get(RESPONSE);

		this.deactivateServer();
		return true;
	}

	/**
	 * Deletes a user from the server.
	 * 
	 * @preconditions: username != null
	 * 
	 * @return true if the add was successful, false otherwise.
	 */
	public boolean deleteUser(String username) {
		Objects.requireNonNull(username, "The username cannot be null.");

		Map<String, String> message = new HashMap<>();
		message.put(MSG_ID, DELETE_USER);
		message.put(MSG_USER_NAME, username);

		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message, HashMap.class);

		this.socket.send(jsonMessage.getBytes(), 0);
		byte[] reply = this.socket.recv(0);

		if (reply == null) {
			return false;
		}

		Map<String, String> responseMap = gson.fromJson(new String(reply), HashMap.class);
		String data = responseMap.get(RESPONSE);

		this.deactivateServer();
		return true;
	}

	/**
	 * Terminates the server.
	 * 
	 * @preconditions: None
	 * @postconditions: Server will be terminated.
	 */
	public void terminateServer() {
		Map<String, String> message = new HashMap<>();
		message.put(MSG_ID, COMMAND_TERMINATE);
		message.put(MSG_USER_NAME, "admin");

		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message, HashMap.class);

		this.socket.send(jsonMessage.getBytes(), 0);
		byte[] reply = this.socket.recv(0);

		return;
	}

	/**
	 * Closes the connection to the remote server and makes further transactions
	 * impossible. Used to enforce a single transaction per Server object policy.
	 * 
	 * @preconditions: None
	 * @postconditions: The server will no longer be active.
	 */
	public void deactivateServer() {
		if (this.isActive) {
			this.socket.close();
			this.context.term();
			this.isActive = false;
		}
	}

}
