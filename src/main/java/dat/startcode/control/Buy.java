package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.TicketsHaveBeenMoved;
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

public class Buy extends Command {
    private static final int ticketPrice = 16;
    private ConnectionPool connectionPool;

    public Buy() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
//        String buyAmountS = request.getParameter("buy");
//        int buyAmount = Integer.parseInt(buyAmountS);
        String swimdateCommaFamilyIdS = request.getParameter("buy_id");
        String[] swimdateFamilyIdA = swimdateCommaFamilyIdS.split(",");
        String swimdateS = swimdateFamilyIdA[0];
        Timestamp swimdate = Timestamp.valueOf(swimdateS);
        String familyIdS = swimdateFamilyIdA[1];
        int buyFromFamilyId = Integer.parseInt(familyIdS);
        int buyAmount = Integer.parseInt(swimdateFamilyIdA[2]);
        User user = (User) session.getAttribute("user");
        int buyerFamilyId = user.getFamilyId();

        swimMapper.buy(swimdate, buyFromFamilyId, buyAmount, buyerFamilyId);
        TicketsHaveBeenMoved t = new TicketsHaveBeenMoved(true);
        //t.setTicketsHaveBeenMoved(true);

        MessageMapper messageMapper = new MessageMapper(connectionPool);
        int price = buyAmount * ticketPrice;
        String buyerFamilyName = swimMapper.getFamilyName(buyerFamilyId);
        int toTime = Integer.parseInt(swimdateS.substring(11, 13)) + 1;
        String splitSwimdate = swimdateS.substring(8, 10) + "/" + swimdateS.substring(5,7) + " : " + swimdateS.substring(11, 13) + "-" + toTime;
        String message = buyerFamilyName + " har købt " + buyAmount + " billetter fra dig til den " + splitSwimdate + ". Du skulle gerne have modtaget " + price + " kr på Mobile Pay.";
        messageMapper.putMessageInDB(buyFromFamilyId, message);


        Userpage  userpage = new Userpage();
        userpage.execute(request, response);
        return "userpage";
    }
}
