package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Code;

class TestWhenCreateCode {

	@Test
	void testCreateCodeWhenCodeTextIsNull() {
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           new Code(null);
	       });
		
		assertEquals("Codetext was null.", exception.getMessage());
	}
		
	
	@Test
	void testCreateCodeWithValidCodeText() {
		String result = new Code("Test").getCodeText();

		assertEquals("Test", result);
	}

}
