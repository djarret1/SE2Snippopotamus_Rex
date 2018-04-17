package test;

import static org.junit.jupiter.api.Assertions.*;

import model.Server;

import org.junit.jupiter.api.Test;

class TestServerWhenCreateServer {

	@Test
	void testCreateServerDefaultConstructor() {
		Server server = new Server();
		
		server.deactivateServer();
	}

}
