package ru.basejava.webapp.web;

import ru.basejava.Config;
import ru.basejava.webapp.model.*;
import ru.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(type, new StringSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        String[] companyNames = request.getParameterValues(type.name() + "CompanyName");
                        String[] companyWebsites = request.getParameterValues(type.name() + "CompanyWebsite");

                        List<Company> companies = new ArrayList<>();
                        if (companyNames != null) {
                            for (int i = 0; i < companyNames.length; i++) {
                                String companyName = companyNames[i];
                                String companyWebsite = companyWebsites[i];

                                // company's period
                                String[] titles = request.getParameterValues(type.name() + i + "PeriodTitle");
                                String[] descriptions = request.getParameterValues(type.name() + i + "PeriodDescription");
                                String[] startDates = request.getParameterValues(type.name() + i + "PeriodStartDate");
                                String[] endDates = request.getParameterValues(type.name() + i + "PeriodEndDate");

                                List<Period> periods = new ArrayList<>();
                                if (titles != null) {
                                    for (int j = 0; j < titles.length; j++) {
                                        String title = titles[j];
                                        String description = descriptions[j];
                                        LocalDate startDate = LocalDate.parse(startDates[j]);
                                        LocalDate endDate = LocalDate.parse(endDates[j]);
                                        periods.add(new Period(title, description, startDate, endDate));
                                    }
                                }
                                companies.add(new Company(companyName, companyWebsite, periods.toArray(new Period[0])));
                            }
                        }
                        r.addSection(type, new CompanySection(companies));
                        break;
                }
            } else {
                r.getSections().remove(type);
            }

        }
        storage.update(r);
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
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
