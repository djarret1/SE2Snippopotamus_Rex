package model;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class CodeSnippet {
	
	private StringProperty name;
	private StringProperty description;
	private StringProperty code;
	
	public static Callback<CodeSnippet, Observable[]> extractor() {
        return (s) -> new Observable[] {s.getNameProperty(), s.getDescriptionProperty(), s.getCodeProperty()};
    }
	
	public CodeSnippet(String name, String description, String code) {
		this.name = new SimpleStringProperty(Objects.requireNonNull(name, "Name was null."));
		this.description = new SimpleStringProperty(Objects.requireNonNull(description, "Description was null."));
		this.code = new SimpleStringProperty(Objects.requireNonNull(code, "Code was null."));
	}

	public final String getName() {
		return this.name.get();
	}
	
	public final void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty getNameProperty() {
		return this.name;
	}
	
	public final String getDescription() {
		return this.description.get();
	}
	
	public final void setDescription(String description) {
		this.description.set(description);
	}
	
	public StringProperty getDescriptionProperty() {
		return this.description;
	}
	
	public final String getCode() {
		return this.code.get();
	}
	
	public final void setCode(String code) {
		this.code.set(code);
	}
	
	public StringProperty getCodeProperty() {
		return this.code;
	}
	
	@Override
	public String toString() {
		return this.getName() + " - " + this.getDescription();
	}
	
}
