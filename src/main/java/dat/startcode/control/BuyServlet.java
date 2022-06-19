package dat.startcode.control;

import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;

@WebServlet(name = "BuyServlet", value = "/BuyServlet")
public class BuyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConnectionPool connectionPool = new ConnectionPool();
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
        String buyAmountS = request.getParameter("buy");
        int buyAmount = Integer.parseInt(buyAmountS);
        String swimdateCommaFamilyIdS = request.getParameter("buy_id");
        String[] swimdateFamilyIdA = swimdateCommaFamilyIdS.split(",");
        String swimdateS = swimdateFamilyIdA[0];
        Timestamp swimdate = Timestamp.valueOf(swimdateS);
        String familyIdS = swimdateFamilyIdA[1];
        int buyFromFamilyId = Integer.parseInt(familyIdS);
        User user = (User) session.getAttribute("user");
        int buyerFamilyId = user.getFamilyId();

        try {
            swimMapper.buy(swimdate, buyFromFamilyId, buyAmount, buyerFamilyId);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
