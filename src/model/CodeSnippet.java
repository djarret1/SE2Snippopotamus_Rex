package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

/**
 * Represents a CodeSnippet.
 * 
 * @author David Jarrett & Andrew Weems
 * @version 2/12/2018
 */
public class CodeSnippet {

	private StringProperty name;
	private StringProperty description;
	private ObjectProperty<Code> code;
	private List<StringProperty> tags;

	/**
	 * Any ObservableList created with this callback will automatically refresh
	 * itself when its list items change by reading each property in the CodeSnippet
	 * that changed. Pass as a parameter to an ObservableList constructor.
	 * 
	 * @return A lambda expression that refreshes the list.
	 */
	public static Callback<CodeSnippet, Observable[]> extractor() {
		return (s) -> new Observable[] { s.getNameProperty(), s.getDescriptionProperty(), s.getCodeProperty() };
	}

	/**
	 * Initializes a new CodeSnippet.
	 * 
	 * @preconditions: name != null && description != null && codeText != null
	 * @postconditions: Object will be initialized and ready for use.
	 * @param name
	 *            The name of the CodeSnippet.
	 * @param description
	 *            A description of what the CodeSnippet is/does.
	 * @param codeText
	 *            The actual code stored in the CodeSnippet.
	 */
	public CodeSnippet(String name, String description, String codeText) {
		this.name = new SimpleStringProperty(Objects.requireNonNull(name, "Name was null."));
		this.description = new SimpleStringProperty(Objects.requireNonNull(description, "Description was null."));
		Code theCode = new Code(Objects.requireNonNull(codeText, "Code text was null."));
		this.code = new SimpleObjectProperty<>(theCode);
		this.tags = new ArrayList<StringProperty>();
	}

	/**
	 * Gets the name of the CodeSnippet.
	 * 
	 * @preconditions: None
	 * @return The name of the CodeSnippet.
	 */
	public final String getName() {
		return this.name.get();
	}

	/**
	 * Sets the name of the CodeSnippet.
	 * 
	 * @preconditions: name != null
	 * @param name
	 *            The new name of the CodeSnippet.
	 */
	public final void setName(String name) {
		this.name.set(Objects.requireNonNull(name, "Name was null."));
	}

	/**
	 * Gets the name property of the CodeSnippet.
	 * 
	 * @preconditions: None
	 * @return The name property of the CodeSnippet.
	 */
	public StringProperty getNameProperty() {
		return this.name;
	}

	/**
	 * Gets the description of the CodeSnippet.
	 * 
	 * @preconditions: None
	 * @return The description of the CodeSnippet.
	 */
	public final String getDescription() {
		return this.description.get();
	}

	/**
	 * Sets the description of the CodeSnippet.
	 * 
	 * @preconditions: description != null
	 * @param description
	 *            The new description of the CodeSnippet.
	 */
	public final void setDescription(String description) {
		this.description.set(Objects.requireNonNull(description, "Description was null."));
	}

	/**
	 * Gets the description property of the CodeSnippet.
	 * 
	 * @preconditions: None
	 * @return The description property of the CodeSnippet.
	 */
	public StringProperty getDescriptionProperty() {
		return this.description;
	}

	/**
	 * Gets the Code object of this CodeSnippet.
	 * 
	 * @preconditions: None
	 * @return The Code object of this snippet.
	 */
	public final Code getCode() {
		return this.code.get();
	}

	/**
	 * Sets the Code object of this CodeSnippet.
	 * 
	 * @preconditions: code != null
	 * @param code
	 *            The new Code object.
	 */
	public final void setCode(Code code) {
		this.code.set(Objects.requireNonNull(code, "The code was null."));
	}

	/**
	 * Gets the code property of this CodeSnippet.
	 * 
	 * @preconditions: None
	 * @return The code property of this CodeSnippet.
	 */
	public ObjectProperty<Code> getCodeProperty() {
		return this.code;
	}

	/**
	 * Returns a String representation of this CodeSnippet.
	 * 
	 * @preconditions: None
	 * @return A String consisting of the name of this CodeSnippet.
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	public void addTag(String tag) {
		if (this.tags == null) {
			throw new IllegalStateException("The code snippet has not been initialized.");
		}
		if (tag == null) {
			throw new IllegalArgumentException("The provided tag is invalid");
		}
		if(!containsTag(tag)) {
			this.tags.add(new SimpleStringProperty(tag));
		}
	}
	public void removeTag(String tag) {
		
	}
	
	private boolean containsTag(String tag) {
		boolean toReturn = false;
		for (int i = 0; i < tags.size(); i++) {
			if (this.tags.get(i).toString().equals(tag)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
}