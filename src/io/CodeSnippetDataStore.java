package io;

import javafx.collections.ObservableList;
import model.CodeSnippet;

public interface CodeSnippetDataStore {

	void loadSnippets();
	ObservableList<CodeSnippet> getCodeSnippetList();
	void writeCodeSnippetList();
	void writeCodeSnippet(CodeSnippet snippet);
	
}
