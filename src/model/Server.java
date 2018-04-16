package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zeromq.ZMQ;

import com.google.gson.Gson;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

	public Server() {
		this(Server.DEFAULT_IP_PORT, "admin");
	}

	public Server(String ipPort, String userName) {
		this.context = ZMQ.context(1);
		socket = context.socket(ZMQ.REQ);
		socket.connect(ipPort);
		this.userName = userName;
	}

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
