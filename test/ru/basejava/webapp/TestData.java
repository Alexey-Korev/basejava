package ru.basejava.webapp;

import ru.basejava.ResumeTestData;
import ru.basejava.webapp.model.Resume;

public class TestData {
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";
    public static final String UUID_NOT_EXIST = "dummy";
    public static final String FULLNAME_1 = "name1";
    public static final String FULLNAME_2 = "name2";
    public static final String FULLNAME_3 = "name3";
    public static final String FULLNAME_4 = "name4";
    public static final String FULLNAME_NOT_EXIST = "dummy_name";

    public static final Resume RESUME1;
    public static final Resume RESUME2;
    public static final Resume RESUME3;
    public static final Resume RESUME4;

    static {
        RESUME1 = ResumeTestData.resumeCreate(UUID_1, FULLNAME_1);
        RESUME2 = ResumeTestData.resumeCreate(UUID_2, FULLNAME_2);
        RESUME3 = ResumeTestData.resumeCreate(UUID_3, FULLNAME_3);
        RESUME4 = ResumeTestData.resumeCreate(UUID_4, FULLNAME_4);
    }
}
