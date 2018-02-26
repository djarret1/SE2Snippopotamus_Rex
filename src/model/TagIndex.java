package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.StringProperty;

/**
 * Indexing system for tags.
 * 
 * @author Andrew Weems
 * @version 2/12/2018
 */
public class TagIndex {
	private HashMap<String, ArrayList<CodeSnippet>> tags;

	/**
	 * Initializes a new tagindex.
	 * 
	 * @preconditions: none
	 * @postconditions: Object will be initialized and ready for use.
	 */
	public TagIndex() {
		this.tags = new HashMap<String, ArrayList<CodeSnippet>>();
	}

	/**
	 * Adds a tag to the index
	 * 
	 * @preconditions: toAdd != null and the index has been initialized
	 * @postcondition: the tag is added to the index
	 * @param toAdd
	 *            Tag to add.
	 */
	public void addTag(String toAdd) {


		if (!this.tags.containsKey(toAdd)) {
			this.tags.put(toAdd, new ArrayList<CodeSnippet>());
		}
	}

	/**
	 * Adds tags to a snippet in the index
	 * 
	 * @preconditions: tag != null snippet!= null and the index has been initialized
	 * @postcondition: the tag is added to the index if it doesnt exist, and the
	 *                 snippet is associated with it
	 * @param tag
	 *            Tag to add.
	 * @param snippet
	 *            The snippet to tag
	 */
	public void tagSnippet(String tag, CodeSnippet snippet) {

		if (!this.tags.containsKey(tag)) {
			this.addTag(tag);
			this.tagSnippet(tag, snippet);

		} else if (!this.tags.get(tag).contains(snippet)) {

			this.tags.get(tag).add(snippet);
			snippet.addTag(tag);

		}

	}

	/**
	 * Removes tags from a snippet in the index
	 * 
	 * @preconditions: tag != null snippet!= null and the index has been initialized
	 * @postcondition: the tag is added to the index
	 * @param tag
	 *            Tag to remove.
	 * @param snippet
	 *            The snippet to untag
	 */
	public void untagSnippet(String tag, CodeSnippet snippet) {

		if (this.tags.containsKey(tag)) {

			if (this.tags.get(tag).contains(snippet)) {
				snippet.removeTag(tag);
				this.tags.get(tag).remove(snippet);
			}
		}

	}

	/**
	 * Removes a tag from the index, and every code snippet that contains it
	 * 
	 * @preconditions: toPurge != null and the index has been initialized
	 * @postcondition: the tag is purged from the system
	 * @param toPurge
	 *            Tag to purged.
	 */
	public void purgeTag(String toPurge) {

		if (this.tags.containsKey(toPurge)) {
			for (CodeSnippet currSnippet : this.tags.get(toPurge)) {
				currSnippet.removeTag(toPurge);
			}
			this.tags.remove(toPurge);
		}
	}

	/**
	 * Removes a tag from the index
	 * 
	 * @preconditions: toRemove != null and the index has been initialized
	 * @postcondition: the tag is removed from the index
	 * @param toRemove
	 *            Tag to add.
	 */
	public void removeTag(String toRemove) {

		if (this.tags.containsKey(toRemove)) {
			this.tags.remove(toRemove);

		}
	}
	
	/**
	 * Populates the index from a datastore
	 * 
	 * @preconditions: data != null, and the index has been initialized
	 * @postcondition: the tagindex is populated from a datastore
	 * @param data
	 *            Datastore to populate with
	 */
	public void populateIndex(TextFileDataStoreImplementation data) {
		Objects.requireNonNull(data, "DataStore can't be null");
		List<CodeSnippet> codeStore = data.getCodeSnippetList();
		for (CodeSnippet snippet : codeStore) {
			for (StringProperty tag : snippet.getTags()) {
				this.tagSnippet(tag.toString(), snippet);
				}
			}
		}
	}

