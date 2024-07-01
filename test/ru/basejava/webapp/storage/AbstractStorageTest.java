package ru.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.Config;
import ru.basejava.webapp.exception.ExistStorageException;
import ru.basejava.webapp.exception.NotExistStorageException;
import ru.basejava.webapp.model.*;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.basejava.webapp.TestData.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    public void clear() {
        Resume[] expected = {};
        storage.clear();
        assertSize(0);
        Assert.assertArrayEquals(storage.getAllSorted().toArray(), expected);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "New Name");
        newResume.getContacts().put(ContactType.NUMBER, "+7(921) 855-0482");
        newResume.getContacts().put(ContactType.SKYPE, "skype:grigory.kislin");
        newResume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        newResume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        newResume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        newResume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");

        AbstractSection position = new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        AbstractSection personal = new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        newResume.getSections().put(SectionType.OBJECTIVE, position);
        newResume.getSections().put(SectionType.PERSONAL, personal);
        List<String> achievents = new ArrayList<>();
        achievents.add("● Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievents.add("● С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievents.add("● Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievents.add("● Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievents.add("● Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievents.add("● Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievents.add("● Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        List<String> qualification = new ArrayList<>();
        qualification.add("● JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("● Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("● DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add("● Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add("● XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add("● Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add("● Python: Django.");
        qualification.add("● JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.add("● Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.add("● Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.\");");
        AbstractSection listAchieventsSection = new ListSection(achievents);
        AbstractSection ListQualificationSection = new ListSection(qualification);
        newResume.getSections().put(SectionType.ACHIEVEMENT, listAchieventsSection);
        newResume.getSections().put(SectionType.QUALIFICATIONS, ListQualificationSection);

        Period javaOnlineProjectsPeriod = new Period("Java Online Projects",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                LocalDate.of(2013, Month.OCTOBER, 1), LocalDate.now());
        Period wrikePeriod = new Period("Wrike",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2013, Month.OCTOBER, 1), LocalDate.of(2014, Month.OCTOBER, 1));
        Company javaOps = new Company("java Online ProjectsPeriod", "https://javaops.ru/", javaOnlineProjectsPeriod);
        Company wrike = new Company("Wrike", "https://www.wrike.com/", wrikePeriod);

        List<Company> companies = new ArrayList<>();
        companies.add(javaOps);
        companies.add(wrike);
        AbstractSection companySection = new CompanySection(companies);
        newResume.getSections().put(SectionType.EXPERIENCE, companySection);

        //education
        Period coursera = new Period("Coursera",
                "С'Functional Programming Principles in Scala' by Martin Odersky",
                LocalDate.of(2013, Month.MARCH, 1), LocalDate.of(2013, Month.MAY, 1));
        Period luxoft = new Period("Luxoft",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                LocalDate.of(2011, Month.MAY, 1), LocalDate.of(2013, Month.APRIL, 1));
        Company courseraComp = new Company("jCoursera", "https://www.coursera.org/learn/scala-functional-programming",
                coursera);
        Company luxoftComp = new Company("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                luxoft);

        companies.add(courseraComp);
        companies.add(luxoftComp);
        AbstractSection institutionSection = new CompanySection(companies);
        newResume.getSections().put(SectionType.EDUCATION, institutionSection);

        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_NOT_EXIST, FULLNAME_NOT_EXIST));
    }


    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME1);
    }

    @Test
    public void save() {
        storage.save(RESUME4);
        assertGet(RESUME4);
        assertSize(4);
    }

    @Test
    public void get() {
        assertGet(RESUME1);
        assertGet(RESUME2);
        assertGet(RESUME3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        assertSize(3);
        assertGet(RESUME1);
        storage.delete(UUID_1);
        assertSize(2);
        assertGet(RESUME1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() {
        List<Resume> allSorted = storage.getAllSorted();
        Assert.assertEquals(3, allSorted.size());
        Assert.assertEquals(Arrays.asList(RESUME1, RESUME2, RESUME3), allSorted);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}