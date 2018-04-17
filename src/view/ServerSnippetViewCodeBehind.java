package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import controller.MainViewController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import model.CodeSnippet;
import model.Server;

/**
 * Code-behind file for the main view.
 * 
 * @author Ras Fincher and David Jarrett
 * @version 4/16/2018
 */
public class ServerSnippetViewCodeBehind {

	private static final String DATA_STORE_FILE = "data.dat";
	
    @FXML private ListView<CodeSnippet> snippetListView;
    @FXML private ListView<CodeSnippet> serverSnippetListView;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private TextArea outputTextArea;
    @FXML private TitledPane detailsTitledPane;
    @FXML private HTMLEditor snippetEditor;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button saveSnippetButton;
    @FXML private Button selectSnippetButton;
    @FXML private Button clearAllSelectedSnippetsButton;
    @FXML private Button addToMySnippetsButton;
    @FXML private TextField snippetNameTextField;
    @FXML private TextField searchTextField;
    @FXML private TextField tagTextField;
    @FXML private ComboBox<String> tagComboBox;
    @FXML private Button clearTagFilterButton;
    
    private MainViewController controller;
    private MainViewCodeBehind mainViewCodeBehind;
    private CodeSnippet selected;
    private ObservableList<CodeSnippet> selectedSnippets;
    private Server snipRexServer;
    
    @FXML
    private void initialize() {
    	this.controller = new MainViewController(DATA_STORE_FILE);
    	this.mainViewCodeBehind = null;
    	this.snipRexServer = new Server();
    	this.selected = null;
    	this.initializeListView();
    	this.updateFilterComboBox();
    	this.initializeListeners();
    	this.updateView(null);
    	this.selectedSnippets = FXCollections.observableArrayList(CodeSnippet.extractor());
    }
    
	private void initializeListView() {
		List<CodeSnippet> snippetsFromServer = this.snipRexServer.getAllSnippetsFromServer();
		ObservableList<CodeSnippet> observableSnippetsFromServer = FXCollections.observableArrayList(snippetsFromServer);
		this.snippetListView.setItems(observableSnippetsFromServer);
		this.snippetListView.getSelectionModel().selectFirst();
	}

	private void updateFilterComboBox() {
		this.filterComboBox.setItems(this.controller.getAllExistingTags());		
	}
	
	private void initializeListeners() {
		ChangeListener<Boolean> updateSnippetOnLoseFocus = (observable, oldState, hasFocus) -> {
			if (!hasFocus && this.selected != null) {
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
		}
		this.selected = this.snippetListView.selectionModelProperty().getValue().getSelectedItem();
		if (this.selected != null) {
			this.snippetNameTextField.textProperty().bindBidirectional(this.selected.getNameProperty());
			this.descriptionTextArea.textProperty().bindBidirectional(this.selected.getDescriptionProperty());
			this.snippetEditor.setHtmlText(this.selected.getCode().getCodeText());
		}
	}
	
	@FXML
	private void onSearchFieldEdited(Event event) {
		this.controller.filterListWith(this.searchTextField.getText());
		this.snippetListView.setItems(this.controller.getObservableList());
	}
	
	@FXML
	void onSelectFilterTag(ActionEvent event) {
		String filterString = this.filterComboBox.selectionModelProperty().getValue().getSelectedItem();
		this.controller.filterListWithTag(filterString);
		this.snippetListView.setItems(this.controller.getObservableList());
		this.snippetListView.getSelectionModel().selectFirst();
	}
	
	@FXML
    void clearTagFilter(ActionEvent event) {
		this.searchTextField.setText("");
		this.onSearchFieldEdited(null);
    }
	
	@FXML
	void selectSnippetButtonPressed(ActionEvent event) {
		if (this.selectedSnippets.contains(this.selected)) {
			return;
		}
		this.selectedSnippets.add(this.selected);
		this.serverSnippetListView.setItems(this.selectedSnippets);
	}
	
	@FXML
	void clearAllButtonPressed(ActionEvent event) {
		this.selectedSnippets.clear();
		this.serverSnippetListView.setItems(this.selectedSnippets);
	}
	
	@FXML
	void onAddToMySnippetsButtonPressed(ActionEvent event) {
		for (CodeSnippet snippet : this.selectedSnippets) {
			this.mainViewCodeBehind.getController().getObservableList().add(snippet);
		}
		this.mainViewCodeBehind.getController().writeAllCodeSnippetsToDataStore();
		Stage stage = (Stage) this.addToMySnippetsButton.getScene().getWindow();
		stage.close();
		
	}

	/**
	 * Allows you to set the MainViewCodeBehind so that the list may be passed back to the MainView
	 * 
	 * @param mainViewCodeBehind the MainViewCodeBehind
	 * 
	 * @precondition mainViewCodeBehind != null
	 * 
	 * @postcondition this.mainViewCodeBehind == mainViewCodeBehind
	 */
	public void setMainViewCodeBehind(MainViewCodeBehind mainViewCodeBehind) {
		Objects.requireNonNull(mainViewCodeBehind, "mainViewCodeBehind cannot be null");
		this.mainViewCodeBehind = mainViewCodeBehind;
	}	
	
	public MainViewController getController() {
		return this.controller;
	}
}
