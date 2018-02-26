package controller;

import java.util.HashSet;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CodeSnippet;
import model.CodeSnippetDataStore;
import model.TagIndex;
import model.TextFileDataStoreImplementation;

/**
 * Controller for the main view.
 * @author 	David Jarrett
 * @version 2/12/2018
 */
public class MainViewController {

	private CodeSnippetDataStore dataStore;
	private ObservableList<CodeSnippet> observableData;
	private ObservableList<CodeSnippet> unfilteredData;
	private ObservableList<CodeSnippet> filteredData;
	private TagIndex tagIndex;
	/**
	 * Initializes the controller by loading the code snippet data from the data-store.
	 * @preconditions: 	filename != null
	 * @postconditions: Code snippet data will be loaded into the data-store.
	 * @param filename 	The name of the code snippet data file.
	 */
	public MainViewController(String filename) {
		this.dataStore = new TextFileDataStoreImplementation(Objects.requireNonNull(filename, "Filename was null."));
		this.tagIndex = new TagIndex();
		this.tagIndex.populateIndex(this.dataStore);
		this.unfilteredData = FXCollections.observableArrayList(CodeSnippet.extractor());
		this.loadObservableData();
	}
	
	private void loadObservableData() {
		for (CodeSnippet current : this.dataStore.getCodeSnippetList()) {
			this.unfilteredData.add(current);
		}
		this.observableData = this.unfilteredData;
	}

	/**
	 * Returns the observable CodeSnippet list.
	 * @preconditions: 	None
	 * @return An observable list of code snippets.
	 */
	public ObservableList<CodeSnippet> getObservableList() {
		return this.observableData;
	}
	
	/**
	 * Puts the supplied CodeSnippet into the data-store. If the snippet does not exist,
	 * it is added to the data-store. If it does exist, the existing snippet it replaced
	 * with the supplied snippet.
	 * @param snippet The CodeSnippet to add to the data-store.
	 */
	public void storeCodeSnippet(CodeSnippet snippet) {
		if (this.unfilteredData.contains(Objects.requireNonNull(snippet, "Snippet was null."))) {
			int index = this.unfilteredData.indexOf(snippet);
			this.unfilteredData.set(index, snippet);
		} else {
			this.unfilteredData.add(snippet);
		}
		this.dataStore.writeCodeSnippet(snippet);
	}
	
	/**
	 * Removes the provided CodeSnippet from the list if it exists. If the snippet does not exist nothing is done.
	 * 
	 * @preconditions: The data-store should be initialized. The provided code snippet can not be null.
	 * @postconditions: The provided CodeSnippet will be removed from the Data-store
	 * @param snippet The CodeSnippet to remove.
	 */
	public void removeCodeSnippet(CodeSnippet snippet) {
		if (this.unfilteredData.contains(Objects.requireNonNull(snippet, "CodeSnippet was null."))) {
			int index = this.unfilteredData.indexOf(snippet);
			this.unfilteredData.remove(index);
			this.dataStore.removeCodeSnippet(snippet);
		}
	}

	/**
	 * Sets the current observable list to either the standard unfiltered list, or
	 * to a filtered version of the standard list that is filtered on the provided text.
	 * @preconditions: 	text != null
	 * @postconditions: The current observable list will either be filtered or unfiltered.
	 * @param text The text to filter with.
	 */
	public void filterListWith(String text) {
		if (text == null) {
			throw new NullPointerException("Filter text was null.");
		}
		if (text.equals("")) {
			this.observableData = this.unfilteredData;
		} else {
			this.filteredData = this.unfilteredData.filtered((snippet) -> {
				return snippet.getCode().containsText(text);
			});
			this.observableData = this.filteredData;
		}
	}

	public TagIndex getTagIndex() {
		return tagIndex;
	}

	public ObservableList<String> getAllExistingTags() {
		HashSet<String> tags = this.tagIndex.getAllTags();
		ObservableList<String> allTags = FXCollections.observableArrayList();
		tags.forEach(tag -> allTags.add(tag));
		return allTags;
	}
	
}
