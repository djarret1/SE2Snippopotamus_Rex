package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.CodeSnippet;
import model.CodeSnippetDataStore;
import model.TagIndex;
import model.TextFileDataStoreImplementation;

class TestTextFileDataStoreImplementationWhenRemoveCodeSnippet {

	@Test
	void testRemoveCodeSnippetWithValidDataStore() {
		CodeSnippetDataStore ds = new TextFileDataStoreImplementation("populatedDummyData2.dat");
		TagIndex index = new TagIndex();
		index.populateIndex(ds);
		CodeSnippet snippet = new CodeSnippet("Test name", "Test description", "Test codeText");
		
		ds.writeCodeSnippet(snippet);
		
		ds.saveCodeSnippets();
		
		ds.removeCodeSnippet(snippet);
		ds.saveCodeSnippets();
		
		assertEquals(3, ds.getCodeSnippetList().size());
		
		
	}
}
