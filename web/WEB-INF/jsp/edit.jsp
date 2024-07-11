<%@ page import="ru.basejava.webapp.model.ContactType" %>
<%@ page import="ru.basejava.webapp.model.SectionType" %>
<%@ page import="ru.basejava.webapp.model.CompanySection" %>
<%@ page import="ru.basejava.webapp.model.Company" %>
<%@ page import="ru.basejava.webapp.model.Period" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Редактирование резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume">
        <input type="hidden" name="uuid" value="${resume.uuid}"/>
        <label for="fullName">Полное имя</label>
        <input type="text" id="fullName" name="fullName" value="${resume.fullName}" required/><br/>

        <c:forEach var="contactType" items="${ContactType.values()}">
            <label for="${contactType.name()}">${contactType.title}</label>
            <input type="text" id="${contactType.name()}" name="${contactType.name()}" value="${resume.contacts[contactType] != null ? resume.contacts[contactType] : ''}"/><br/>
        </c:forEach>

        <c:forEach var="sectionType" items="${SectionType.values()}">
            <c:set var="section" value="${resume.sections[sectionType]}"/>
            <label for="${sectionType.name()}">${sectionType.title}</label><br/>
            <c:choose>
                <c:when test="${sectionType eq 'PERSONAL' || sectionType eq 'OBJECTIVE'}">
                    <textarea style="width: 700px; height: 150px;" id="${sectionType.name()}" name="${sectionType.name()}"><c:out value="${section != null ? section.text : ''}"/></textarea><br/>
                </c:when>
                <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                    <textarea style="width: 700px; height: 150px;" id="${sectionType.name()}" name="${sectionType.name()}"><c:forEach var="item" items="${section != null ? section.text : ''}">${item}</c:forEach></textarea><br/>
                    </c:when>
                <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <div id="${sectionType.name()}Container">
                        <c:forEach var="company" items="${section.companies}" varStatus="companyStatus">
                            <!-- ${sectionType.name()}CompanyName${companyStatus.index} -->
                            <div class="company">
                                <label for="${sectionType.name()}CompanyName">Название компании</label>
                                <input type="text" id="${sectionType.name()}CompanyName" name="${sectionType.name()}CompanyName" value="${company.title}" /><br/>
                                <label for="${sectionType.name()}CompanyWebsite">Вебсайт компании</label>
                                <input type="text" id="${sectionType.name()}CompanyWebsite" name="${sectionType.name()}CompanyWebsite" value="${company.website}" /><br/>
                                <c:forEach var="period" items="${company.periods}" varStatus="loop">
                                    <div class="period">
                                        <label for="${sectionType.name()}PeriodTitle${loop.index}">Должность</label>
                                        <input type="text" id="${sectionType.name()}PeriodTitle${loop.index}" name="${sectionType.name()}${loop.index}PeriodTitle" value="${period.title}" /><br/>
                                        <label for="${sectionType.name()}PeriodDescription${loop.index}">Описание</label>
                                        <textarea id="${sectionType.name()}PeriodDescription${loop.index}" name="${sectionType.name()}${loop.index}PeriodDescription" style="width: 700px; height: 150px;">${period.description}</textarea><br/>
                                        <label for="${sectionType.name()}PeriodStartDate${loop.index}">Дата начала</label>
                                        <input type="date" id="${sectionType.name()}PeriodStartDate${loop.index}" name="${sectionType.name()}${loop.index}PeriodStartDate" value="${period.startDate}" /><br/>
                                        <label for="${sectionType.name()}PeriodEndDate${loop.index}">Дата окончания</label>
                                        <input type="date" id="${sectionType.name()}PeriodEndDate${loop.index}" name="${sectionType.name()}${loop.index}PeriodEndDate" value="${period.endDate}" /><br/>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" onclick="addCompany('${sectionType.name()}')">Добавить ${sectionType.name() == 'EXPERIENCE' ? 'опыт работы' : 'образование'}</button><br/>
                </c:when>
                <%--<c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <c:forEach var="company" items="${sectionType.value.companies}">
                        <div>
                            <label for="companyName">Название компании</label>
                            <input type="text" id="companyName" name="${sectionEntry.key.name()}CompanyName" value="${company.title}" /><br/>
                            <label for="companyWebsite">Вебсайт компании</label>
                            <input type="text" id="companyWebsite" name="${sectionEntry.key.name()}CompanyWebsite" value="${company.website}" /><br/>
                            <c:forEach var="period" items="${company.period}">
                                <div>
                                    <label for="periodTitle">Должность</label>
                                    <input type="text" id="periodTitle" name="${sectionEntry.key.name()}PeriodTitle" value="${period.title}" /><br/>
                                    <label for="periodDescription">Описание</label>
                                    <textarea id="periodDescription" name="${sectionEntry.key.name()}PeriodDescription" style="width: 700px; height: 150px;">${period.description}</textarea><br/>
                                    <label for="periodStartDate">Дата начала</label>
                                    <input type="date" id="periodStartDate" name="${sectionEntry.key.name()}PeriodStartDate" value="${period.startDate}" /><br/>
                                    <label for="periodEndDate">Дата окончания</label>
                                    <input type="date" id="periodEndDate" name="${sectionEntry.key.name()}PeriodEndDate" value="${period.endDate}" /><br/>
                                </div>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>--%>
            </c:choose>
        </c:forEach>

        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
