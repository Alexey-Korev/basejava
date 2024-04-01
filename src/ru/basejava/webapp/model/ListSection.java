package ru.basejava.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection{
    private static final long serialVersionUID = 1L;
    private final List<String> text;

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> text) {
        Objects.requireNonNull(text);
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "text=" + text +
                '}';
    }
}
