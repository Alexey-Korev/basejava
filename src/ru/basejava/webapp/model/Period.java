package ru.basejava.webapp.model;

import com.google.gson.annotations.JsonAdapter;
import ru.basejava.webapp.util.JsonSectionAdapter;
import ru.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String title;
    private final String description;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(JsonSectionAdapter.class)
    private final LocalDate startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonAdapter(JsonSectionAdapter.class)
    private final LocalDate endDate;

    public Period(String title, String description, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(title, "Title can't be null");
        Objects.requireNonNull(startDate, "StartDate can't be null");
        Objects.requireNonNull(endDate, "EndDate can't be null");
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Period{" +
                "name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
