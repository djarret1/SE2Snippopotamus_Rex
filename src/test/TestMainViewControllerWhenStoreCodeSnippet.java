package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;

class TestMainViewControllerWhenStoreCodeSnippet {

	@Test
	void testStoreCodeSnippet() {
		File test = new File("test.dat");
		CodeSnippet cs = new CodeSnippet("Test1", "Test2", "Test3");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(cs);
		
		assertEquals("Test1", controller.getObservableList().get(1).getName());
		
		test.delete();
	}
	
	@Test
	void testStoreNullCodeSnippet() {
		File test = new File("test.dat");
		
		
		MainViewController controller = new MainViewController(test.getName());
		
		assertThrows(NullPointerException.class, () -> controller.storeCodeSnippet(null));
		
		test.delete();
	}
	
	@Test
	void testStoreNullCodeSnippetWithDuplicateSnippet() {
		File test = new File("test.dat");
		CodeSnippet cs = new CodeSnippet("Test1", "Test2", "Test3");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(cs);
		controller.storeCodeSnippet(cs);
		
		assertEquals("Test1", controller.getObservableList().get(1).getName());
		assertEquals(2, controller.getObservableList().size());
		
		test.delete();
	}

}
