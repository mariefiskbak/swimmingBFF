<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Login
    </jsp:attribute>

    <jsp:attribute name="footer">
            Login
    </jsp:attribute>

    <jsp:body>

        <h3>Du kan logge ind her</h3>

        <form action=${pageContext.request.contextPath}/fc/login method="post">
            <input type="hidden" name="command" value="login"/>
            <label for="email">Email: </label>
            <br>
            <input type="email" id="email" name="email"/>
            <br> <br>
            <label for="password">Password: </label>
            <br>
            <input type="password" id="password" name="password"/>
            <input type="submit"  value="Log ind"/>
        </form>

    </jsp:body>
</t:pagetemplate>