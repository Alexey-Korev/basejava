package ru.basejava.webapp.model;

import java.util.Objects;

public class StringSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private String text;

    public StringSection(String text) {
        Objects.requireNonNull(text, "Text can't be empty");
        this.text = text;
    }

    public StringSection() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringSection that = (StringSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "StringSection{" +
                "text='" + text + '\'' +
                '}';
    }
}
