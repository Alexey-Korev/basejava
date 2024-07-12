package ru.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String website;
    private List<Period> periods = new ArrayList<>();

    public Company(String title, String website, Period... periods) {
        Objects.requireNonNull(title, "title can't be null");
        this.title = title;
        this.website = website;
        this.periods.addAll(Arrays.asList(periods));
    }

    public Company() {}

    public Company(String title, String website, List<Period> periods) {
        this.title = title;
        this.website = website;
        this.periods = periods;
    }


    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(title, company.title) && Objects.equals(website, company.website) && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, website, periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "title='" + title + '\'' +
                ", website='" + website + '\'' +
                ", periods=" + periods +
                '}';
    }
}
