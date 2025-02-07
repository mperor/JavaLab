package pl.mperor.lab.java.design.pattern.behavioral.memento;

import java.util.ArrayDeque;
import java.util.Deque;

class History {

    private final Deque<TextMemento> history = new ArrayDeque<>();

    void save(TextEditor editor) {
        history.push(editor.save());
    }

    void undo(TextEditor editor) {
        if (!history.isEmpty()) {
            editor.restore(history.pop());
        }
    }
}
