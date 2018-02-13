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

public class TemporaryDataStore implements CodeSnippetDataStore {

	private File storageFile;
	private ObservableList<CodeSnippet> snippets;
	
	public TemporaryDataStore(String filename) {
		if (filename == null) {
			throw new NullPointerException("Filename was null.");
		}
		String currentDirectory = System.getProperty("user.dir");
		this.storageFile = new File(currentDirectory, filename);
		this.loadSnippets();
	}
	
	@Override
	public void loadSnippets() {
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

	private void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.show();
	}
	
	@Override
	public ObservableList<CodeSnippet> getCodeSnippetList() {
		return this.snippets;
	}

	@Override
	public void writeCodeSnippetList() {
		try (PrintWriter outWriter = new PrintWriter(this.storageFile, "UTF-8")){
			for (CodeSnippet snippet : this.snippets) {
				outWriter.println(snippet.getName());
				outWriter.println(snippet.getDescription());
				outWriter.println(snippet.getCode().getCodeText());
			}
		} catch (FileNotFoundException e) {
			this.showErrorAlert("Could not find the code snippet data file!");
		} catch (UnsupportedEncodingException e) {
			this.showErrorAlert(e.getLocalizedMessage());
		}
	}

	@Override
	public void writeCodeSnippet(CodeSnippet snippet) {
		if (this.snippets != null && this.snippets.contains(snippet)) {
			int index = this.snippets.indexOf(snippet);
			this.snippets.set(index, snippet);
		} else {
			this.snippets.add(snippet);
		}
		this.writeCodeSnippetList();
	}

}
