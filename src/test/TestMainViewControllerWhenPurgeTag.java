package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;

class TestMainViewControllerWhenPurgeTag {

	@Test
	void testPurgeTagOnValidTag() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		CodeSnippet testCodeSnippet2 = new CodeSnippet("TestName2", "TestDescription2", "TestCodeText2");
		CodeSnippet testCodeSnippet3 = new CodeSnippet("TestName3", "TestDescription3", "TestCodeText3");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(testCodeSnippet1);
		controller.storeCodeSnippet(testCodeSnippet2);
		controller.storeCodeSnippet(testCodeSnippet3);
		controller.addTagToSnippet(testCodeSnippet1, "Tag1");
		controller.addTagToSnippet(testCodeSnippet2, "Tag1");
		controller.addTagToSnippet(testCodeSnippet3, "Tag1");
		controller.addTagToSnippet(testCodeSnippet1, "Tag2");
		controller.addTagToSnippet(testCodeSnippet2, "Tag2");
		controller.addTagToSnippet(testCodeSnippet3, "Tag2");
		
		controller.purgeTag("Tag1");
		
		assertEquals("Tag2", controller.getAllExistingTags().get(0));
		
		test.delete();
	}
	
	@Test
	void testPurgeTagOnTagNotContained() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		CodeSnippet testCodeSnippet2 = new CodeSnippet("TestName2", "TestDescription2", "TestCodeText2");
		CodeSnippet testCodeSnippet3 = new CodeSnippet("TestName3", "TestDescription3", "TestCodeText3");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(testCodeSnippet1);
		controller.storeCodeSnippet(testCodeSnippet2);
		controller.storeCodeSnippet(testCodeSnippet3);
		controller.addTagToSnippet(testCodeSnippet1, "Tag1");
		controller.addTagToSnippet(testCodeSnippet2, "Tag1");
		controller.addTagToSnippet(testCodeSnippet3, "Tag1");
		controller.addTagToSnippet(testCodeSnippet1, "Tag2");
		controller.addTagToSnippet(testCodeSnippet2, "Tag2");
		controller.addTagToSnippet(testCodeSnippet3, "Tag2");
		
		controller.purgeTag("Tag3");
		
		assertEquals(2, controller.getAllExistingTags().size());
		
		test.delete();
	}

}
