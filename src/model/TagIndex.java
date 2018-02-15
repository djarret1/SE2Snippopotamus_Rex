package model;

import java.util.ArrayList;
import java.util.HashMap;

public class TagIndex {
	private HashMap<String, ArrayList<CodeSnippet>> tags;

	public TagIndex() {
		this.tags = new HashMap<String, ArrayList<CodeSnippet>>();
	}

	public void addTag(String toAdd) {

		if (this.tags == null) {
			throw new IllegalStateException("TagIndex not initialized");
		}
		if (!this.tags.containsKey(toAdd)) {
			this.tags.put(toAdd, new ArrayList<CodeSnippet>());
		}
	}

	public void tagSnippet(String tag, CodeSnippet snippet) {

		if (this.tags == null) {
			throw new IllegalStateException("TagIndex not initialized");
		}
		if (!this.tags.containsKey(tag)) {
			this.addTag(tag);
			this.tagSnippet(tag, snippet);
			
		} else if (!this.tags.get(tag).contains(snippet)) {
			
			this.tags.get(tag).add(snippet);
			snippet.addTag(tag);
			
		} 

	}

	public void untagSnippet(String tag, CodeSnippet snippet) {

		if (this.tags == null) {
			throw new IllegalStateException("TagIndex not initialized");
		}
		if (this.tags.containsKey(tag)) {
			
			if (this.tags.get(tag).contains(snippet)) {
				snippet.removeTag(tag);
				this.tags.get(tag).remove(snippet);
			}
		}

	}

	public void purgeTag(String toPurge) {

		if (this.tags == null) {
			throw new IllegalStateException("TagStore not initialized");
		}
		if (this.tags.containsKey(toPurge)) {
			for (CodeSnippet currSnippet : this.tags.get(toPurge)) {
				currSnippet.removeTag(toPurge);
			}
			this.tags.remove(toPurge);
		}
	}

	public void removeTag(String toRemove) {

		if (this.tags == null) {
			throw new IllegalStateException("TagStore not initialized");
		}
		if (this.tags.containsKey(toRemove)) {
			this.tags.remove(toRemove);
			
		}
	}
}
