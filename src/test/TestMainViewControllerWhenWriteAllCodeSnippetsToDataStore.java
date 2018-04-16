package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import controller.MainViewController;

class TestMainViewControllerWhenWriteAllCodeSnippetsToDataStore {

	@Test
	void testWriteAllCodeSnippetsToDataStore() {
		MainViewController controller = new MainViewController("populatedDummyData.dat");
		
		controller.writeAllCodeSnippetsToDataStore();
		
		assertEquals(3, controller.getObservableList().size());
	}

}
