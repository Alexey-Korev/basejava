package ru.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private final String title;
    private final String website;
    private final List<Period> period;

    public Company(String title, String website, List<Period> period) {
        Objects.requireNonNull(title, "title can't be null");
        this.title = title;
        this.website = website;
        this.period = period;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriod() {
        return period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(title, company.title) && Objects.equals(website, company.website) && Objects.equals(period, company.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, website, period);
    }

    @Override
    public String toString() {
        return "Company{" +
                "title='" + title + '\'' +
                ", website='" + website + '\'' +
                ", periods=" + period +
                '}';
    }
}
