package pl.mperor.lab.java.lang;

import java.io.Serializable;

/**
 * Designed for frameworks (JSP, JSF, etc.)
 */
public class JavaBean implements Serializable {  // Serializable:

    // Optional but Recommended
    // This prevents deserialization issues when class structure changes.
    private static final long serialVersionUID = 1L;

    // Private fields:
    private String stringField;
    private int primitiveIntField;

    // No-argument constructor:
    public JavaBean() {
    }

    public JavaBean(String stringField, int primitiveIntField) {
        this.stringField = stringField;
        this.primitiveIntField = primitiveIntField;
    }

    // Getter and Setter methods:
    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public int getPrimitiveIntField() {
        return primitiveIntField;
    }

    public void setPrimitiveIntField(int primitiveIntField) {
        this.primitiveIntField = primitiveIntField;
    }
}
