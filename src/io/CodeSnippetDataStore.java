package io;

import javafx.collections.ObservableList;
import model.CodeSnippet;

public interface CodeSnippetDataStore {

	void loadCodeSnippets();
	ObservableList<CodeSnippet> getCodeSnippetList();
	void saveCodeSnippets();
	void writeCodeSnippet(CodeSnippet snippet);
	
}
