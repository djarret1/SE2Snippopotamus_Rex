package test.model.code;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Code;

class TestWhenCreateCode {

	@Test
	void testCreateCodeWhenCodeTextIsNull() {
		
		assertThrows(NullPointerException.class,
	            ()->{
	            new Code(null);
	            });
	}
	
	@Test
	void testCreateCodeWithValidCodeText() {
		
		assertEquals("Test", new Code("Test").getCodeText());
	}

}
