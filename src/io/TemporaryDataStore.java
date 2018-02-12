package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public void loadSnippets() {
		try (FileInputStream inputFile = new FileInputStream(this.storageFile);
			 ObjectInputStream inputObject = new ObjectInputStream(inputFile);) {
			List<CodeSnippet> tempList = (List<CodeSnippet>) inputObject.readObject();
			this.snippets = FXCollections.observableArrayList(tempList);
		} catch (FileNotFoundException e) {
			this.snippets = FXCollections.emptyObservableList();
		} catch (IOException e) {
			this.showErrorAlert("The existing code snippet file could not be read.");
		} catch (ClassNotFoundException e) {
			this.showErrorAlert("Invalid data in specified file. Is this the corrent file?");
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
		try (FileOutputStream outFile = new FileOutputStream(this.storageFile);
			 ObjectOutputStream outputObject = new ObjectOutputStream(outFile);) {
			outputObject.writeObject(new ArrayList<CodeSnippet>(this.snippets));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			this.showErrorAlert("The code snippet file could not be written to.");
		}
	}

	@Override
	public void writeCodeSnippet(CodeSnippet snippet) {
		if (this.snippets.contains(snippet)) {
			int index = this.snippets.indexOf(snippet);
			this.snippets.set(index, snippet);
		} else {
			this.snippets.add(snippet);
		}
		this.writeCodeSnippetList();
	}

}
