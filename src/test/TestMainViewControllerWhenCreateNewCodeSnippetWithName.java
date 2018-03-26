package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;
import model.TagIndex;

class TestMainViewControllerWhenCreateNewCodeSnippetWithName {

	@Test
	void testCreateNewCodeSnippetWithValidName() {
		File test = new File("test.dat");
		
		MainViewController controller = new MainViewController(test.getName());
		
		CodeSnippet newCodeSnippet = controller.createNewCodeSnippetWithName("New code snippet");
		
		assertEquals("New code snippet", newCodeSnippet.getName());
		
		test.delete();
	}
	
	@Test
	void testCreateCodeSnippetWithEmptyName() {
		File test = new File("test.dat");
		
		MainViewController controller = new MainViewController(test.getName());
		
		assertThrows(IllegalArgumentException.class, () -> controller.createNewCodeSnippetWithName(""));
		
		test.delete();
	}
	
	@Test
	void testCreateCodeSnippetWithNullName() {
		File test = new File("test.dat");
		
		MainViewController controller = new MainViewController(test.getName());
		
		assertThrows(NullPointerException.class, () -> controller.createNewCodeSnippetWithName(null));
		
		test.delete();
	}

}
