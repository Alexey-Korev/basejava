package ru.basejava.webapp.web;

import ru.basejava.Config;
import ru.basejava.webapp.model.*;
import ru.basejava.webapp.storage.Storage;
import ru.basejava.webapp.util.ResumeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL driver", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid == null || uuid.isEmpty()) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        // Обработка контактов
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        // Обработка секций
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> r.addSection(type, new StringSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            r.addSection(type, new ListSection(Arrays.asList(value.split("\n"))));

                    /*case EXPERIENCE:
                    case EDUCATION:
                        String[] companyNames = request.getParameterValues(type.name() + "CompanyName");
                        String[] companyWebsites = request.getParameterValues(type.name() + "CompanyWebsite");

                        List<Company> companies = new ArrayList<>();
                        if (companyNames != null) {
                            for (int i = 0; i < companyNames.length; i++) {
                                String companyName = companyNames[i];
                                String companyWebsite = companyWebsites[i];

                                // Период в компании
                                String[] titles = request.getParameterValues(type.name() + i + "PeriodTitle");
                                String[] descriptions = request.getParameterValues(type.name() + i + "PeriodDescription");
                                String[] startDates = request.getParameterValues(type.name() + i + "PeriodStartDate");
                                String[] endDates = request.getParameterValues(type.name() + i + "PeriodEndDate");

                                List<Period> periods = new ArrayList<>();
                                if (titles != null) {
                                    for (int j = 0; j < titles.length; j++) {
                                        String title = titles[j];
                                        String description = descriptions[j];
                                        LocalDate startDate = startDates != null ? LocalDate.parse(startDates[j]) : null;
                                        LocalDate endDate = endDates != null ? LocalDate.parse(endDates[j]) : null;
                                        periods.add(new Period(title, description, startDate, endDate));
                                    }
                                }
                                companies.add(new Company(companyName, companyWebsite, periods.toArray(new Period[0])));
                            }
                        }
                        if (!companies.isEmpty()) {
                            r.addSection(type, new CompanySection(companies));
                        }
                        break;*/
                }
            } else if (value == null || value.trim().isEmpty()){
                r.getSections().remove(type);
            }
        }
        if (uuid == null || uuid.isEmpty()) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "create" -> {
                r = new Resume();
                request.setAttribute("resume", r);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
            }
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view", "edit" -> {
                r = storage.get(uuid);
                Map<SectionType, AbstractSection> sortedSections = ResumeUtil.sortSections(r.getSections());
                request.setAttribute("resume", r);
                request.setAttribute("sections", sortedSections);
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    /*private Resume getResume(String uuid) {
        return storage.get(uuid);
    }*/
}
