package ru.basejava;

import ru.basejava.webapp.model.*;

import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = resumeCreate("fsdf314", "Alex Korev");


        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println("\n" + entry.getKey() + " : " + entry.getValue());
        }

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            if (entry.getKey() == SectionType.PERSONAL || entry.getKey() == SectionType.OBJECTIVE) {
                System.out.println(entry.getKey() + " : " + ((StringSection) entry.getValue()).getText());
            }
            if (entry.getKey() == SectionType.QUALIFICATIONS || entry.getKey() == SectionType.ACHIEVEMENT) {
                System.out.println("\n" + entry.getKey());
                AbstractSection section = entry.getValue();
                for (String str : ((ListSection) section).getText()) {
                    System.out.println(str);
                }
            }
            if (entry.getKey() == SectionType.EXPERIENCE || entry.getKey() == SectionType.EDUCATION) {
                System.out.println("\n" + entry.getKey() + "\n");
                AbstractSection section = entry.getValue();
                for (Company company : ((CompanySection) section).getCompanies()) {
                    System.out.println(company.getTitle() + " " + company.getTitle() + " " +
                            company.getWebsite() + " " +
                            company.getPeriod());
                }

            }
        }

    }

    public static Resume resumeCreate(String uuid, String fullname){
        Resume resume =  new Resume(uuid, fullname);
        //resume contacts
        resume.getContacts().put(ContactType.NUMBER, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "skype:grigory.kislin");
        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin/");
        resume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");

        // resume string section
       /* AbstractSection position = new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        AbstractSection personal = new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.getSections().put(SectionType.OBJECTIVE, position);
        resume.getSections().put(SectionType.PERSONAL, personal);

        //resume list section
        List<String> achievents = new ArrayList<>();
        achievents.add(" ● Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievents.add(" ● С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievents.add(" ● Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievents.add(" ● Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievents.add(" ● Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievents.add(" ● Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievents.add(" ● Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        List<String> qualification = new ArrayList<>();
        qualification.add(" ● JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add(" ● Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add(" ● DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add(" ● Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.add(" ● XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.add(" ● Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualification.add(" ● Python: Django.");
        qualification.add(" ● JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.add(" ● Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.add(" ● Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.\");\n");
        AbstractSection listAchieventsSection = new ListSection(achievents);
        AbstractSection ListQualificationSection = new ListSection(qualification);
        resume.getSections().put(SectionType.ACHIEVEMENT, listAchieventsSection);
        resume.getSections().put(SectionType.QUALIFICATIONS, ListQualificationSection);

        //resume company section
        //experience
        Period javaOnlineProjectsPeriod = new Period("Java Online Projects",
                "Создание, организация и проведение Java онлайн проектов и стажировок.",
                LocalDate.parse("2013-10-01"), LocalDate.now());
        Period wrikePeriod = new Period("Wrike",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.parse("2014-10-01"), LocalDate.parse("2016-01-01"));
        Company javaOps = new Company("java Online ProjectsPeriod", "https://javaops.ru/", javaOnlineProjectsPeriod);
        Company wrike = new Company("Wrike", "https://www.wrike.com/", wrikePeriod);

        List<Company> companies = new ArrayList<>();
        companies.add(javaOps);
        companies.add(wrike);
        AbstractSection companySection = new CompanySection(companies);
        resume.getSections().put(SectionType.EXPERIENCE, companySection);

        //education
        Period coursera = new Period("Coursera",
                "С'Functional Programming Principles in Scala' by Martin Odersky",
                LocalDate.parse("2013-03-01"), LocalDate.parse("2013-05-01"));
        Period luxoft = new Period("Luxoft",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                LocalDate.parse("2011-03-01"), LocalDate.parse("2011-04-01"));
        Company courseraComp = new Company("jCoursera", "https://www.coursera.org/learn/scala-functional-programming",
                coursera);
        Company luxoftComp = new Company("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                luxoft);

        companies.add(courseraComp);
        companies.add(luxoftComp);
        AbstractSection institutionSection = new CompanySection(companies);
        resume.getSections().put(SectionType.EDUCATION, institutionSection);*/
        return resume;
    }
}
