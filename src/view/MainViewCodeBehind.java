package view;

import java.util.Optional;

import controller.MainViewController;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.web.HTMLEditor;
import model.CodeSnippet;

/**
 * Code-behind file for the main view.
 * 
 * @author David Jarrett
 * @version 2/13/2018
 */
public class MainViewCodeBehind {

	@FXML
	private ListView<CodeSnippet> snippetListView;
	@FXML
	private ComboBox<CodeSnippet> filterComboBox;
	@FXML
	private TextArea outputTextArea;
	@FXML
	private TitledPane detailsTitledPane;
	@FXML
	private HTMLEditor snippetEditor;
	@FXML
	private TextArea descriptionTextArea;
	@FXML
	private Button saveSnippetButton;
	@FXML
	private TextField snippetNameTextField;
	@FXML
	private TextField searchTextField;
	@FXML
	private TextField tagTextField;
	@FXML
	private ComboBox<StringProperty> tagComboBox;
	private MainViewController controller;
	private CodeSnippet selected;

	@FXML
	private void initialize() {
		this.controller = new MainViewController("testing.dat");
		this.selected = null;
		this.initializeListView();
		this.initializeListeners();
		this.updateView(null);
	}

	private void initializeListView() {
		this.snippetListView.setItems(this.controller.getObservableList());
		this.snippetListView.getSelectionModel().selectFirst();
	}

	private void initializeListeners() {
		ChangeListener<Boolean> updateSnippetOnLoseFocus = (observable, oldState, hasFocus) -> {
			if (!hasFocus) {
				this.controller.storeCodeSnippet(this.selected);
			}
		};
		this.descriptionTextArea.focusedProperty().addListener(updateSnippetOnLoseFocus);
		this.snippetNameTextField.focusedProperty().addListener(updateSnippetOnLoseFocus);
	}

	@FXML
	private void updateView(Event e) {
		if (this.selected != null) {
			this.snippetNameTextField.textProperty().unbindBidirectional(this.selected.getNameProperty());
			this.descriptionTextArea.textProperty().unbindBidirectional(this.selected.getDescriptionProperty());
			this.updateCodeSaveState();
		}
		this.selected = this.snippetListView.selectionModelProperty().getValue().getSelectedItem();
		this.snippetNameTextField.textProperty().bindBidirectional(this.selected.getNameProperty());
		this.descriptionTextArea.textProperty().bindBidirectional(this.selected.getDescriptionProperty());
		this.snippetEditor.setHtmlText(this.selected.getCode().getCodeText());
		this.updateTagComboBox();
	}

	private void updateCodeSaveState() {
		if (this.saveSnippetButton.isDisabled()) {
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Press OK to save changes, Cancel to discard.");
		alert.setTitle("Save Changes?");
		alert.setHeaderText("Save Changes?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.saveSnippetButtonClick(null);
		}
		this.saveSnippetButton.setDisable(true);
	}

	private void updateTagComboBox() {
		this.tagComboBox.getItems().clear();
		this.tagComboBox.getItems().addAll(this.selected.getTags());
		this.tagComboBox.getSelectionModel().selectFirst();
	}

	@FXML
	private void addTagButtonClick(ActionEvent event) {
		if (tagTextField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Tagging Alert");
			alert.setHeaderText(null);
			alert.setContentText("A tag can't be empty");

			alert.showAndWait();
		}
		String toAdd = tagTextField.getText();
		this.controller.getTagIndex().tagSnippet(toAdd, selected);
		this.updateTagComboBox();
	}

	@FXML
	private void newSnippetButtonClick(ActionEvent event) {
		CodeSnippet newSnippet = new CodeSnippet("Enter name here...", "", "");
		this.controller.storeCodeSnippet(newSnippet);
		this.snippetListView.getSelectionModel().select(newSnippet);
		this.updateView(null);
		this.snippetNameTextField.requestFocus();
		this.snippetNameTextField.selectAll();
	}

	@FXML
	private void deleteSnippetButtonClick(ActionEvent event) {
		this.controller.removeCodeSnippet(this.selected);
		this.updateView(null);
	}

	@FXML
	private void deleteTagButtonClick(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Press OK to save changes, Cancel to discard.");
		alert.setTitle("Remove tag?");
		alert.setHeaderText("Are you sure you would like to remove this tag?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.controller.getTagIndex().untagSnippet(this.tagComboBox.getValue().getValue(), this.selected);
			this.updateTagComboBox();
		}

	}
	@FXML
	private void purgeTagButtonClick() {
		
	}
	@FXML
	private void saveSnippetButtonClick(ActionEvent event) {
		this.selected.getCode().setCodeText(this.snippetEditor.getHtmlText());
		this.controller.storeCodeSnippet(this.selected);
		this.saveSnippetButton.setDisable(true);
	}

	@FXML
	private void onSnippetEdited(Event event) {
		this.saveSnippetButton.setDisable(false);
	}

	@FXML
	private void onSearchFieldEdited(Event event) {
		this.controller.filterListWith(this.searchTextField.getText());
		this.snippetListView.setItems(this.controller.getObservableList());
	}

}
