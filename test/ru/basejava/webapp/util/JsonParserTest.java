package ru.basejava.webapp.util;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.webapp.model.Resume;
import ru.basejava.webapp.model.StringSection;

import static ru.basejava.webapp.TestData.RESUME1;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME1);
        System.out.println(json);
        Resume resume2 = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME1, resume2);
    }

    @Test
    public void write() {
        StringSection section1 = new StringSection("Objective1");
        String json = JsonParser.write(section1, StringSection.class);
        System.out.println(json);
        StringSection section2 = JsonParser.read(json, StringSection.class);
        Assert.assertEquals(section1, section2);
    }
}