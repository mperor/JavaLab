package pl.mperor.lab.java;

import java.io.Serializable;

/**
 * Designed for frameworks (JSP, JSF, etc.)
 */
class JavaBean implements Serializable {  // Serializable:

    // Optional but Recommended
    // This prevents deserialization issues when class structure changes.
    private static final long serialVersionUID = 1L;

    // Private fields:
    private String stringField;
    private int primitiveIntField;

    // No-argument constructor:
    JavaBean() {
    }

    JavaBean(String stringField, int primitiveIntField) {
        this.stringField = stringField;
        this.primitiveIntField = primitiveIntField;
    }

    // Getter and Setter methods:
    String getStringField() {
        return stringField;
    }

    void setStringField(String stringField) {
        this.stringField = stringField;
    }

    int getPrimitiveIntField() {
        return primitiveIntField;
    }

    void setPrimitiveIntField(int primitiveIntField) {
        this.primitiveIntField = primitiveIntField;
    }
}
