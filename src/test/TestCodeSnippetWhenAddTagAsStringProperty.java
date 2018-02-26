package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.CodeSnippet;

class TestCodeSnippetWhenAddTagAsStringProperty {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testAddTagToValidCodeSnippet() {
		String test = "Test tag";
		StringProperty tag = new SimpleStringProperty(test);
		this.cs.addTag(test);
		
		assertEquals("Test tag", this.cs.getTags().get(0).getValue());
	}
	
	@Test
	void testAddNullTagToValidCodeSnippet() {
		StringProperty test = null;
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           this.cs.addTag(test);
	       });
		
		assertEquals("The tag was null", exception.getMessage());
	}
	
	@Test
	void testAddTagToCodeSnippetAlreadyContainingTag() {
		String test = "Test tag";
		StringProperty tag = new SimpleStringProperty(test);
		this.cs.addTag(test);
		
		String test2 = "Test tag";
		
		this.cs.addTag(test2);
		
		assertEquals(1, this.cs.getTags().size());
	}
}
