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
	void testContainsTextExactMatchWhenMatchExistsButCaseDoesNotMatch() {
		boolean result = this.code.containsTextExactMatch("snippopotami are cool");
		
		assertFalse(result);
	}
	
	@Test
	void testContainsTextExactMatchWhenTextBeingLookedForIsNull() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           code.containsText(null);
	       });
		
		assertEquals(null, exception.getMessage());
	}
}
