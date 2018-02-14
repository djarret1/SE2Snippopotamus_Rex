package test.model.code;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Code;

class TestWhenSetCodeText {

	@Test
	void testSetCodeTextToNull() {
		Code code = new Code("Test");
		
		assertThrows(NullPointerException.class,
	            ()->{
	            code.setCodeText(null);
	            }, "Codetext was null");
	}
	
	@Test
	void testSetValidCodeText() {
		Code code = new Code("Test");
		
		code.setCodeText("New test");
		
		assertEquals("New test", code.getCodeText());
	}

}
