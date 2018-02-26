package model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.StringProperty;

/**
 * Indexing system for tags.
 * 
 * @author Andrew Weems & David Jarrett
 * @version 2/12/2018
 */
public class TagIndex {
	private HashSet<String> allTags;

	/**
	 * Initializes a new TagIndex.
	 * 
	 * @preconditions: none
	 * @postconditions: Object will be initialized and ready for use.
	 */
	public TagIndex() {
		this.allTags = new HashSet<>();
	}

	/**
	 * Populates the index from a datastore
	 * 
	 * @preconditions: data != null
	 * @postcondition: the TagIndex is populated from a DataStore
	 * @param data DataStore to populate from.
	 */
	public void populateIndex(CodeSnippetDataStore data) {
		Objects.requireNonNull(data, "DataStore can't be null");
		List<CodeSnippet> codeStore = data.getCodeSnippetList();
		codeStore.forEach(snippet -> {
			List<StringProperty> tags = snippet.getTags();
			tags.forEach(tag -> this.allTags.add(tag.get()));
		});
	}
	
	public HashSet<String> getAllTags() {
		return this.allTags;
	}
}
