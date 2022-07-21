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
             Til salg
    </jsp:attribute>

    <jsp:attribute name="footer">
            Til salg
    </jsp:attribute>

    <jsp:body>

        <br>
        <h3>Billetter til salg</h3>
        <br>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col">Dato</th>
                    <th scope="col">Uge nr</th>
                    <th scope="col">Hold Id</th>
                    <th scope="col">Til salg</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="forsale" items="${sessionScope.forSaleDTOList}">
                    <tr>
                        <form action=${pageContext.request.contextPath}/fc/buy>
                            <td>${forsale.splitSwimday}</td>
                            <td>${forsale.weekNo}</td>
                            <td>${forsale.team}</td>
                            <td>
                                <div class="container">
                                    <div class="row">
                                        <div class="col">
                                            <input type="number" name="buy"
                                                   id="buy${forsale.weekNo}_${forsale.familyId}"
                                                   class="form-control" value="${forsale.amountForSaleFromOneFamily}"
                                                   style="width: 5rem" min="0"
                                                   max="${forsale.amountForSaleFromOneFamily}"/>
                                        </div>
                                        <div class="col">
                                            <!-- Button trigger modal -->
                                            <button type="button" name="buybutton" class="btn btn-outline-secondary"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#staticBackdrop${forsale.weekNo}_${forsale.familyId}"
                                                    value="${forsale.swimday},${forsale.familyId}"
                                                    id="submit${forsale.weekNo}_${forsale.familyId}">
                                                Køb
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <!-- Modal -->
                                <div class="modal fade" id="staticBackdrop${forsale.weekNo}_${forsale.familyId}"
                                     data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                                     aria-labelledby="staticBackdropLabel${forsale.weekNo}_${forsale.familyId}"
                                     aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title"
                                                    id="staticBackdropLabel${forsale.weekNo}_${forsale.familyId}">
                                                </h5>
                                                    <%--                                                <button type="button" class="btn-close" data-bs-dismiss="modal"--%>
                                                    <%--                                                        aria-label="Close"></button>--%>
                                            </div>
                                            <div class="modal-body">
                                                <p id="modal_body${forsale.weekNo}_${forsale.familyId}"></p>
                                                <p id="modal_body2${forsale.weekNo}_${forsale.familyId}"></p>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="hidden" name="command" value="buy"/>
                                                <button type="submit" class="btn btn-primary" name="buy_id"
                                                        value="${forsale.swimday},${forsale.familyId}">Jeg har overført
                                                    nu
                                                </button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    Fortryd køb
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </form>
                    </tr>

                    <%--                                                Burde lave 15 til en konstant(ticketPrice), kan jeg undgå at gøre det mere en ét sted i koden?--%>
                    <script type="text/javascript">
                        $("#submit${forsale.weekNo}_${forsale.familyId}").click(function () {
                            var amount = $("#buy${forsale.weekNo}_${forsale.familyId}").val();
                            var str = "Overfør "
                                + 16 * amount +
                                " kr til ${forsale.familyName} via Mobile Pay på nr: ${forsale.familyPhoneNo}"
                            $("#modal_body${forsale.weekNo}_${forsale.familyId}").html(str);

                            var str2 = "Skriv: '" + amount
                                + " svømmebilletter til den ${forsale.splitSwimday}' i kommentarfeltet";
                            $("#modal_body2${forsale.weekNo}_${forsale.familyId}").html(str2);

                            var head = "Køb " + amount + " billetter til den ${forsale.splitSwimday}"
                            $("#staticBackdropLabel${forsale.weekNo}_${forsale.familyId}").html(head);


                        });

                    </script>


<%--                    VIRKER IKKE HER, Den fanger måske ikke parameteren, og den tjekker nok heller ikke på runtime, virker når den er under nedenstående besked--%>
                    <c:if test="${param.buybutton != null}">
                        <sql:setDataSource var="reserve" driver="com.mysql.jdbc.Driver"
                                           url="jdbc:mysql://localhost:3306/swimming"
                                           user="root" password="root"/>
                        <sql:update dataSource="${reserve}" var="count">
                            UPDATE swimming.swimdaytickets SET tickets_for_sale = tickets_for_sale - 1, reserved_tickets = reserved_tickets + 1 WHERE family_id = 1 AND swimdate = 20220603;
                        </sql:update>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <%--         Virker, men slet ikke som jeg vil have det--%>

        <%--        Til sikring mod to der køber samtidigt:--%>
        <%--        egentlig skal databasen ændres tilbage ved KØB, og ændres tilbage ved FORTRYD, og så først ved JEG HAR OVERFØRT skal beskeden laves--%>
    </jsp:body>
</t:pagetemplate>
