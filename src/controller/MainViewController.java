package controller;

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
		if (filename == null) {
			throw new NullPointerException("Filename was null.");
		}
		this.dataStore = new TemporaryDataStoreImplementation(filename);
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
	public void updateCodeSnippet(CodeSnippet snippet) {
		this.dataStore.writeCodeSnippet(snippet);
	}
	
}
