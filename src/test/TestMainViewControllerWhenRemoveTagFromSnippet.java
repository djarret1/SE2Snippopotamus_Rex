package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;

class TestMainViewControllerWhenRemoveTagFromSnippet {

	@Test
	void test() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		
		MainViewController controller = new MainViewController(test.getName());
		controller.addTagToSnippet(testCodeSnippet1, "Test tag 1");
		
		controller.removeTagFromSnippet(testCodeSnippet1, "Test tag 1");
		
		assertEquals(0, testCodeSnippet1.getTags().size());
		
		test.delete();
	}

}
