package view;

import controller.MainViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import model.CodeSnippet;

public class MainViewCodeBehind {

    @FXML private ListView<CodeSnippet> snippetListView;
    @FXML private ComboBox<CodeSnippet> filterComboBox;
    @FXML private TextArea outputTextArea;
    @FXML private Label snippetNameLabel;
    @FXML private Accordion detailsAccordian;
    @FXML private TitledPane detailsTitledPane;
    
    private MainViewController controller;
    
    @FXML
    void initialize() {
    	this.controller = new MainViewController("testing.dat");
    	this.snippetListView.setItems(this.controller.getCodeSnippetList());
    	this.snippetListView.getSelectionModel().selectFirst();
    }

}
