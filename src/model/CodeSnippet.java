package model;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class CodeSnippet {
	
	private StringProperty name;
	private StringProperty description;
	private ObjectProperty<Code> code;
	
	public static Callback<CodeSnippet, Observable[]> extractor() {
        return (s) -> new Observable[] {s.getNameProperty(), s.getDescriptionProperty(), s.getCodeProperty()};
    }
	
	public CodeSnippet(String name, String description, String code) {
		this.name = new SimpleStringProperty(Objects.requireNonNull(name, "Name was null."));
		this.description = new SimpleStringProperty(Objects.requireNonNull(description, "Description was null."));
		Code theCode = new Code(code);
		this.code = new SimpleObjectProperty<>(theCode);
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
	
	public final Code getCode() {
		return this.code.get();
	}
	
	public final void setCode(Code code) {
		this.code.set(Objects.requireNonNull(code, "The code was null."));
	}
	
	public ObjectProperty<Code> getCodeProperty() {
		return this.code;
	}
	
	@Override
	public String toString() {
		return this.getName() + " - " + this.getDescription();
	}
	
}
