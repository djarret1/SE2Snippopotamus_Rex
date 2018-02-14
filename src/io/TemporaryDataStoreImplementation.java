package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.CodeSnippet;

/**
 * This class is acting as our temporary data-store until we decide upon a more
 * permanent solution.
 * 
 * @author David Jarrett
 * @version 02/13/2018
 */
public class TemporaryDataStoreImplementation implements CodeSnippetDataStore {

	private File storageFile;
	private ObservableList<CodeSnippet> snippets;

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
	public TemporaryDataStoreImplementation(String filename) {
		if (filename == null) {
			throw new NullPointerException("Filename was null.");
		}
		String currentDirectory = System.getProperty("user.dir");
		this.storageFile = new File(currentDirectory, filename);
		this.loadCodeSnippets();
	}

	/**
	 * Loads all CodeSnippet's from the snippet data file. If no data file is
	 * present, then an empty ObservableList is used to house future CodeSnippets.
	 * 
	 * @preconditions: None
	 * @postconditions: All snippet data will be loaded and available, or an empty
	 *                  list, ready for population, will be available.
	 */
	@Override
	public void loadCodeSnippets() {
		this.snippets = FXCollections.observableArrayList(CodeSnippet.extractor());
		try (Scanner in = new Scanner(this.storageFile)) {
			while (in.hasNextLine()) {
				String name = in.nextLine();
				String description = in.nextLine();
				String code = in.nextLine();
				CodeSnippet snippet = new CodeSnippet(name, description, code);
				this.snippets.add(snippet);
			}
		} catch (FileNotFoundException e) {
			// use empty list;
		}
	}

	/**
	 * Returns an ObservableList containing the CodeSnippets.
	 * 
	 * @preconditions: The data-store should be initialized.
	 * @return An ObservableList<CodeSnippet>
	 */
	@Override
	public ObservableList<CodeSnippet> getCodeSnippetList() {
		if (this.snippets == null) {
			throw new IllegalStateException("The data-store has not been initialized.");
		}
		return this.snippets;
	}

	/**
	 * Saves the CodeSnippet list back to the data file.
	 * 
	 * @preconditions: The data-store should be initialized.
	 * @postconditions: The ObservableList<CodeSnippet> will be written back to
	 *                  disk.
	 */
	@Override
	public void saveCodeSnippets() {
		if (this.snippets == null) {
			throw new IllegalStateException("The data-store has not been initialized.");
		}
		try (PrintWriter outWriter = new PrintWriter(this.storageFile, "UTF-8")) {
			for (CodeSnippet snippet : this.snippets) {
				outWriter.println(snippet.getName());
				outWriter.println(snippet.getDescription());
				outWriter.println(snippet.getCode().getCodeText());
			}
		} catch (FileNotFoundException e) {
			this.showErrorAlert("Could not write out data!");
		} catch (UnsupportedEncodingException e) {
			this.showErrorAlert("An invalid coding scheme was supplied. Try UTF-8.");
		}
	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.show();
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
		if (this.snippets == null) {
			throw new IllegalStateException("The data-store has not been initialized.");
		}
		if (snippet == null) {
			throw new IllegalArgumentException("The provided snippet is invalid");
		}
		if (this.snippets.contains(snippet)) {
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
		if (this.snippets == null) {
			throw new IllegalStateException("The data-store has not been initialized.");
		}
		if (snippet == null) {
			throw new IllegalArgumentException("The provided snippet is invalid");
		}
		if (this.snippets.contains(snippet)) {
			int index = this.snippets.indexOf(snippet);
			this.snippets.remove(index, index);
		}
	}
}
