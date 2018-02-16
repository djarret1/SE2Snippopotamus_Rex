package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import model.Code;
import model.CodeSnippet;

class TestCodeSnippetWhenGetCodeProperty {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testGetDescriptionProperty() {
		ObjectProperty<Code> op = this.cs.getCodeProperty();
		
		String result = op.getValue().getCodeText();
		
		assertEquals("Test codeText", result);
	}
}
