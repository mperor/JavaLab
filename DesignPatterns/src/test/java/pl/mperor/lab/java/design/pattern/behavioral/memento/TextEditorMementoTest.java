package pl.mperor.lab.java.design.pattern.behavioral.memento;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextEditorMementoTest {

    @Test
    public void testSavingAndRestoringWithTextMemento() {
        var editor = new TextEditor();
        var history = new History();

        editor.write("⭐");
        Assertions.assertEquals("⭐", editor.getContent());
        history.save(editor);

        editor.write("1️⃣").write("2️⃣").write("3️⃣");
        Assertions.assertEquals("⭐1️⃣2️⃣3️⃣", editor.getContent());

        history.undo(editor);
        Assertions.assertEquals("⭐", editor.getContent());
    }
}