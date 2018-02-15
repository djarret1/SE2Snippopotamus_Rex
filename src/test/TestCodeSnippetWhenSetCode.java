package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Code;
import model.CodeSnippet;

class TestCodeSnippetWhenSetCode {
private CodeSnippet cs;
private Code code;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
		this.code = new Code("Test codeText");
	}

	@Test
	void testSetCodeWithValidCode() {
		this.cs.setCode(this.code);
		
		String result = this.cs.getCode().getCodeText();
		
		assertEquals("Test codeText", result);
	}
	
	@Test
	void testSetDescriptionWithNullDescription() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           cs.setCode(null);
	       });
		
		assertEquals("The code was null.", exception.getMessage());
	}
}
