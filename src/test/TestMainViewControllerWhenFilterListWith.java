package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import controller.MainViewController;
import model.CodeSnippet;

class TestMainViewControllerWhenFilterListWith {

	@Test
	void testFilterByValidCodeText() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		CodeSnippet testCodeSnippet2 = new CodeSnippet("TestName2", "TestDescription2", "TestCodeText2");
		CodeSnippet testCodeSnippet3 = new CodeSnippet("TestName3", "TestDescription3", "TestCodeText3");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(testCodeSnippet1);
		controller.storeCodeSnippet(testCodeSnippet2);
		controller.storeCodeSnippet(testCodeSnippet3);
		
		controller.filterListWith("TestCodeText1");
		
		assertEquals("TestCodeText1", controller.getObservableList().get(0).getCode().getCodeText());
		
		test.delete();	
	}
	
	@Test
	void testFilteredByEmptyString() {
		File test = new File("test.dat");
		CodeSnippet testCodeSnippet1 = new CodeSnippet("TestName1", "TestDescription1", "TestCodeText1");
		CodeSnippet testCodeSnippet2 = new CodeSnippet("TestName2", "TestDescription2", "TestCodeText2");
		CodeSnippet testCodeSnippet3 = new CodeSnippet("TestName3", "TestDescription3", "TestCodeText3");
		
		MainViewController controller = new MainViewController(test.getName());
		
		controller.storeCodeSnippet(testCodeSnippet1);
		controller.storeCodeSnippet(testCodeSnippet2);
		controller.storeCodeSnippet(testCodeSnippet3);
		
		controller.filterListWith("");
		
		assertEquals(4, controller.getObservableList().size());
		
		test.delete();
	}

}
