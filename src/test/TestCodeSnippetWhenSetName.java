package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CodeSnippet;

class TestCodeSnippetWhenSetName {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testSetNameWithValidName() {
		this.cs.setName("New test name");
		
		assertEquals("New test name", this.cs.getName());
	}
	
	@Test
	void testSetNameWithNullName() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           cs.setName(null);
	       });
		
		assertEquals("Name was null.", exception.getMessage());
	}
}
