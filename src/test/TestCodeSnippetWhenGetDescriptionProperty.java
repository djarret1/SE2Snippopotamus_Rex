package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.StringProperty;
import model.CodeSnippet;

class TestCodeSnippetWhenGetDescriptionProperty {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testGetDescriptionProperty() {
		StringProperty sp = this.cs.getDescriptionProperty();
		
		String result = sp.getValue();
		
		assertEquals("Test description", result);
	}
}
