package controller;

import io.CodeSnippetDataStore;
import io.TemporaryDataStoreImplementation;
import javafx.collections.ObservableList;
import model.CodeSnippet;

public class MainViewController {

	private CodeSnippetDataStore dataStore;
		
	public MainViewController(String filename) {
		if (filename == null) {
			throw new NullPointerException("Filename was null.");
		}
		this.dataStore = new TemporaryDataStoreImplementation(filename);
	}
	
	public ObservableList<CodeSnippet> getCodeSnippetList() {
		return this.dataStore.getCodeSnippetList();
	}
	
	public void updateCodeSnippet(CodeSnippet snippet) {
		this.dataStore.writeCodeSnippet(snippet);
	}
	
}
