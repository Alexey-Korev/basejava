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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        if (isExist(uuid)) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            switch (type) {
                case PERSONAL, OBJECTIVE -> {
                    if (value != null && !value.trim().isEmpty()) {
                        r.addSection(type, new StringSection(value));
                    } else {
                        r.getSections().remove(type);
                    }
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    if (value != null && !value.trim().isEmpty()) {
                        List<String> items = Arrays.asList(value.split("\\n"));
                        r.addSection(type, new ListSection(items));
                    } else {
                        r.getSections().remove(type);
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    List<Company> companies = new ArrayList<>();
                    for (int i = 0; ; i++) {
                        String companyName = request.getParameter(type.name() + "CompanyName" + i);
                        if (companyName == null) break;
                        String companyWebsite = request.getParameter(type.name() + "CompanyWebsite" + i);

                        List<Period> periods = new ArrayList<>();
                        for (int j = 0; ; j++) {
                            String title = request.getParameter(type.name() + i + "PeriodTitle" + j);
                            if (title == null) break;
                            String description = request.getParameter(type.name() + i + "PeriodDescription" + j);
                            LocalDate startDate = parseDate(request.getParameter(type.name() + i + "PeriodStartDate" + j));
                            LocalDate endDate = parseDate(request.getParameter(type.name() + i + "PeriodEndDate" + j));
                            periods.add(new Period(title, description, startDate, endDate));
                        }
                        companies.add(new Company(companyName, companyWebsite, periods.toArray(new Period[0])));
                    }
                    if (!companies.isEmpty()) {
                        r.addSection(type, new CompanySection(companies));
                    } else {
                        r.getSections().remove(type);
                    }
                }
            }
        }
        if (isExist(uuid)) {
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

    public boolean isExist(String uuid) {
        return uuid == null || uuid.isEmpty();
    }

    private LocalDate parseDate(String dateStr) {
        return dateStr != null ? LocalDate.parse(dateStr) : null;
    }
}
