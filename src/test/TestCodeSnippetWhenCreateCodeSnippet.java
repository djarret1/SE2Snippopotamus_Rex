package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Code;
import model.CodeSnippet;

class TestWhenCreateCodeSnippet {

	@Test
	void testCreateCodeSnippetWhenNameIsNull() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           new CodeSnippet(null, "Test description", "Test codeText");
	       });
		
		assertEquals("Name was null.", exception.getMessage());
	}
	
	@Test
	void testCreateCodeSnippetWhenDescriptionIsNull() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           new CodeSnippet("Test name", null, "Test codeText");
	       });
		
		assertEquals("Description was null.", exception.getMessage());
	}
	
	@Test
	void testCreateCodeSnippetWhenCodeTextIsNull() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           new CodeSnippet("Test name", "Test description", null);
	       });
		
		assertEquals("Code text was null.", exception.getMessage());
	}
	
	@Test
	void testCreateValidCodeSnippet() {
		CodeSnippet snippet = new CodeSnippet("Test name", "Test description", "Test codeText");
		
		assertAll("snippet",
	            () -> assertEquals("Test name", snippet.getName()),
	            () -> assertEquals("Test description", snippet.getDescription()),
	            () -> assertEquals("Test codeText", snippet.getCode().getCodeText())
	        );
	}

}
