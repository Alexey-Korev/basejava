package ru.basejava.webapp.model;

public enum ContactType {
    NUMBER("Телефонный номер"),
    SKYPE("Скайп"),
    MAIL("Почта"),
    LINKEDIN("Профиль Linkedin"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
