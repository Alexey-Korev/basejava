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
    <script type="text/javascript">
        function addCompany(sectionType) {
            const container = document.getElementById(sectionType + 'Container');
            const index = container.getElementsByClassName('company').length;
            let companyHtml =
                '<div class="company">' +
                '<label for="' + sectionType + 'CompanyName' + index + '">Название компании</label>' +
                '<input type="text" id="' + sectionType + 'CompanyName' + index + '" name="' + sectionType + 'CompanyName' + index + '" /><br/>' +
                '<label for="' + sectionType + 'CompanyWebsite' + index + '">Вебсайт компании</label>' +
                '<input type="text" id="' + sectionType + 'CompanyWebsite' + index + '" name="' + sectionType + 'CompanyWebsite' + index + '" /><br/>' +
                '<div class="period">';
            for (let periodIndex = 0; periodIndex < 1; periodIndex++) { // Замените 1 на количество периодов, если известно
                companyHtml +=
                    '<label for="' + sectionType + index + 'PeriodTitle' + periodIndex + '">Должность</label>' +
                    '<input type="text" id="' + sectionType + index + 'PeriodTitle' + periodIndex + '" name="' + sectionType + index + 'PeriodTitle' + periodIndex + '" /><br/>' +
                    '<label for="' + sectionType + index + 'PeriodDescription' + periodIndex + '">Описание</label>' +
                    '<textarea id="' + sectionType + index + 'PeriodDescription' + periodIndex + '" name="' + sectionType + index + 'PeriodDescription' + periodIndex + '" style="width: 700px; height: 150px;"></textarea><br/>' +
                    '<label for="' + sectionType + index + 'PeriodStartDate' + periodIndex + '">Дата начала</label>' +
                    '<input type="date" id="' + sectionType + index + 'PeriodStartDate' + periodIndex + '" name="' + sectionType + index + 'PeriodStartDate' + periodIndex + '" /><br/>' +
                    '<label for="' + sectionType + index + 'PeriodEndDate' + periodIndex + '">Дата окончания</label>' +
                    '<input type="date" id="' + sectionType + index + 'PeriodEndDate' + periodIndex + '" name="' + sectionType + index + 'PeriodEndDate' + periodIndex + '" /><br/>';
            }

            companyHtml +=
                '</div>' +
                '</div>';
            container.insertAdjacentHTML('beforeend', companyHtml);
            if (sectionType === 'EXPERIENCE') {
                experienceCount++;
            } else if (sectionType === 'EDUCATION') {
                educationCount++;
            }
        }
    </script>
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
            <label for="${sectionType.name()}"><b>${sectionType.title}</b></label><br/>
            <c:choose>
                <c:when test="${sectionType eq 'PERSONAL' || sectionType eq 'OBJECTIVE'}">
                    <textarea style="width: 700px; height: 150px;" id="${sectionType.name()}" name="${sectionType.name()}"><c:out value="${section != null ? section.text : ''}"/></textarea><br/>
                </c:when>
                <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                    <textarea style="width: 700px; height: 150px;" id="${sectionType.name()}" name="${sectionType.name()}"><c:forEach var="item" items="${section != null ? section.text : ''}">${item}</c:forEach></textarea><br/>
                    </c:when>
                <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <div id="${sectionType.name()}Container">
                            <c:forEach var="company" items="${section != null ? section.companies : ''}" varStatus="companyStatus">
                            <div class="company">
                                <label for="${sectionType.name()}CompanyName${companyStatus.index}">Название компании</label>
                                <input type="text" id="${sectionType.name()}CompanyName${companyStatus.index}" name="${sectionType.name()}CompanyName${companyStatus.index}" value="${company.title}" /><br/>
                                <label for="${sectionType.name()}CompanyWebsite${companyStatus.index}">Вебсайт компании</label>
                                <input type="text" id="${sectionType.name()}CompanyWebsite${companyStatus.index}" name="${sectionType.name()}CompanyWebsite${companyStatus.index}" value="${company.website}" /><br/>
                                <c:forEach var="period" items="${company.periods}" varStatus="loop">
                                    <div class="period">
                                        <label for="${sectionType.name()}${companyStatus.index}PeriodTitle${loop.index}">Должность</label>
                                        <input type="text" id="${sectionType.name()}${companyStatus.index}PeriodTitle${loop.index}" name="${sectionType.name()}${companyStatus.index}PeriodTitle${loop.index}" value="${period.title}" /><br/>
                                        <label for="${sectionType.name()}${companyStatus.index}PeriodDescription${loop.index}">Описание</label>
                                        <textarea id="${sectionType.name()}${companyStatus.index}PeriodDescription${loop.index}" name="${sectionType.name()}${companyStatus.index}PeriodDescription${loop.index}" style="width: 700px; height: 150px;">${period.description}</textarea><br/>
                                        <label for="${sectionType.name()}${companyStatus.index}PeriodStartDate${loop.index}">Дата начала</label>
                                        <input type="date" id="${sectionType.name()}${companyStatus.index}PeriodStartDate${loop.index}" name="${sectionType.name()}${companyStatus.index}PeriodStartDate${loop.index}" value="${period.startDate}" /><br/>
                                        <label for="${sectionType.name()}${companyStatus.index}PeriodEndDate${loop.index}">Дата окончания</label>
                                        <input type="date" id="${sectionType.name()}${companyStatus.index}PeriodEndDate${loop.index}" name="${sectionType.name()}${companyStatus.index}PeriodEndDate${loop.index}" value="${period.endDate}" /><br/>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" onclick="addCompany('${sectionType.name()}')">Добавить ${sectionType.name() == 'EXPERIENCE' ? 'опыт работы' : 'образование'}</button><br/>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
