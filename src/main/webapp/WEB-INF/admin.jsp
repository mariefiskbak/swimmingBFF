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
        1 Opret svømmedagene:
        skriv dagene ind med formatet YYYY-MM-DD og ugenr og om det er et lige eller ulige hold og så opretter systemet begge hold på dagen
        <form action="fc/createSwimdays">
            <label for="date">Dato:</label>
            <input type="date" id="date" name="date"><br><br>
            <label for="weekno">Uge nr:</label>
            <input type="number" id="weekno" name="weekno"><br><br>
            <label for="week">"lige = l" / "ulige = u":</label>
            <input type="text" id="week" name="week"><br><br>
            <input type="hidden" name="command" value="createSwimdays"/>
            <input type="submit" value="Send">
        </form>
        <br><br>


        2 Opret brugerne:
        så opretter systemet automatisk alle swimdaytickets med 0 på hver plads.

        <br><br>

        3 Opret registreringer:
        Så giver systemet automatisk familierne det rigtige antal billetter.

    </jsp:body>
</t:pagetemplate>
