package model;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Provides a consistent interface for reading and writing CodeSnippets from a data-source.
 * @author 	David Jarrett
 * @version	2/13/2018
 */
public interface CodeSnippetDataStore {

	/**
	 * Provided explicitly in order to facilitate lazy loading.
	 */
	void loadCodeSnippets();
	
	/**
	 * Provides the CodeSnippet data in the data-store as an ObservableList<CodeSnippet>
	 * @return The list of CodeSnippets.
	 */
	List<CodeSnippet> getCodeSnippetList();
	
	/**
	 * Should write all code snippets to disk.
	 */
	void saveCodeSnippets();
	
	/**
	 * Stores a provided CodeSnippet in the data-store.
	 * @param snippet
	 */
	void writeCodeSnippet(CodeSnippet snippet);
	
	/**
	 * Removes a provided CodeSnippet from the data-store.
	 * @param snippet
	 */
	void removeCodeSnippet(CodeSnippet snippet);
}
