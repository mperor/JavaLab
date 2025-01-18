package pl.mperor.lab.java.design.pattern.structural.composite;

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
        List<String> paths = root.find(name -> name.contains("css"));
        System.out.println(paths);
    }

}