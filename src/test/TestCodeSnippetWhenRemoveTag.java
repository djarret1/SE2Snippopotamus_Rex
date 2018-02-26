package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CodeSnippet;

class TestCodeSnippetWhenRemoveTag {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
		this.cs.addTag("test");
	}

	@Test
	void testRemoveTagFromValidCodeSnippet() {
		String test = "test";
		
		this.cs.removeTag(test);
		
		assertEquals(0, this.cs.getTags().size());
	}
	
	@Test
	void testRemoveTagFromCodeSnippetThatDoesNotContainTag() {
		String test = "Not this tag";
		
		this.cs.removeTag(test);
		
		assertFalse(test.equals(this.cs.getTags().get(0)));
	}

}
