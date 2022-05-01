<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Medlemsside
    </jsp:attribute>

    <jsp:attribute name="footer">
            Medlemsside
    </jsp:attribute>

    <jsp:body>

        <br>
        <h3>${sessionScope.name} *** plus mere info i siden ***</h3>

        <br>
        <h3>Beskeder</h3>
        <br>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Besked</th>
                    <th scope="col">Fjern besked</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="messages" items="${sessionScope.messageList}">
                    <tr>
                        <form action="fc/deletemessage">
                            <td>${messages.message}</td>
                            <td>
                                <div class="container">
                                    <div class="row">
                                        <div class="col">
                                            <input type="hidden" name="command" value="deletemessage"/>
                                            <button type="submit" class="btn btn-outline-secondary" name="deletemessage" id="deletemessage${messages.messageId}"
                                                   value="${messages.messageId}"
                                            >Læst</button>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


        <br>

        <form action="fc/forsale" method="post">
            <input type="hidden" name="command" value="forsale"/>
            <input type="submit" value="Se billetter til salg"/>
        </form>

        <br>

        <h2>Dine svømmebilletter</h2>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Dato</th>
                    <th scope="col">Uge nr</th>
                    <th scope="col">Dine billetter</th>
                    <th scope="col">Sæt til salg</th>
                    <th scope="col">Sat til salg</th>
                    <th scope="col">Til salg</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="swimtable" items="${sessionScope.swimTableDTOList}">
                    <tr>
                        <form action="fc/sell">
                            <td>${swimtable.swimday}</td>
                            <td>${swimtable.weekNo}</td>
                            <td>${swimtable.currentTicketAmount} stk</td>
                            <td>
                                <div class="container">
                                    <div class="row">
                                        <div class="col">
                                            <input type="hidden" name="command" value="sell"/>
                                            <input type="number" name="sell" id="sell${swimtable.weekNo}"
                                                   class="form-control" value="${swimtable.currentTicketAmount}"
                                                   style="width: 5rem" min="0" max="${swimtable.currentTicketAmount}"/>
                                        </div>
                                        <div class="col">
                                            <button type="submit" class="btn btn-outline-secondary" name="sell_id"
                                                    value="${swimtable.swimday}">Sælg
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </form>
                        <form action="fc/regret">
                            <td>
                                <div class="container">
                                    <div class="row">
                                        <div class="col">
                                            <input type="hidden" name="command" value="regret"/>
                                            <input type="number" name="regret" id="regret${swimtable.weekNo}"
                                                   class="form-control" value="${swimtable.ticketsForSale}"
                                                   style="width: 5rem" min="0" max="${swimtable.ticketsForSale}"/>
                                        </div>
                                        <div class="col">
                                            <button type="submit" class="btn btn-outline-secondary" name="regret_id"
                                                    value="${swimtable.swimday}">Fortryd
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </form>
                        <td></td>


                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <br>
        <p><a href="../index.jsp">Til forsiden</a></p>


    </jsp:body>
</t:pagetemplate>