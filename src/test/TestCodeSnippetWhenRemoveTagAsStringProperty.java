package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.CodeSnippet;

class TestCodeSnippetWhenRemoveTagAsStringProperty {
private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
		
	}
	
	@Test
	void testRemoveTagFromValidCodeSnippet() {
		String test = "Test tag";
		StringProperty tag = new SimpleStringProperty(test);
		this.cs.addTag(tag);
		
		this.cs.removeTag(tag);
		
		assertEquals(0, this.cs.getTags().size());
	}
	
	@Test
	void testRemoveTagFromCodeSnippetThatDoesNotContainTag() {
		String test = "Tag";
		StringProperty tag = new SimpleStringProperty(test);
		this.cs.addTag(tag);
		
		String test2 = "Not this one";
		StringProperty notThisOne = new SimpleStringProperty(test2);
		
		
		this.cs.removeTag(notThisOne);
		
		assertEquals(1, this.cs.getTags().size());
		assertEquals("Tag", this.cs.getTags().get(0).getValue());
	}
	


}
