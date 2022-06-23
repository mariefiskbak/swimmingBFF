<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
         Ønsk svømmebilletter
    </jsp:attribute>

    <jsp:attribute name="footer">
        Ønsk svømmebilletter
    </jsp:attribute>

    <jsp:body>


        <c:if test="${sessionScope.user == null}">
            <p>Log ind her: <a href="${pageContext.request.contextPath}/login.jsp">Login</a></p>
        </c:if>

        <c:if test="${sessionScope.user != null}">
            <br>
            <h2>Stil dig i kø</h2>
            <br>
            <p>Bemærk at man IKKE er sikret at være den der køber billetterne, blot fordi man har stillet sig forrest i
                køen.</p>
            <br>
            <p>Til gengæld vil en der sætter sine billetter til salg, få en besked om at du har ønsket billetter og
                blive bedt om at sende dig en sms om at der nu er billetter til salg den pågældende dag. </p>
            <p>Disse SKAL købes via denne side og ikke direkte med sælger på sms, da der ellers går kludder i systemet
                og for mange kan få billetter. Vi må ikke være mere end 30 personer i vandet. </p>
            <p>Hvis en anden familie tilfældigvis køber billetterne inden dig, så er det dem der har dem. Vi bliver nødt
                til at respektere, der hvor systemet lægger billetterne, så der ikke går kludder i hvor mange der har en
                billet.</p>
            <br>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Dato</th>
                        <th scope="col">Uge nr</th>
                        <th scope="col">Kø</th>
                        <th scope="col">Ønsk billetter</th>
                        <th scope="col">Måske markering af om der allerede er billetter til salg</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="swimday" items="${sessionScope.swimdayList}">
                        <tr>
                            <td>${swimday.swimday}</td>
                            <td>${swimday.weekNo}</td>
                            <td>Antal i køen</td>
                            <td>
                                <div class="container">
                                    <div class="row">
                                        <div class="col">
                                            <input type="number" name="wish"
                                                <%--                                               id="wish${.weekNo}_${.familyId}"--%>
                                                   class="form-control" value="0"
                                                   style="width: 5rem" min="0"
                                            />
                                        </div>
                                        <div class="col">
                                            <!-- Button buy -->
                                            <input type="hidden" name="command" value="wish"/>
                                            <button type="submit" name="wishbutton" class="btn btn-outline-secondary"
                                                    formaction="fc/wish" formmethod="post"
                                                    value="${swimday.swimday},${sessionScope.user.familyId}"
                                                <%--                                                id="submit${forsale.weekNo}_${forsale.familyId}"--%>
                                            >
                                                Ønsk
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td>knap, der leder hen til salgssiden?</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </jsp:body>

</t:pagetemplate>