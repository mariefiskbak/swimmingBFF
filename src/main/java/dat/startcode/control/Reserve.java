package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reserve extends Command {
    private ConnectionPool connectionPool;

    public Reserve() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        HttpSession session = request.getSession();

        SwimMapper swimMapper = null;
        int reserveAmount = 0;
        Timestamp swimdate = null;
        int buyFromFamilyId = 0;
        int buyerFamilyId = 0;
        String familyName = "";
        int familyPhoneNo = 0;
        String splitSwimday = "";
        try {
            swimMapper = new SwimMapper(connectionPool);
            String reserveAmountS = request.getParameter("buy");
            reserveAmount = Integer.parseInt(reserveAmountS);
            String swimdateCommaFamilyIdS = request.getParameter("reservebutton");
            String[] swimdateFamilyIdA = swimdateCommaFamilyIdS.split(",");
            String swimdateS = swimdateFamilyIdA[0];
            swimdate = Timestamp.valueOf(swimdateS);
            String familyIdS = swimdateFamilyIdA[1];
            buyFromFamilyId = Integer.parseInt(familyIdS);
            User user = (User) session.getAttribute("user");
            buyerFamilyId = user.getFamilyId();
            familyName = swimdateFamilyIdA[2];
            familyPhoneNo = Integer.parseInt(swimdateFamilyIdA[3]);
            splitSwimday = swimdateFamilyIdA[4];
        } catch (Exception e) {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            //TODO vil gerne hen til fejlsiden, så der kan prøves igen. Virker ikke. vil også gerne skrive en besked om at nogle andre allerede har købt billetterne
            //request.getRequestDispatcher("error.jsp").forward(request, response);
            response.sendRedirect(request.getServletContext().getContextPath() + "/error.jsp");
        }

        session.setAttribute("reservedAmount", reserveAmount);
        session.setAttribute("buyFromFamilyId", buyFromFamilyId);
        session.setAttribute("familyName", familyName);
        session.setAttribute("familyPhoneNo", familyPhoneNo);
        session.setAttribute("splitSwimday", splitSwimday);
        session.setAttribute("swimday", swimdate);

        swimMapper.reserve(swimdate, buyFromFamilyId, reserveAmount, buyerFamilyId);

        //Starts a new thread that after 4 minutes, moves the tickets back from reserved_tickets to _tickets_for_sale.
        //It works!!
        Thread thread = new Thread(new Runnable(swimdate, buyFromFamilyId, reserveAmount, buyerFamilyId, connectionPool, session));
        thread.start();

        return "pay";
    }
}
