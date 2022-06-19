<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Betal
    </jsp:attribute>

  <jsp:attribute name="footer">
            Betal
    </jsp:attribute>

  <jsp:body>

      <br>
      Overfør ${sessionScope.reservedAmount * 16} kr til ${sessionScope.familyName} via Mobile Pay på tlf nr: ${sessionScope.familyPhoneNo}
      <br> <br>
      Skriv "${sessionScope.reservedAmount} svømmebilletter til den ${sessionScope.splitSwimday}" i kommentarfeltet.
      <br> <br>
      <form action="fc/buy" method="post">
          <input type="hidden" name="command" value="buy"/>
          <button type="submit" class="btn btn-primary" name="buy_id"
                  value="${sessionScope.swimday},${sessionScope.buyFromFamilyId},${sessionScope.reservedAmount}">Jeg har overført nu
          </button>
      </form>
      <br>
<%--      //TODO denne knap skal også virke--%>
      <form action="fc/regretBuying" method="post">
          <input type="hidden" name="command" value="regretBuying"/>
          <button type="submit" class="btn btn-secondary" name="regret_id" value="${sessionScope.swimday},${sessionScope.buyFromFamilyId},${sessionScope.reservedAmount}">
              Fortryd køb
          </button>
      </form>
    <%--  //TODO hvis folk forlader siden uden at trykke fortryd, hvordan kommer billetterne så tilbage til salg?
      //TODO kan folk lave rav i den med URL-hacking?--%>
  </jsp:body>
</t:pagetemplate>