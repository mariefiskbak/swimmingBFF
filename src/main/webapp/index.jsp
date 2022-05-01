<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
         Svømmeside Bornholms Frie Familieklub
    </jsp:attribute>

    <jsp:attribute name="footer">
        Svømmeside Bornholms Frie Familieklub
    </jsp:attribute>

    <jsp:body>

        <c:if test="${sessionScope.user != null}">
            <p>You are logged in with the role of "${sessionScope.user.role}".</p>
        </c:if>

        <c:if test="${sessionScope.user == null}">
            <p>Log ind her: <a
                    href="login.jsp">Login</a></p>
        </c:if>

        <h2>Svømmedage</h2>
        <br>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Dato</th>
                    <th scope="col">Uge nr</th>
                    <th scope="col">Hold Id</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="swimday" items="${sessionScope.swimdayList}">
                    <tr>
                            <td>${swimday.swimday}</td>
                            <td>${swimday.weekNo}</td>
                            <td>${swimday.team}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </jsp:body>

</t:pagetemplate>