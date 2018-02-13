package controller;

import java.util.Objects;

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
	
	public void addCodeSnippet(String name, String description, String codeText) {
		CodeSnippet snippet = new CodeSnippet(Objects.requireNonNull(name, "Name was null."),
				Objects.requireNonNull(description), Objects.requireNonNull(codeText));
		this.dataStore.writeCodeSnippet(snippet);
	}
	
}
