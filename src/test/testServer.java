package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.CodeSnippet;
import model.Server;

class testServer {

	@Test
	void testGetAllSnippetsFromServerWhenServerIsEmpty() {
		Server server = new Server();
		assertEquals(true, server.deleteAllSnippets());
		server = new Server();
		List<CodeSnippet> snippets = server.getAllSnippetsFromServer();
		assertEquals(0, snippets.size());
	}

	@Test
	void testStoreAndRetrieveSnippet() {
		Server server = new Server();
		server.deleteAllSnippets();

		CodeSnippet testSnippet = new CodeSnippet("admin", "Test Snippet", "Desc");
		server = new Server();
		server.addSnippet(testSnippet);

		server = new Server();
		List<CodeSnippet> results = server.getAllSnippetsFromServer();
		boolean wasFound = false;
		for (CodeSnippet snippet : results) {
			if (snippet.getName().equals(testSnippet.getName())) {
				wasFound = true;
			}
		}

		assertEquals(true, wasFound);
	}
	
	@Test
	void testUpdateSnippet() {
		Server server = new Server();
		server.deleteAllSnippets();

		CodeSnippet testSnippet = new CodeSnippet("admin", "Test Snippet", "Desc");
		server = new Server();
		server.addSnippet(testSnippet);

		server = new Server();
		testSnippet = new CodeSnippet("admin", "Test Snippet", "Longer Description");
		server.updateSnippet(testSnippet);
		
		server = new Server();
		List<CodeSnippet> results = server.getAllSnippetsFromServer();
		boolean wasFound = false;
		for (CodeSnippet snippet : results) {
			if (snippet.getName().equals(testSnippet.getName()) && snippet.getDescription().equals(testSnippet.getDescription())) {
				wasFound = true;
			}
		}

		assertEquals(true, wasFound);
	}

	@Test
	void testDeleteSnippet() {
		Server server = new Server();
		server.deleteAllSnippets();

		CodeSnippet testSnippet = new CodeSnippet("admin", "Test Snippet", "Desc");
		server = new Server();
		server.addSnippet(testSnippet);

		server = new Server();
		server.deleteSnippet(testSnippet);

		server = new Server();
		List<CodeSnippet> results = server.getAllSnippetsFromServer();
		assertEquals(0, results.size());
	}

	@Test
	void testDeactivateServer() {
		Server server = new Server();
		List<CodeSnippet> results = server.getAllSnippetsFromServer();
		
		assertEquals(true, server.deleteAllSnippets());

//		Throwable exception = assertThrows(IllegalStateException.class, () -> {
//			server.deleteAllSnippets();
//		});
//
//		assertEquals("The server is no longer active.", exception.getMessage());
	}

}
