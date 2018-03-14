package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.CodeSnippet;
import model.CodeSnippetDataStore;
import model.TagIndex;
import model.TextFileDataStoreImplementation;

class TestTextFileDataStoreImplementationWhenSaveCodeSnippets {

	@Test
	void testSaveCodeSnippetWithValidDataStore() {
		CodeSnippetDataStore ds = new TextFileDataStoreImplementation("populatedDummyData.dat");
		TagIndex index = new TagIndex();
		index.populateIndex(ds);
		CodeSnippet snippet = new CodeSnippet("Test name", "Test description", "Test codeText");
		
		ds.writeCodeSnippet(snippet);
		
		ds.saveCodeSnippets();
		
		assertEquals(4, ds.getCodeSnippetList().size());
		
		ds.removeCodeSnippet(snippet);
	}
	
	@Test
	void testSaveCodeSnippetWithInvalidDataStore() {
		
	}

}
