package pl.mperor.lab.java.generic;

public class Box<T> {
    private T content;

    public Box<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public T getContent() {
        return content;
    }
}
