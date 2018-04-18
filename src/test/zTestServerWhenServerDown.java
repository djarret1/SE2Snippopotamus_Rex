package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.CodeSnippet;
import model.Server;

class zTestServerWhenServerDown {

	@Test
	void testTerminateServer() {
		Server server = new Server();
		server.terminateServer();

		server = new Server();
		List<CodeSnippet> results = server.getAllSnippetsFromServer();

		assertEquals(0, results.size());
	}

}
