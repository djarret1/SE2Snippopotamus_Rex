package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Code;

class TestCodeWhenContainsCode {
	private Code code;
	@BeforeEach
	void setUp() throws Exception {
		this.code = new Code("Snippopotami are cool");
	}

	@Test
	void testContainsTextWhenMatchingTextIsFirstWord() {
		 boolean result = this.code.containsText("Snippopotami");
		 
		 assertTrue(result);
	}
	
	@Test
	void testContainsTextWhenMatchingTextIsMiddleWord() {
		boolean result = this.code.containsText("are");
		
		assertTrue(result);
	}
	
	@Test
	void testContainsTextWhenMatchingTextIsLastWord() {
		boolean result = this.code.containsText("cool");
		
		assertTrue(result);
	}
	
	@Test
	void testContainsTextWhenNoMatchingText() {
		boolean result = this.code.containsText("Giraffe");
		
		assertFalse(result);
	}
	
	@Test
	void testContainsTextWhenTextBeingLookedForIsNull() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           code.containsText(null);
	       });
		
		assertEquals(null, exception.getMessage());
	}
	
	

}
