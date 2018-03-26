package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;

class TestMainViewControllerWhenRemoveCodeSnippet {

	@Test
	void testRemoveCodeSnippet() {
		File test = new File("test.dat");
		CodeSnippet cs = new CodeSnippet("Test1", "Test2", "Test3");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(cs);
		
		assertEquals(2, controller.getObservableList().size());
		
		controller.removeCodeSnippet(cs);
		
		assertEquals(1, controller.getObservableList().size());
		
		test.delete();	
		}
	
	@Test
	void testRemoveCodeSnippetWithSnippetNotStored() {
		File test = new File("test.dat");
		CodeSnippet cs = new CodeSnippet("Test1", "Test2", "Test3");
		CodeSnippet notStored = new CodeSnippet("Not stored", "Not stored", "Not stored");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(cs);
		
		assertEquals(2, controller.getObservableList().size());
		
		controller.removeCodeSnippet(notStored);
		
		assertEquals(2, controller.getObservableList().size());
		
		test.delete();	
	}

}
