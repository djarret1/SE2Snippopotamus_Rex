package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.CodeSnippet;
import model.CodeSnippetDataStore;
import model.TagIndex;
import model.TextFileDataStoreImplementation;

class TestTextFileDataStoreImplementationWhenWriteSnippet {

	@Test
	void testWriteSnippetWhenSnippetIsNull() {
		CodeSnippetDataStore ds = new TextFileDataStoreImplementation("populatedDummyData3.dat");
		TagIndex index = new TagIndex();
		index.populateIndex(ds);
		
		assertThrows(NullPointerException.class, () -> ds.writeCodeSnippet(null));
	}
	
	@Test
	void testWriteCodeSnippetWhenSnippetAlreadyExists() {
		CodeSnippetDataStore ds = new TextFileDataStoreImplementation("populatedDummyData3.dat");
		TagIndex index = new TagIndex();
		index.populateIndex(ds);
		CodeSnippet snippet = new CodeSnippet("Test name", "Test description", "Test codeText");
		CodeSnippet snippet2 = snippet;
		ds.writeCodeSnippet(snippet);
		
		ds.writeCodeSnippet(snippet2);
		ds.saveCodeSnippets();
		
		assertEquals(4, ds.getCodeSnippetList().size());
		ds.removeCodeSnippet(snippet);
		ds.saveCodeSnippets();
		ds.removeCodeSnippet(snippet2);
		ds.saveCodeSnippets();
	}
}
