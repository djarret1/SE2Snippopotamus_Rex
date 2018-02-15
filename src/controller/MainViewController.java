package controller;

import java.util.Objects;

import io.CodeSnippetDataStore;
import io.TemporaryDataStoreImplementation;
import javafx.collections.ObservableList;
import model.CodeSnippet;

/**
 * Controller for the main view.
 * @author 	David Jarrett
 * @version 2/12/2018
 */
public class MainViewController {

	private CodeSnippetDataStore dataStore;
		
	/**
	 * Initializes the controller by loading the code snippet data from the data-store.
	 * @preconditions: 	filename != null
	 * @postconditions: Code snippet data will be loaded into the data-store.
	 * @param filename 	The name of the code snippet data file.
	 */
	public MainViewController(String filename) {
		this.dataStore = new TemporaryDataStoreImplementation(Objects.requireNonNull(filename, "Filename was null."));
	}
	
	/**
	 * Returns the code snippets from the data-store.
	 * @preconditions: 	None
	 * @return An observable list of code snippets.
	 */
	public ObservableList<CodeSnippet> getCodeSnippetList() {
		return this.dataStore.getCodeSnippetList();
	}
	
	/**
	 * Puts the supplied CodeSnippet into the data-store. If the snippet does not exist,
	 * it is added to the data-store. If it does exist, the existing snippet it replaced
	 * with the supplied snippet.
	 * @param snippet The CodeSnippet to add to the data-store.
	 */
	public void storeUpdatedCodeSnippet(CodeSnippet snippet) {
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
	
}
