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

      Overfør X kr til Navn via Mobile Pay på tlf nr: XXXXXXXX

      Skriv "X svømmebilletter til den DATO" i kommentarfeltet.

      <form action="fc/buyMessage">
          <input type="hidden" name="command" value="buyMessage"/>
          <button type="submit" class="btn btn-primary" name="buy_id"
                  value="2022-06-03,1">Jeg har overført nu
          </button>
      </form>

      <form action="fc/regretBuying">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" value="regretBuying">
              Fortryd køb
          </button>
      </form>

      <%--<div class="modal fade" id="staticBackdrop${forsale.weekNo}_${forsale.familyId}"
      data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
      aria-labelledby="staticBackdropLabel${forsale.weekNo}_${forsale.familyId}"
      aria-hidden="true">
      <div class="modal-dialog">
          <div class="modal-content">
              <div class="modal-header">
                  <h5 class="modal-title"
                      id="staticBackdropLabel${forsale.weekNo}_${forsale.familyId}">
                  </h5>
              </div>
              <div class="modal-body">
                  <p id="modal_body${forsale.weekNo}_${forsale.familyId}"></p>
                  <p id="modal_body2${forsale.weekNo}_${forsale.familyId}"></p>
              </div>
              <div class="modal-footer">
                  <form action="fc/buyMessage">
                      <input type="hidden" name="command" value="buyMessage"/>
                      <button type="submit" class="btn btn-primary" name="buy_id"
                              value="${forsale.swimday},${forsale.familyId}">Jeg har overført
                          nu
                      </button>
                  </form>
                  <form action="fc/regretBuying">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" value="regretBuying">
                          Fortryd køb
                      </button>
                  </form>
              </div>
          </div>
      </div>
      </div>--%>
      <div class="modal fade" id="staticBackdrop1_1"
           data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
           aria-labelledby="staticBackdropLabel1_1"
           aria-hidden="true">
          <div class="modal-dialog">
              <div class="modal-content">
                  <div class="modal-header">
                      <h5 class="modal-title"
                          id="staticBackdropLabel1_1">
                          Her er titlen
                      </h5>
                  </div>
                  <div class="modal-body">
                      Her er kroppen
                      <p id="modal_body1_1"></p>
                      <p id="modal_body21_1"></p>
                  </div>
                  <div class="modal-footer">
                      <form action="fc/buyMessage">
                          <input type="hidden" name="command" value="buyMessage"/>
                          <button type="submit" class="btn btn-primary" name="buy_id"
                                  value="2022-06-03,1">Jeg har overført
                              nu
                          </button>
                      </form>
                      <form action="fc/regretBuying">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" value="regretBuying">
                              Fortryd køb
                          </button>
                      </form>
                  </div>
              </div>
          </div>
      </div>
      <script>
          $(document).ready(function(){
              $("#staticBackdrop1_1").showModal();
          });
      </script>
  </jsp:body>
</t:pagetemplate>