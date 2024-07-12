<%@ page import="ru.basejava.webapp.model.ListSection" %>
<%@ page import="ru.basejava.webapp.model.CompanySection" %>
<%@ page import="ru.basejava.webapp.model.StringSection" %>
<%@ page import="ru.basejava.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.basejava.webapp.model.Resume" scope="request"/>
    <jsp:useBean id="sections" type="java.util.Map" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h1>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<ru.basejava.webapp.model.ContactType, java.lang.String>"/>
            <img src="img/${contactEntry.key.name().toLowerCase()}.png" alt="${contactEntry.key.title}" />
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.basejava.webapp.model.SectionType, ru.basejava.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.basejava.webapp.model.AbstractSection"/>
            <tr>
                <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
            </tr>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td colspan="2">
                            <h3><%=((StringSection) section).getText()%></h3>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td colspan="2">
                            <%=((StringSection) section).getText()%>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <tr>
                        <td colspan="2">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getText()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="experienceCompany" items="<%=((CompanySection) section).getCompanies()%>">
                        <tr>
                            <td colspan="2">
                                <h3>${experienceCompany.title}</h3>
                                <c:if test="${not empty experienceCompany.website}">
                                    <p><a href="${experienceCompany.website}" target="_blank">${experienceCompany.website}</a></p>
                                </c:if>
                            </td>
                        </tr>
                        <c:forEach var="experiencePeriod" items="${experienceCompany.periods}">
                            <jsp:useBean id="experiencePeriod" type="ru.basejava.webapp.model.Period"/>
                            <tr>
                                <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(experiencePeriod)%></td>
                                <td><b>${experiencePeriod.title}</b><br>${experiencePeriod.description}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>
    <button onclick="window.history.back()">ОК</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>