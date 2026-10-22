package pl.mperor.lab.java.design.pattern.structural.composite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FolderCompositeTest {

    @Test
    public void shouldAllowToSearchNames() {
        var root = new Folder("root",
                new Folder("css", new File("main.css"), new File("user.css")),
                new Folder("js", new File("main.js"), new File("user.js")),
                new Folder("fonts", new File("monospace.ttf")),
                new Folder("images", new File("logo.png")),
                new File("index.html")
        );

        Assertions.assertEquals(List.of("root/css", "root/css/main.css", "root/css/user.css"),
                root.find(name -> name.contains("css")));
        Assertions.assertEquals(List.of("root/js/main.js", "root/js/user.js"),
                root.find(name -> name.endsWith(".js")));
        Assertions.assertEquals(List.of(), root.find(name -> name.endsWith(".exe")));
    }

}
