<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<ru.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
    <h3>${sectionEntry.key.title}</h3>
    <c:choose>
        <c:when test="${sectionEntry.value['class'].name eq 'ru.basejava.webapp.model.StringSection'}">
            <p>${sectionEntry.value.text}</p>
        </c:when>
        <c:when test="${sectionEntry.value['class'].name eq 'ru.basejava.webapp.model.ListSection'}">
            <ul>
                <c:forEach var="item" items="${sectionEntry.value.text}">
                    <li>${item}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:when test="${sectionEntry.value['class'].name eq 'ru.basejava.webapp.model.CompanySection'}">
            <c:forEach var="company" items="${sectionEntry.value.companies}">
                <h4>${company.title}</h4>
                <p><a href="${company.website}" target="_blank">${company.website}</a></p>
                <ul>
                    <c:forEach var="period" items="${company.period}">
                        <li>${period.startDate} - ${period.endDate}: ${period.title}</li>
                        <p>${period.description}</p>
                    </c:forEach>
                </ul>
            </c:forEach>
        </c:when>
    </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
