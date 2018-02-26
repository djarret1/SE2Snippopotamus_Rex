package view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	private static final String DATA_STORE_FILE = "data.dat";
	
    @FXML private ListView<CodeSnippet> snippetListView;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private TextArea outputTextArea;
    @FXML private TitledPane detailsTitledPane;
    @FXML private HTMLEditor snippetEditor;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button saveSnippetButton;
    @FXML private TextField snippetNameTextField;
    @FXML private TextField searchTextField;
    @FXML private TextField tagTextField;
    @FXML private ComboBox<String> tagComboBox;
    @FXML private Button clearTagFilterButton;
    
    private MainViewController controller;
    private CodeSnippet selected;
    
    @FXML
    private void initialize() {
    	this.controller = new MainViewController(DATA_STORE_FILE);
    	this.selected = null;
    	this.initializeListView();
    	this.updateFilterComboBox();
    	this.initializeListeners();
    	this.updateView(null);
    }

	private void initializeListView() {
		this.snippetListView.setItems(this.controller.getObservableList());
		this.snippetListView.getSelectionModel().selectFirst();
	}

	private void updateFilterComboBox() {
		this.filterComboBox.setItems(this.controller.getAllExistingTags());		
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
		String content = "Press OK to save changes, Cancel to discard.";
		String title = "Save Changes?";
		Optional<ButtonType> result = this.showAlertDialog(AlertType.CONFIRMATION, content, title, title);
		if (result.get() == ButtonType.OK) {
			this.saveSnippetButtonClick(null);
		}
		this.saveSnippetButton.setDisable(true);
	}

	private void updateTagComboBox() {
		this.tagComboBox.getItems().clear();
		Stream<StringProperty> stream = this.selected.getTags().stream();
		List<String> tagNames = stream.map(tag -> tag.get()).collect(Collectors.toList());
		this.tagComboBox.getItems().addAll(tagNames);
		this.tagComboBox.getSelectionModel().selectFirst();
	}

	@FXML
	private void addTagButtonClick(ActionEvent event) {
		if (tagTextField.getText().isEmpty()) {
			String content = "A tag cannot be empty.";
			String title = "Empty Tag";
			this.showAlertDialog(AlertType.WARNING, content, title, null);
		}
		String toAdd = tagTextField.getText();
		this.controller.addTagToSnippet(this.selected, toAdd);
		this.updateTagComboBox();
		this.updateFilterComboBox();
		this.tagTextField.setText("");
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
		String content = "Press OK to save changes, Cancel to discard.";
		String title = "Remove tag?";
		String header = "Are you sure you would like to remove this tag?";
		Optional<ButtonType> result = this.showAlertDialog(AlertType.CONFIRMATION, content, title, header);
		if (result.get() == ButtonType.OK) {
			String tagToRemove = this.tagComboBox.selectionModelProperty().get().getSelectedItem();
			this.controller.removeTagFromSnippet(this.selected, tagToRemove);
			this.updateTagComboBox();
			this.updateFilterComboBox();
		}

	}
	
	@FXML
	private void purgeTagButtonClick() {
        String content = "Press OK to remove all instences of this tag, Cancel to discard.";
        String title = "Purge tag?";
        String header = "Are you sure you would like to remove all instences of this tag?";
        Optional<ButtonType> result = this.showAlertDialog(AlertType.CONFIRMATION, content, title, header);
        if (result.get() == ButtonType.OK) {
            this.controller.purgeTag(this.filterComboBox.getValue());
            this.controller.writeAllCodeSnippetsToDataStore();
            String tag = this.filterComboBox.selectionModelProperty().getValue().getSelectedItem();
        	this.filterComboBox.itemsProperty().get().remove(tag);
        }
    }
	
	private Optional<ButtonType> showAlertDialog(AlertType type, String content, String title, String header) {
		Alert alert = new Alert(type);
		alert.setContentText(content);
		alert.setTitle(title);
		alert.setHeaderText(header);
		return alert.showAndWait();
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
	
	@FXML
	void onSelectFilterTag(ActionEvent event) {
		String filterString = this.filterComboBox.selectionModelProperty().getValue().getSelectedItem();
		this.controller.filterListWithTag(filterString);
		this.snippetListView.setItems(this.controller.getObservableList());
	}
	
	@FXML
    void clearTagFilter(ActionEvent event) {
		this.searchTextField.setText("");
		this.onSearchFieldEdited(null);
    }

}
