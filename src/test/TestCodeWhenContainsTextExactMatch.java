package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Code;

class TestCodeWhenContainsTextExactMatch {
	private Code code;
	
	@BeforeEach
	void setUp() throws Exception {
		this.code = new Code("Snippopotami are cool");
	}

	@Test
	void testContainsTextExactMatchWhenExactMatchExists() {
		boolean result = this.code.containsTextExactMatch("Snippopotami are cool");
		
		assertTrue(result);
	}
	
	@Test
	void testContainsTextExactMatchWhenExactMatchDoesNotExist() {
		boolean result = this.code.containsTextExactMatch("Snippopotamus are cool");
		
		assertFalse(result);
	}
	
	@Test
	void testContainsTextThatIsSubstringOfCodeText() {
		boolean result = this.code.containsTextExactMatch("are cool");
		
		assertTrue(result);
	}
	
	@Test
	void testContainsTextThatIsSubstringOfCodeTextButNotCompleteWord() {
		boolean result = this.code.containsTextExactMatch("are coo");
		
		assertFalse(result);
	}
	
	@Test
	void testContainsTextExactMatchWhenMatchExistsButCaseDoesNotMatch() {
		boolean result = this.code.containsTextExactMatch("snippopotami are cool");
		
		assertFalse(result);
	}
	
	@Test
	void testContainsExactMatchAtBeginningOfText() {
		boolean result = this.code.containsTextExactMatch("Snippopotami");
		
		assertTrue(result);
	}
	
	@Test
	void testContainsTextExactMatchWhenTextBeingLookedForIsNull() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           code.containsTextExactMatch(null);
	       });
		
		assertEquals("Query text was null.", exception.getMessage());
	}
}
