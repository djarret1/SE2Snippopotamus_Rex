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

	private static final char SPACE = ' ';
	
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
	
	/**
	 * Returns true if this code object contains the supplied queryText in any
	 * position or form, including text buried within substrings.
	 * @param queryText The text being searched for.
	 * @return True if queryText is found anywhere in the code, and false otherwise.
	 */
	public boolean containsText(String queryText) {
		String codeText = this.codeText.get();
		return codeText.contains(queryText);
	}
	
	/**
	 * Returns true if this code object contains the supplied queryText, exactly as
	 * provided. It will not locate text buried within substrings.
	 * @param queryText The text being searched for.
	 * @return True if an exact match is found, and false otherwise.
	 */
	public boolean containsTextExactMatch(String queryText) {
		String codeText = this.codeText.get();
		int index = -1;
		do {
			index = codeText.indexOf(queryText, index + 1);
			if(index != -1 && this.isNotEmbeddedText(codeText, queryText, index)) {
				return true;
			}
		} while (index != -1);
		return false;
	}

	private boolean isNotEmbeddedText(String codeText, String queryText, int index) {
		if (codeText.length() == queryText.length()) {
			return true;
		}
		if (index == 0) {
			return this.checkEnd(codeText, queryText, index);
		} else {
			return this.checkBeginningAndEnd(codeText, queryText, index);
		}
	}

	private boolean checkEnd(String codeText, String queryText, int index) {
		if (index + queryText.length() == codeText.length()) {
			return true;
		} else {
			return codeText.charAt(index + queryText.length()) == SPACE;
		}
	}
	
	private boolean checkBeginningAndEnd(String codeText, String queryText, int index) {
		return codeText.charAt(index - 1) == SPACE && this.checkEnd(codeText, queryText, index);
	}
	
}
