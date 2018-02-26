package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.StringProperty;
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
		Objects.requireNonNull(snippet, "CodeSnippet was null");
		if (this.unfilteredData.contains(snippet)) {
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
		Objects.requireNonNull(snippet, "CodeSnippet was null.");
		if (this.unfilteredData.contains(snippet)) {
			int index = this.unfilteredData.indexOf(snippet);
			this.unfilteredData.remove(index);
			this.dataStore.removeCodeSnippet(snippet);
		}
	}

	/**
	 * Writes the entire DataStore to disk, syncing all CodeSnippets.
	 * 
	 * @preconditions: 	None
	 * @postconditions: The DataStore will be written to disk.
	 */
	public void writeAllCodeSnippetsToDataStore() {
		for (CodeSnippet snippet : this.unfilteredData) {
			this.storeCodeSnippet(snippet);
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
		Objects.requireNonNull(text, "Filter text was null.");
		if (text.equals("")) {
			this.observableData = this.unfilteredData;
		} else {
			this.filteredData = this.unfilteredData.filtered((snippet) -> {
				return snippet.getCode().containsText(text);
			});
			this.observableData = this.filteredData;
		}
	}

	/**
	 * Returns the TagIndex object in this controller.
	 * @preconditions: None
	 * @return The TagIndex
	 */
	public TagIndex getTagIndex() {
		return tagIndex;
	}

	/**
	 * Gets a list of all tags that currently exist in the program.
	 * @preconditions: None
	 * @return The list of tags.
	 */
	public ObservableList<String> getAllExistingTags() {
		HashSet<String> tags = this.tagIndex.getAllTags();
		ObservableList<String> allTags = FXCollections.observableArrayList();
		tags.forEach(tag -> allTags.add(tag));
		return allTags;
	}

	/**
	 * Sets the current observable list to either the standard unfiltered list, or
	 * to a filtered version of the standard list that is filtered on the provided tag.
	 * @preconditions: 	text != null
	 * @postconditions: The current observable list will either be filtered or unfiltered.
	 * @param text The text to filter with.
	 */
	public void filterListWithTag(String filterString) {
		this.filteredData = this.unfilteredData.filtered((snippet) -> {
			boolean[] containsTag = {false};
			List<StringProperty> tags = snippet.getTags();
			tags.forEach(tagProperty -> { if(tagProperty.get().equals(filterString)) containsTag[0] = true; });
			return containsTag[0];
		});
		this.observableData = this.filteredData;
	}
	
	/**
	 * Creates a new CodeSnippet with the specified name and puts it in the DataStore.
	 * @preconditions: name != null && name is not empty
	 * @param name The name of the new CodeSnippet.
	 * @return The new CodeSnippet.
	 */
	public CodeSnippet createNewCodeSnippetWithName(String name) {
		Objects.requireNonNull(name, "The name cannot be null.");
		if (name.length() == 0) {
			throw new IllegalArgumentException("The name cannot have length zero.");
		}
		CodeSnippet newSnippet = new CodeSnippet(name, "", "");
		this.storeCodeSnippet(newSnippet);
		return newSnippet;
	}

	/**
	 * Removes the specified tag from every CodeSnippet, and the system as a whole.
	 * @preconditions: tagString != null
	 * @postconditions: The specified tag will be removed from the system.
	 * @param tagString The tag to purge.
	 */
	public void purgeTag(String tagString) {
		Objects.requireNonNull(tagString, "The tagString was null.");
		this.unfilteredData.forEach(snippet -> {
			List<StringProperty> tags = snippet.getTags();
			tags.removeIf(aTag -> aTag.get().equals(tagString));
		});
		this.tagIndex.getAllTags().remove(tagString);
		this.writeAllCodeSnippetsToDataStore();
	}

	/**
	 * Adds a tag to a CodeSnippet.
	 * @preconditions: newTag != null && snippet != null
	 * @postconditions: The specified tag will be added to the specified CodeSnippet.
	 * @param snippet The CodeSnippet the tag is being added to.
	 * @param newTag The tag being added.
	 */
	public void addTagToSnippet(CodeSnippet snippet, String newTag) {
		Objects.requireNonNull(newTag,  "The tag cannot be null.");
		Objects.requireNonNull(snippet, "The CodeSnippet cannot be null.");
		if (newTag.length() == 0) {
			throw new IllegalArgumentException("Cannot add tag of length zero.");
		}
		snippet.addTag(newTag);
		this.storeCodeSnippet(snippet);
		this.tagIndex.getAllTags().add(newTag);
	}

	/**
	 * Removes a tag from a CodeSnippet.
	 * @preconditions: tagToRemove != null && codeSnippet != null
	 * @postconditions: The specified tag will be removed from the specified CodeSnippet.
	 * @param snippet The CodeSnippet the tag is being removed from.
	 * @param newTag The tag being removed.
	 */
	public void removeTagFromSnippet(CodeSnippet codeSnippet, String tagToRemove) {
		Objects.requireNonNull(tagToRemove, "The tag cannot be null.");
		Objects.requireNonNull(codeSnippet, "The CodeSnippet cannot be null.");
		
		codeSnippet.removeTag(tagToRemove);
		this.storeCodeSnippet(codeSnippet);
		boolean[] isTagPurgable = {true};
		this.unfilteredData.forEach(snippet -> {
			List<StringProperty> tags = snippet.getTags();
			tags.forEach(tagProperty -> { if (tagProperty.get().equals(tagToRemove)) isTagPurgable[0] = false; });
		});
		if (isTagPurgable[0]) {
			this.tagIndex.getAllTags().remove(tagToRemove);
		}
	}
}
