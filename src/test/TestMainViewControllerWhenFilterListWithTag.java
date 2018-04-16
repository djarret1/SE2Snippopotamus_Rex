package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;
import model.TagIndex;

class TestMainViewControllerWhenFilterListWithTag {

	@Test
	void testFilterListWithTag() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(testCodeSnippet1);
		controller.addTagToSnippet(testCodeSnippet1, "Test tag1");
		controller.addTagToSnippet(testCodeSnippet1, "Test tag2");
		controller.addTagToSnippet(testCodeSnippet1, "Test tag3");
		
		controller.filterListWithTag("Test tag1");
		
		assertEquals(1, controller.getObservableList().size());
		
		test.delete();
	}

}
