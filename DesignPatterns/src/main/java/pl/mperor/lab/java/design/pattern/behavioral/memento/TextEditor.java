package pl.mperor.lab.java.design.pattern.behavioral.memento;

class TextEditor {

    private StringBuilder content = new StringBuilder();

    TextEditor write(String text) {
        content.append(text);
        return this;
    }

    TextMemento save() {
        return new TextMemento(content.toString());
    }

    void restore(TextMemento memento) {
        content = new StringBuilder(memento.content());
    }

    String getContent() {
        return content.toString();
    }
}
