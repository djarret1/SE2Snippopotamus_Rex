package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import model.CodeSnippet;
import model.CodeSnippetDataStore;
import model.TagIndex;
import model.TextFileDataStoreImplementation;

class TestTextFileDataStoreImplementationWhenLoadCodeSnippets {

	@Test
	void test() {
		File test = new File("test.dat");
		CodeSnippetDataStore ds = new TextFileDataStoreImplementation(test.getName());
		
		TagIndex index = new TagIndex();
		index.populateIndex(ds);
		CodeSnippet snippet = new CodeSnippet("Test name", "Test description", "Test codeText");
		
		ds.writeCodeSnippet(snippet);
		
		ds.saveCodeSnippets();
		
		test.delete();
		
		assertEquals("Welcome! Click here to change my name...", ds.getCodeSnippetList().get(0).getName());
	}

}
