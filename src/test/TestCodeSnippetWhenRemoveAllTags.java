package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CodeSnippet;

class TestCodeSnippetWhenRemoveAllTags {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}
	
	@Test
	void testRemoveAllTagsFromCodeSnippetWithNoTags() {
		this.cs.removeAllTags();
		
		assertEquals(0, this.cs.getTags().size());
	}
	
	@Test
	void testRemoveAllTagsFromCodeSnippetWithOneTag() {
		this.cs.addTag("test");
		
		this.cs.removeAllTags();
		
		assertEquals(0, this.cs.getTags().size());
	}
	
	@Test
	void testRemoveAllTagsFromCodeSnippetWithManyTags() {
		this.cs.addTag("test1");
		this.cs.addTag("test2");
		this.cs.addTag("test3");
		
		this.cs.removeAllTags();
		
		assertEquals(0, this.cs.getTags().size());
		
	}

}
