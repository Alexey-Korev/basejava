package ru.basejava.webapp.util;

import ru.basejava.webapp.model.AbstractSection;
import ru.basejava.webapp.model.SectionType;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResumeUtil {
    public static Map<SectionType, AbstractSection> sortSections(Map<SectionType, AbstractSection> sections) {
        SectionType[] orderedTypes = {SectionType.OBJECTIVE, SectionType.PERSONAL, SectionType.QUALIFICATIONS, SectionType.ACHIEVEMENT, SectionType.EXPERIENCE, SectionType.EDUCATION};

        Map<SectionType, AbstractSection> sortedSections = new LinkedHashMap<>();
        for (SectionType type : orderedTypes) {
            if (sections.containsKey(type)) {
                sortedSections.put(type, sections.get(type));
            }
        }
        return sortedSections;
    }
}
