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
 * @author 	David Jarrett
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
	public static final String COMMAND_DUMP = "dump";
	public static final String NEW_USER = "new_user";
	public static final String COMMAND_TERMINATE = "terminate";
	public static final String COMMAND_UPDATE = "update";

	private ZMQ.Context context;
	private ZMQ.Socket socket;
	private String userName;

	/**
	 * Creates a new Server object that is connected to the default IP and port. Uses admin
	 * as the username.
	 * 
	 * @preconditions: None
	 * @postconditions: The Server object will be connected to the server and ready to accept commands.
	 */
	public Server() {
		this(Server.DEFAULT_IP_PORT, "admin");
	}

	/**
	 * Creates a new Server object connected to the specified ip/port, using the provided username.
	 * 
	 * @preconditions: ipPort != null && userName != null
	 * @postconditions: The Server object will be connected to the server and ready to accept commands.
	 * 
	 * @param ipPort The ip/port combination to use.
	 * @param userName The username of the person logging in to the server.
	 */
	public Server(String ipPort, String userName) {
		Objects.requireNonNull(ipPort, "The ipPort cannot be null.");
		Objects.requireNonNull(userName, "The username cannot be null.");
		this.context = ZMQ.context(1);
		socket = context.socket(ZMQ.REQ);
		socket.connect(ipPort);
		this.userName = userName;
	}

	/**
	 * Returns a list containing all CodeSnippets on the server.
	 * 
	 * @preconditions: None
	 * 
	 * @return A list containing all CodeSnippets on the server.
	 */
	public List<CodeSnippet> getAllSnippetsFromServer() {
		Map<String, String> message = new HashMap<>();
		message.put(MSG_ID, COMMAND_DUMP);
		message.put(MSG_USER_NAME, this.userName);

		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message, HashMap.class);

		this.socket.send(jsonMessage.getBytes(), 0);
		byte[] reply = this.socket.recv(0);

		Map<String, String> responseMap = gson.fromJson(new String(reply), HashMap.class);
		String data = responseMap.get(RESPONSE);

		return this.parseServerSnippetObjects(data);
	}

	private List<CodeSnippet> parseServerSnippetObjects(String data) {
		List<CodeSnippet> snippets = new ArrayList<>();
		String[] snippetRecords = data.split("\n");

		for (String record : snippetRecords) {
			String[] components = record.split(",");
			//String userName = components[0];
			String snippetName = components[1];
			String description = components[2];
			String code = components[3];
			List<StringProperty> tags = new ArrayList<>();
			for (int i = 4; i < components.length; i++) {
				tags.add(new SimpleStringProperty(components[i]));
			}
			CodeSnippet newSnippet = new CodeSnippet(snippetName, description, code, tags);
			snippets.add(newSnippet);
		}

		return snippets;
	}
}
