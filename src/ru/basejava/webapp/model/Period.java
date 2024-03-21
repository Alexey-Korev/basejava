package ru.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final String title;
    private final String description;
    private final LocalDate startDate;
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
