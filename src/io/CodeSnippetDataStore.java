package io;

import java.io.Closeable;
import java.util.List;

import model.CodeSnippet;

public interface CodeSnippetDataStore extends Closeable {

	List<CodeSnippet> getCodeSnippetList();
	void writeCodeSnippetList(List<CodeSnippet> snippets);
	void writeCodeSnippet(CodeSnippet snippet);
	
}
