package model;

import java.io.Serializable;
import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Code implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StringProperty codeText;
	
	public Code(String codeText) {
		this.codeText = new SimpleStringProperty(Objects.requireNonNull(codeText, "Codetext was null."));
	}
	
	public final String getCodeText() {
		return this.codeText.get();
	}
	
	public final void setCodeText(String codeText) {
		this.codeText.set(Objects.requireNonNull(codeText, "Codetext was null"));
	}
	
	//search methods
	
}
