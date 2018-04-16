package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;
import model.TagIndex;

class TestMainViewControllerWhenGetAllExistingTags {

	@Test
	void testGetAllExistingTags() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(testCodeSnippet1);
		controller.addTagToSnippet(testCodeSnippet1, "Test tag1");
		controller.addTagToSnippet(testCodeSnippet1, "Test tag2");
		controller.addTagToSnippet(testCodeSnippet1, "Test tag3");
		
		assertEquals(3, controller.getAllExistingTags().size());
		
		test.delete();	
		}

}
