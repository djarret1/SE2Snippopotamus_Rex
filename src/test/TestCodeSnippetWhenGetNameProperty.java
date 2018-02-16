package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.StringProperty;
import model.CodeSnippet;

class TestCodeSnippetWhenGetNameProperty {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testGetNameProperty() {
		StringProperty sp = this.cs.getNameProperty();
		
		String result = sp.getValue();
		
		assertEquals("Test name", result);
	}
}
