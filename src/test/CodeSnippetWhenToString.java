package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CodeSnippet;

class CodeSnippetWhenToString {
	private CodeSnippet cs;
	
	@BeforeEach
	void setUp() throws Exception {
		this.cs = new CodeSnippet("Test name", "Test description", "Test codeText");
	}

	@Test
	void testToStringOnValidCodeSnippet() {
		String result = this.cs.toString();
		
		assertEquals(result, "Test name");
	}
}
