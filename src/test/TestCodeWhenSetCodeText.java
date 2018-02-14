package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Code;

class TestWhenSetCodeText {

	@Test
	void testSetCodeTextToNull() {
		Code code = new Code("Test");
		
		Throwable exception = assertThrows(NullPointerException.class, () -> {
	           code.setCodeText(null);
	       });
		
		assertEquals("Codetext was null", exception.getMessage());
	}
	
	@Test
	void testSetValidCodeText() {
		Code code = new Code("Test");
		
		code.setCodeText("New test");
		
		assertEquals("New test", code.getCodeText());
	}

}
