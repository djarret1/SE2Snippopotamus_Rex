package model;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents the code stored in a CodeSnippet.
 * @author 	David Jarrett
 * @version 02/13/2018
 */
public class Code {

	private StringProperty codeText;
	
	/**
	 * Initializes the Code object.
	 * @preconditions: codeText != null
	 * @param codeText The code to be stored in the Code object.
	 */
	public Code(String codeText) {
		this.codeText = new SimpleStringProperty(Objects.requireNonNull(codeText, "Codetext was null."));
	}
	
	/**
	 * Gets the code stored in this Code object.
	 * @preconditions: None
	 * @return The code text stored in this Code object.
	 */
	public final String getCodeText() {
		return this.codeText.get();
	}
	
	/**
	 * Sets the code text stored in this Code object.
	 * @preconditions: codeText != null
	 * @param codeText The code text to be stored in this Code object.
	 */
	public final void setCodeText(String codeText) {
		this.codeText.set(Objects.requireNonNull(codeText, "Codetext was null"));
	}
	
	//search methods
	
}
