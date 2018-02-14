package view;

import controller.MainViewController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
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
    	this.initializeListView();
    	this.setupEventHandlers();
    	this.updateView(null);
    }

	private void initializeListView() {
		this.snippetListView.setItems(this.controller.getCodeSnippetList());
    	this.snippetListView.setOnMouseClicked(this::updateView);
		this.snippetListView.getSelectionModel().selectFirst();
	}
	
	private void updateView(Event e) {
		if (this.selected != null) {
			this.snippetNameLabel.textProperty().unbindBidirectional(this.selected.getNameProperty());
			this.descriptionTextArea.textProperty().unbindBidirectional(this.selected.getDescriptionProperty());
		}
		this.selected = this.snippetListView.selectionModelProperty().getValue().getSelectedItem();
		this.snippetNameLabel.textProperty().bindBidirectional(this.selected.getNameProperty());
		this.descriptionTextArea.textProperty().bindBidirectional(this.selected.getDescriptionProperty());
		this.snippetEditor.setHtmlText(this.selected.getCode().getCodeText());
	}
	
	private void setupEventHandlers() {
		this.saveSnippetButton.setOnMouseClicked(e -> {
			this.selected.getCode().setCodeText(this.snippetEditor.getHtmlText());
			this.controller.updateCodeSnippet(this.selected);
		});		
	}

}
