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
      Overfør ${sessionScope.reservedAmount * 16} kr til ${sessionScope.familyName} via Mobile Pay på tlf nr: <input type="text" value="${sessionScope.familyPhoneNo}" id="myInput" style="border: white; width: 6rem">
      <button onclick="myFunction()">Kopier tlf-nummer</button>
      <br> <br>
      Skriv i kommentarfeltet: <input type="text" value="${sessionScope.reservedAmount} svømmebilletter til den ${sessionScope.splitSwimday}" id="myInput2" style="border: white; width: 20rem;"> <button onclick="myFunction2()">Kopier tekst</button>

      <br> <br>
      <form action=${pageContext.request.contextPath}/fc/buy method="post">
          <input type="hidden" name="command" value="buy"/>
          <button type="submit" class="btn btn-primary" name="buy_id"
                  value="${sessionScope.swimday},${sessionScope.buyFromFamilyId},${sessionScope.reservedAmount}">Jeg har overført nu
          </button>
      </form>
      <br>
      <form action=${pageContext.request.contextPath}/fc/regretBuying method="post">
          <input type="hidden" name="command" value="regretBuying"/>
          <button type="submit" class="btn btn-secondary" name="regret_id" value="${sessionScope.swimday},${sessionScope.buyFromFamilyId},${sessionScope.reservedAmount}">
              Fortryd køb
          </button>
      </form>
    <%-- //TODO kan folk lave rav i den med URL-hacking?--%>
      <br>
      <div>Billetterne er reserverede i <span id="timer"></span> minutter</div>

<%--      Dirigerer en videre til salgssiden efter 4 minutter--%>
      <meta http-equiv = "refresh" content = "240; url = ${pageContext.request.contextPath}/fc/forsale?command=forsale">

      <script>
          document.getElementById('timer').innerHTML = 4 + ":" + 00;
          startTimer();

          function startTimer() {
              var presentTime = document.getElementById('timer').innerHTML;
              var timeArray = presentTime.split(/[:]+/);
              var m = timeArray[0];
              var s = checkSecond((timeArray[1] - 1));
              if(s==59){m=m-1}
              if(m<0){
                  return
              }

              document.getElementById('timer').innerHTML =
                  m + ":" + s;
              console.log(m)
              setTimeout(startTimer, 1000);
          }

          function checkSecond(sec) {
              if (sec < 10 && sec >= 0) {sec = "0" + sec}; // add zero in front of numbers < 10
              if (sec < 0) {sec = "59"};
              return sec;
          }


          function myFunction() {
              /* Get the text field */
              var copyText = document.getElementById("myInput");

              /* Select the text field */
              copyText.select();
              copyText.setSelectionRange(0, 99999); /* For mobile devices */

              /* Copy the text inside the text field */
              navigator.clipboard.writeText(copyText.value);

              /* Alert the copied text */
              alert("Copied the text: " + copyText.value);
          }

          function myFunction2() {
              /* Get the text field */
              var copyText = document.getElementById("myInput2");

              /* Select the text field */
              copyText.select();
              copyText.setSelectionRange(0, 99999); /* For mobile devices */

              /* Copy the text inside the text field */
              navigator.clipboard.writeText(copyText.value);

              /* Alert the copied text */
              alert("Copied the text: " + copyText.value);
          }
      </script>

  </jsp:body>
</t:pagetemplate>