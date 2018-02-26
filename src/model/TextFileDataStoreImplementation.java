package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is acting as our temporary data-store until we decide upon a more
 * permanent solution.
 * 
 * @author David Jarrett
 * @version 02/13/2018
 */
public class TextFileDataStoreImplementation implements CodeSnippetDataStore {

	private static final String NEWLINE = "\n";
	private static final String NEWLINE_REPLACEMENT = "000000zxczxczxc1111111111111122222222222000lskdjfPOPOPOP";
	private static final String TAG_MARKER = ">TAG<";
	
	private File storageFile;
	private List<CodeSnippet> snippets;

	/**
	 * Initializes the data-store by reading the CodeSnippet's from a text file. If
	 * the file does not exist, then the data-store is initialized automatically
	 * with an empty ObservableList<CodeSnippet>.
	 * 
	 * @preconditions: filename != null
	 * @postconditions: Data-store will be initialized and will contain all snippet
	 *                  data.
	 * @param filename
	 *            The name of the snippet data file.
	 */
	public TextFileDataStoreImplementation(String filename) {
		String currentDirectory = System.getProperty("user.dir");
		this.storageFile = new File(currentDirectory, Objects.requireNonNull(filename, "Filename was null."));
		this.loadCodeSnippets();
	}

	/**
	 * Loads all CodeSnippet's from the snippet data file. If no data file is
	 * present, then an empty ObservableList is used to house future CodeSnippets.
	 * @preconditions: None
	 * @postconditions: All snippet data will be loaded and available, or an empty
	 *                  list, ready for population, will be available.
	 */
	@Override
	public void loadCodeSnippets() {
		this.snippets = new ArrayList<>();
		try (Scanner in = new Scanner(this.storageFile)) {
			while (in.hasNextLine()) {
				String name = in.nextLine();
				String description = in.nextLine();
				String code = in.nextLine();
				List<StringProperty> tags = new ArrayList<>();
				while (in.hasNext(TAG_MARKER)) {
					in.nextLine();
					tags.add(new SimpleStringProperty(in.nextLine()));
				}
				CodeSnippet snippet = new CodeSnippet(name, description, code, tags);
				this.replaceBreakingCharactersIn(snippet, false);
				this.snippets.add(snippet);
			}
		} catch (FileNotFoundException e) {
			this.createDefaultCodeSnippet();
		}
	}

	private void replaceBreakingCharactersIn(CodeSnippet snippet, Boolean isSavingData) {
		String description = snippet.getDescription();
		String current = isSavingData ? NEWLINE : NEWLINE_REPLACEMENT;
		String replacement = isSavingData ? NEWLINE_REPLACEMENT : NEWLINE;
		description = description.replaceAll(current, replacement);
		snippet.setDescription(description);
	}

	/**
	 * Returns an ObservableList containing the CodeSnippets.
	 * 
	 * @preconditions: The data-store should be initialized.
	 * @return An ObservableList<CodeSnippet>
	 */
	@Override
	public List<CodeSnippet> getCodeSnippetList() {
		return this.snippets;
	}

	/**
	 * Saves the CodeSnippet list back to the data file.
	 *
	 * @preconditions: The data-store should be initialized.
	 * @postconditions: The ObservableList<CodeSnippet> will be written back to disk.
	 */
	@Override
	public void saveCodeSnippets() {
		try (PrintWriter outWriter = new PrintWriter(this.storageFile, "UTF-8")) {
			for (CodeSnippet snippet : this.snippets) {
				this.replaceBreakingCharactersIn(snippet, true);
				outWriter.println(snippet.getName());
				outWriter.println(snippet.getDescription());
				outWriter.println(snippet.getCode().getCodeText());
				List<StringProperty> tags = snippet.getTags();
				for (StringProperty nextTag : tags) {
					outWriter.println(TAG_MARKER);
					outWriter.println(nextTag.get());
				}
				this.replaceBreakingCharactersIn(snippet, false);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("The data file was somehow deleted!");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("An invalid coding scheme was used for the data file.");
		}
	}

	/**
	 * Stores the provided CodeSnippet into the list. If the snippet exists, it is
	 * replaced. All data is then written back to disk.
	 * 
	 * @preconditions: The data-store should be initialized. The provided code snippet can not be null.
	 * @postconditions: The provided CodeSnippet will be written into the
	 *                  data-store.
	 */
	@Override
	public void writeCodeSnippet(CodeSnippet snippet) {
		if (this.snippets.contains(Objects.requireNonNull(snippet, "Snippet was null."))) {
			int index = this.snippets.indexOf(snippet);
			this.snippets.set(index, snippet);
		} else {
			this.snippets.add(snippet);
		}
		this.saveCodeSnippets();
	}
	
	/**
	 * Removes the provided CodeSnippet from the list if it exists. If the snippet does not exist nothing is done.
	 * 
	 * @preconditions: The data-store should be initialized. The provided code snippet can not be null.
	 * @postconditions: The provided CodeSnippet will be removed from the Data-store
	 */
	@Override
	public void removeCodeSnippet(CodeSnippet snippet) {
		if (this.snippets.contains(Objects.requireNonNull(snippet, "CodeSnippet was null."))) {
			int index = this.snippets.indexOf(snippet);
			this.snippets.remove(index);
		}
		this.saveCodeSnippets();
	}
	
	private void createDefaultCodeSnippet() {
		CodeSnippet emptySnippet = new CodeSnippet("Welcome! Click here to change my name...",
				
				"This is the description area. You can type a description of your code snippet here, along "
				+ "with any other relevant information.000000zxczxczxc1111111111111122222222222000lskdjfPOPOPOP"
				+ "Also, know that any changes that you make to a code snippet's name or description will be "
				+ " saved automatically.",
				
				"<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><h1><font size=\"6\">"
				+ "Welcome to <font color=\"#990000\">S</font><font color=\"#664db3\">n</font>i<font "
				+ "color=\"#336666\">p</font><font color=\"#cc6633\">p</font>o<font color=\"#b3e6e6\">p"
				+ "</font><font color=\"#990000\">o</font><font color=\"#336633\">t</font><font color=\"#666666"
				+ "\">a</font><font color=\"#4d8080\">m</font><font color=\"#996600\">u</font>s <span "
				+ "style=\"background-color: rgb(0, 0, 0);\"><font color=\"#ffffff\">Rex</font></span>!</font>"
				+ "</h1><h3><font size=\"4\">Code Snippets</font></h3><p>This is where you type your code snippets."
				+ "When you're done, make sure to hit the save button. Don't worry if you forget, though, "
				+ "because I'll remind you! The name of your snippet is at the top of this window. You"
				+ " can click it and edit it.</p><h3><font size=\"4\">Tags</font></h3><p>The area right "
				+ "below this message allows you to add tags to your code snippets. You can filter the "
				+ "snippets on the left by tag.</p><p><font size=\"6\"><br></font></p><br></body></html>");
		this.snippets.add(emptySnippet);
		this.saveCodeSnippets();
	}
}
