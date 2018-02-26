package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CodeSnippet;

class TestCodeSnippetWhenAddTag {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testAddTagToValidCodeSnippet() {
		String test = "Test tag";
		this.cs.addTag(test);
		
		assertEquals("Test tag", this.cs.getTags().get(0).getValue());
	}
	
	@Test
	void testAddNullTagToValidCodeSnippet() {
		String test = null;
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           this.cs.addTag(test);
	       });
		
		assertEquals("The tag was null", exception.getMessage());
	}
	
	@Test
	void testAddTagToCodeSnippetAlreadyContainingTag() {
		String test = "Test tag";
		this.cs.addTag(test);
		
		String test2 = "Test tag";
		
		this.cs.addTag(test2);
		
		assertEquals(1, this.cs.getTags().size());
	}
}
