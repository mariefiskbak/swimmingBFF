<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Til salg
    </jsp:attribute>

    <jsp:attribute name="footer">
            Til salg
    </jsp:attribute>

    <jsp:body>

        <h3>Billetter til salg</h3>

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
                        <form action="fc/buy">
                            <td>${forsale.swimday}</td>
                            <td>${forsale.weekNo}</td>
                            <td>${forsale.team}</td>
                            <td>
<%--                                Den sender info fra øverste element i tabellen og ikke det jeg klikker på HVORFOR???!--%>
                                <div class="container">
                                    <div class="row">
                                        <div class="col">
<%--                                            <input type="hidden" name="command" value="buy"/>--%>
                                            <input type="number" name="buy" id="buy${forsale.weekNo}"
                                                   class="form-control" value="${forsale.amountForSaleFromOneFamily}"
                                                   style="width: 5rem" min="0" max="${forsale.amountForSaleFromOneFamily}"/>
                                        </div>
                                        <div class="col">
                                            <!-- Button trigger modal -->
                                            <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#staticBackdrop" value="${forsale.swimday},${forsale.familyId}">
                                                Køb
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <!-- Modal -->
                                <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="staticBackdropLabel">Køb ${forsale.amountForSaleFromOneFamily} billetter til den ${forsale.swimday}</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Overfør <c:out value="${15 * forsale.amountForSaleFromOneFamily}" /> kr til ${forsale.familyName} via Mobile Pay på nr: ${forsale.familyPhoneNo}
                                            </div>
                                            <div class="modal-footer">
                                                <input type="hidden" name="command" value="buy"/>
                                                <%--   <button type="button" class="btn btn-primary">Jeg har overført nu</button>--%>
                                                <button type="submit" class="btn btn-primary" name="buy_id"
                                                        value="${forsale.swimday},${forsale.familyId}">Jeg har overført nu
                                                </button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fortryd køb</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>



<%--                                <div class="container">--%>
<%--                                    <div class="row">--%>
<%--                                        <div class="col">--%>
<%--                                            <input type="hidden" name="command" value="buy"/>--%>
<%--                                            <input type="number" name="buy" id="buy${forsale.weekNo}"--%>
<%--                                                   class="form-control" value="${forsale.amountForSaleFromOneFamily}"--%>
<%--                                                   style="width: 5rem" min="0" max="${forsale.amountForSaleFromOneFamily}"/>--%>
<%--                                        </div>--%>
<%--                                        <div class="col">--%>
<%--                                            <button type="submit" class="btn btn-outline-secondary" name="buy_id"--%>
<%--                                                    value="${forsale.swimday},${forsale.familyId}">Køb--%>
<%--                                            </button>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                                --%>

                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </jsp:body>
</t:pagetemplate>