package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CodeSnippet;

class TestCodeSnippetWhenSetDescription {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testSetDescriptionWithValidDescription() {
		this.cs.setDescription("New test description");
		
		assertEquals("New test description", this.cs.getDescription());
	}
	
	@Test
	void testSetDescriptionWithNullDescription() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           cs.setDescription(null);
	       });
		
		assertEquals("Description was null.", exception.getMessage());
	}

}
