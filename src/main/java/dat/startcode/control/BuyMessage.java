package dat.startcode.control;

import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.MessageMapper;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BuyMessage extends Command {
    private static final int ticketPrice = 15;

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        ConnectionPool connectionPool = new ConnectionPool();
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
        String buyAmountS = request.getParameter("buy");
        int buyAmount = Integer.parseInt(buyAmountS);
        String swimdateCommaFamilyIdS = request.getParameter("buy_id");
        String[] swimdateFamilyIdA = swimdateCommaFamilyIdS.split(",");
        String swimdateS = swimdateFamilyIdA[0];
        LocalDate swimdate = LocalDate.parse(swimdateS);
        String familyIdS = swimdateFamilyIdA[1];
        int buyFromFamilyId = Integer.parseInt(familyIdS);
        User user = (User) session.getAttribute("user");
        int buyerFamilyId = user.getFamilyId();


        MessageMapper messageMapper = new MessageMapper(connectionPool);
        int price = buyAmount * ticketPrice;
        String buyerFamilyName = swimMapper.getFamilyName(buyerFamilyId);
        String message = buyerFamilyName + " har købt " + buyAmount + " billetter fra dig til den " + swimdateS + ". Du skulle gerne have modtaget " + price + " kr på Mobile Pay.";
        messageMapper.putMessageInDB(buyFromFamilyId, message);


        Forsale forsale = new Forsale();
        forsale.execute(request, response);
        return "forsale";
    }
}
