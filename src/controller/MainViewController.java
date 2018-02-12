package controller;

import io.CodeSnippetDataStore;
import io.TemporaryDataStore;
import javafx.collections.ObservableList;
import model.CodeSnippet;

public class MainViewController {

	private CodeSnippetDataStore dataStore;
	private ObservableList<CodeSnippet> snippets;
	
	public MainViewController(String filename) {
		if (filename == null) {
			throw new NullPointerException("Filename was null.");
		}
		this.dataStore = new TemporaryDataStore(filename);
		this.snippets = this.dataStore.getCodeSnippetList();
	}
	
}
