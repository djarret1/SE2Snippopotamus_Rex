package view;

import java.util.Optional;

import controller.MainViewController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.web.HTMLEditor;
import model.CodeSnippet;

/**
 * Code-behind file for the main view.
 * @author 	David Jarrett
 * @version	2/13/2018
 */
public class MainViewCodeBehind {

    @FXML private ListView<CodeSnippet> snippetListView;
    @FXML private ComboBox<CodeSnippet> filterComboBox;
    @FXML private TextArea outputTextArea;
    @FXML private Label snippetNameLabel;
    @FXML private TitledPane detailsTitledPane;
    @FXML private HTMLEditor snippetEditor;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button saveSnippetButton;
    @FXML private Button editDetailsButton;
    @FXML private TextField snippetNameTextField;
    
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
		this.snippetListView.setItems(this.controller.getCodeSnippetList());
    	this.snippetListView.getSelectionModel().selectFirst();
	}
	
	private void initializeListeners() {
		this.descriptionTextArea.focusedProperty().addListener((observable, oldState, hasFocus) -> {
			if (!hasFocus) {
				this.controller.storeUpdatedCodeSnippet(this.selected);
			}
		});
	}
	
	@FXML
	private void updateView(Event e) {
		if (this.selected != null) {
			this.snippetNameLabel.textProperty().unbindBidirectional(this.selected.getNameProperty());
			this.descriptionTextArea.textProperty().unbindBidirectional(this.selected.getDescriptionProperty());
			this.updateCodeSaveState();
		}
		this.selected = this.snippetListView.selectionModelProperty().getValue().getSelectedItem();
		this.snippetNameLabel.textProperty().bindBidirectional(this.selected.getNameProperty());
		this.descriptionTextArea.textProperty().bindBidirectional(this.selected.getDescriptionProperty());
		this.snippetEditor.setHtmlText(this.selected.getCode().getCodeText());
	}
	
    private void updateCodeSaveState() {
    	if (this.saveSnippetButton.isDisabled()) {
    		return;
    	}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("You made modifications to the code. Do you want to save them?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.saveSnippetButtonClick(null);
		}
		this.saveSnippetButton.setDisable(true);
	}

	@FXML
    void addTagButtonClick(ActionEvent event) {

    }

    @FXML
    void newSnippetButtonClick(ActionEvent event) {

    }

    @FXML
    void saveSnippetButtonClick(ActionEvent event) {
    	this.selected.getCode().setCodeText(this.snippetEditor.getHtmlText());
		this.controller.storeUpdatedCodeSnippet(this.selected);
		this.saveSnippetButton.setDisable(true);
    }
    
    @FXML
    void onSnippetEdited(Event event) {
    	this.saveSnippetButton.setDisable(false);
    }

}
