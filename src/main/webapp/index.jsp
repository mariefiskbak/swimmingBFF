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
        <br>
        <h2>Svømmedage</h2>
        <br>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Dato</th>
                    <th scope="col">Uge nr</th>
                    <th scope="col">Hold ID</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="swimday" items="${sessionScope.swimdayList}">
                    <c:if test="${swimday.team == 'lige uge fre 9-10'}">
                        <tr style="background-color: rgba(18,16,255,0.19)">
                    </c:if>
                    <c:if test="${swimday.team == 'lige uge fre 10-11'}">
                        <tr style="background-color: rgba(50,205,50,0.37)">
                    </c:if>
                    <c:if test="${swimday.team == 'ulige uge fre 9-10'}">
                        <tr style="background-color: rgba(255,255,0,0.38)">
                    </c:if>
                    <c:if test="${swimday.team == 'ulige uge fre 10-11'}">
                        <tr style="background-color: rgba(255,0,0,0.26)">
                    </c:if>
                    <td>${swimday.swimday}</td>
                    <td>${swimday.weekNo}</td>
                    <td>${swimday.team}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <br><br>
        <p>Svømmehallens bookingside: <a
                href="https://www.conventus.dk/dataudv/www/booking.php?idv1=1&idv2=06:00&idv3=22:00&idv4=6925&idv5=3602&navn=skjul&ressourceliste=vis&banebooking=vis&login_boks=vis&handelsbetingelser=vis&engine_error=vis"
                target="_blank">
            Conventus</a></p>
    </jsp:body>

</t:pagetemplate>