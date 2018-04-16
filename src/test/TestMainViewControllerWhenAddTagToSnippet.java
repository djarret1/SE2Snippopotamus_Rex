package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;

class TestMainViewControllerWhenAddTagToSnippet {

	@Test
	void testAddInvalidTagToSnippet() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(testCodeSnippet1);
		
		assertThrows(IllegalArgumentException.class, () -> controller.addTagToSnippet(testCodeSnippet1, ""));
		
		test.delete();
	}

}
