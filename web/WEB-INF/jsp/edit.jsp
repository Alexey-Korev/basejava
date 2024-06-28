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
        <div>
            <label for="fullName">Полное имя</label>
            <input type="text" id="fullName" name="fullName" value="${resume.fullName}" required/><br/>
        </div>

        <c:forEach var="contactEntry" items="${resume.contacts}">
            <div>
                <label for="${contactEntry.key.name()}">${contactEntry.key.title}</label>
                <input type="text" id="${contactEntry.key.name()}" name="${contactEntry.key.name()}" value="${contactEntry.value}"/><br/>
            </div>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <div>
                <label>${sectionEntry.key.title}</label><br/>
                <c:choose>
                    <c:when test="${sectionEntry.value['class'].name eq 'ru.basejava.webapp.model.StringSection'}">
                        <textarea style="width: 700px; height: 150px;" id="${sectionEntry.key.name()}" name="${sectionEntry.key.name()}">${sectionEntry.value.text}</textarea><br/>
                    </c:when>
                    <c:when test="${sectionEntry.value['class'].name eq 'ru.basejava.webapp.model.ListSection'}">
                        <textarea style="width: 700px; height: 150px;" id="${sectionEntry.key.name()}" name="${sectionEntry.key.name()}">
                            <c:forEach var="item" items="${sectionEntry.value.text}">
                                ${item}
                            </c:forEach>
                        </textarea><br/>
                    </c:when>
                    <c:when test="${sectionEntry.value['class'].name eq 'ru.basejava.webapp.model.CompanySection'}">
                        <c:forEach var="company" items="${sectionEntry.value.companies}">
                            <div>
                                <label for="companyTitle-${company.hashCode()}">Название компании</label>
                                <input type="text" id="companyTitle-${company.hashCode()}" name="companyTitle-${company.hashCode()}" value="${company.title}" /><br/>
                                <label for="companyWebsite-${company.hashCode()}">Вебсайт компании</label>
                                <input type="text" id="companyWebsite-${company.hashCode()}" name="companyWebsite-${company.hashCode()}" value="${company.website}" /><br/>
                                <c:forEach var="period" items="${company.period}">
                                    <div>
                                        <label for="periodTitle-${period.hashCode()}">Должность</label>
                                        <input type="text" id="periodTitle-${period.hashCode()}" name="periodTitle-${period.hashCode()}" value="${period.title}" /><br/>
                                        <label for="periodDescription-${period.hashCode()}">Описание</label>
                                        <textarea id="periodDescription-${period.hashCode()}" name="periodDescription-${period.hashCode()}" style="width: 700px; height: 150px;">${period.description}</textarea><br/>
                                        <label for="periodStartDate-${period.hashCode()}">Дата начала</label>
                                        <input type="date" id="periodStartDate-${period.hashCode()}" name="periodStartDate-${period.hashCode()}" value="${period.startDate}" /><br/>
                                        <label for="periodEndDate-${period.hashCode()}">Дата окончания</label>
                                        <input type="date" id="periodEndDate-${period.hashCode()}" name="periodEndDate-${period.hashCode()}" value="${period.endDate}" /><br/>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </div>
        </c:forEach>

        <button type="submit">Сохранить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>