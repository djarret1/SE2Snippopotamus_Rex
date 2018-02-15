package controller;

import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CodeSnippet;
import model.CodeSnippetDataStore;
import model.TextFileDataStoreImplementation;

/**
 * Controller for the main view.
 * @author 	David Jarrett
 * @version 2/12/2018
 */
public class MainViewController {

	private CodeSnippetDataStore dataStore;
	private ObservableList<CodeSnippet> observableData;
		
	/**
	 * Initializes the controller by loading the code snippet data from the data-store.
	 * @preconditions: 	filename != null
	 * @postconditions: Code snippet data will be loaded into the data-store.
	 * @param filename 	The name of the code snippet data file.
	 */
	public MainViewController(String filename) {
		this.dataStore = new TemporaryDataStoreImplementation(Objects.requireNonNull(filename, "Filename was null."));
	}
	
	private void loadObservableData() {
		for (CodeSnippet current : this.dataStore.getCodeSnippetList()) {
			this.observableData.add(current);
		}
	}

	/**
	 * Returns the observable CodeSnippet list.
	 * @preconditions: 	None
	 * @return An observable list of code snippets.
	 */
	public ObservableList<CodeSnippet> getCodeSnippetList() {
		return this.observableData;
	}
	
	/**
	 * Puts the supplied CodeSnippet into the data-store. If the snippet does not exist,
	 * it is added to the data-store. If it does exist, the existing snippet it replaced
	 * with the supplied snippet.
	 * @param snippet The CodeSnippet to add to the data-store.
	 */
	public void storeUpdatedCodeSnippet(CodeSnippet snippet) {
		if (this.observableData.contains(Objects.requireNonNull(snippet, "Snippet was null."))) {
			int index = this.observableData.indexOf(snippet);
			this.observableData.set(index, snippet);
		} else {
			this.observableData.add(snippet);
		}
		this.dataStore.writeCodeSnippet(snippet);
	}
	
	/**
	 * Removes the provided CodeSnippet from the data-store if it exists. If the snippet does not exist nothing is done.
	 * 
	 * @param snippet The CodeSnippet to remove from the data-store.
	 */
	public void deleteCodeSnippet(CodeSnippet snippet) {
		this.dataStore.removeCodeSnippet(snippet);
	}
	
	/**
	 * Removes the provided CodeSnippet from the list if it exists. If the snippet does not exist nothing is done.
	 * 
	 * @preconditions: The data-store should be initialized. The provided code snippet can not be null.
	 * @postconditions: The provided CodeSnippet will be removed from the Data-store
	 */
	public void removeCodeSnippet(CodeSnippet snippet) {
		if (this.observableData.contains(Objects.requireNonNull(snippet, "CodeSnippet was null."))) {
			int index = this.observableData.indexOf(snippet);
			this.observableData.remove(index, index);
		}
		this.dataStore.removeCodeSnippet(snippet);
	}
	
}
