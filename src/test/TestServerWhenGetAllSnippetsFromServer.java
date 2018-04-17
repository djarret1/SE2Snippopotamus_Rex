package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.CodeSnippet;
import model.Server;

class TestServerWhenGetAllSnippetsFromServer {

	@Test
	void testServerWhenGetAllSnippetsFromServerWithNoSnippets() {
		Server server = new Server();
		
		assertEquals(0, server.getAllSnippetsFromServer().size());
	}
	
	@Test
	void testServerWhenGetAllSnippetsFromDeactivatedServer() {
		Server server = new Server();
		
		server.deactivateServer();
		
		assertThrows(IllegalStateException.class, () -> {
	           server.getAllSnippetsFromServer();
	       });
	}
}
