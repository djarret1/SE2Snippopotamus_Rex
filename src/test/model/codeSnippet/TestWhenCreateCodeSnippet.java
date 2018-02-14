package test.model.codeSnippet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Code;
import model.CodeSnippet;

class TestWhenCreateCodeSnippet {

	@Test
	void testCreateCodeSnippetWhenNameIsNull() {
		
		assertThrows(NullPointerException.class,
	            ()->{
	            new CodeSnippet(null, "Test description", "Test codeText");
	            }, "Name was null");	}
	
	@Test
	void testCreateCodeSnippetWhenDescriptionIsNull() {
		
		assertThrows(NullPointerException.class,
	            ()->{
	            new CodeSnippet("Test name", null, "Test codeText");
	            }, "Description was null");	}
	
	@Test
	void testCreateCodeSnippetWhenCodeTextIsNull() {
		
		assertThrows(NullPointerException.class,
	            ()->{
	            new CodeSnippet("Test name", "Test description", null);
	            }, "Codetext was null");	}
	
	@Test
	void testCreateValidCodeSnippet() {
		CodeSnippet snippet = new CodeSnippet("Test name", "Test description", "Test codeText");
		
		assertEquals("Test name", snippet.getName());
		assertEquals("Test description", snippet.getDescription());
		assertEquals("Test codeText", snippet.getCode().getCodeText());
	}

}
