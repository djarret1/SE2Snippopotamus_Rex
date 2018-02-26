package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.StringProperty;
import model.CodeSnippet;

class TestCodeSnippetWhenGetTags {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testGetTagsOnCodeSnippetWithNoTags() {
		List<StringProperty> result = this.cs.getTags();
		
		assertEquals(0, result.size());
	}
	
	@Test
	void testGetTagsOnCodeSnippetWithOneTag() {
		this.cs.addTag("Test");
		
		assertEquals("Test", this.cs.getTags().get(0).getValue());
	}
	
	@Test
	void testGetTagsOnCodeSnippetWithManyTags() {
		this.cs.addTag("Test1");
		this.cs.addTag("Test2");
		this.cs.addTag("Test3");
		
		assertEquals("Test1", this.cs.getTags().get(0).getValue());
		assertEquals("Test2", this.cs.getTags().get(1).getValue());
		assertEquals("Test3", this.cs.getTags().get(2).getValue());
	}

}
