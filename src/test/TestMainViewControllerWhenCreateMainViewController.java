package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;

class TestMainViewControllerWhenCreateMainViewController {

	@Test
	void testCreateMainViewControllerWithValidData() {
		MainViewController controller = new MainViewController("populatedDummyData.dat");
		
		assertEquals(3, controller.getObservableList().size());
	}
	
	@Test
	void testCreateMainViewControllerWithNullData() {
		
		assertThrows(NullPointerException.class, () -> new MainViewController(null));
	}
}
