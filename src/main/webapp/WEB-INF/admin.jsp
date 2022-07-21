<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>
<%--<%@ page import="java.sql.*" %>--%>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Admin
    </jsp:attribute>

    <jsp:attribute name="footer">
            Admin
    </jsp:attribute>

    <jsp:body>
        <c:if test="${sessionScope.user.role == 'admin'}">

            <h2>1 Opret svømmedagene:</h2>
            skriv dagene ind med formatet YYYY-MM-DD og ugenr og om det er et lige eller ulige hold og så opretter systemet begge hold på dagen
            <form action=${pageContext.request.contextPath}/fc/createSwimdays>
                <label for="date">Dato:</label>
                <input type="date" id="date" name="date"><br><br>
                <label for="weekno">Uge nr:</label>
                <input type="number" id="weekno" name="weekno"><br><br>
                <label for="week">lige / ulige hold:</label>
                <select name="week" id="week">
                    <option value="l">lige</option>
                    <option value=u">ulige</option>
                </select><br><br>
                <input type="hidden" name="command" value="createSwimdays"/>
                <input type="submit" value="Send">
            </form>
            <br><br>


            <h2>2 Opret brugerne:</h2>
            Systemet fylder i family-tabellen også
Familie-ID:         Navn:       email:      tlf:        password:       primær bruger:
            <form action=${pageContext.request.contextPath}/fc/createUser>
                <label for="family">Familie-ID:</label>
                <input type="number" id="family" name="family"><br><br>
                <label for="name">Navn:</label>
                <input type="text" id="name" name="name"><br><br>
                <label for="email">E-mail:</label>
                <input type="text" id="email" name="email"><br><br>
                <label for="phone">Tlf nr:</label>
                <input type="text" id="phone" name="phone"><br><br>
                <label for="password">Password:</label>
                <input type="text" id="password" name="password"><br><br>
                <label for="primary">Primær bruger?:</label>
                <select name="primary" id="primary">
                    <option value="yes">Ja</option>
                    <option value="no">Nej</option>
                </select><br><br>
                <input type="hidden" name="command" value="createUser"/>
                <input type="submit" value="Send">
            </form>

            <br><br>

            <h2>3 Opret registreringer:</h2>
            Så giver systemet automatisk familierne det rigtige antal billetter.

            <form action=${pageContext.request.contextPath}/fc/createRegistration>
                <label for="familyid">Familie-ID:</label>
                <input type="number" id="familyid" name="familyid"><br><br>
                <label for="team">Hold-ID:</label>
                <select name="team" id="team">
                    <option value="lige uge fre 9-10">lige uge fre 9-10</option>
                    <option value="lige uge fre 10-11">lige uge fre 10-11</option>
                    <option value="ulige uge fre 9-10">ulige uge fre 9-10</option>
                    <option value="ulige uge fre 10-11">ulige uge fre 10-11</option>
                </select><br><br>
                <label for="amount">Antal billetter:</label>
                <input type="number" id="amount" name="amount"><br><br>
                <input type="hidden" name="command" value="createRegistration"/>
                <input type="submit" value="Send">
            </form>
            <br><br>

        </c:if>
    </jsp:body>
</t:pagetemplate>
