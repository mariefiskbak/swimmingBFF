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
import java.sql.Timestamp;

public class RegretBuying extends Command {
    private ConnectionPool connectionPool;

    public RegretBuying() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
//        String buyAmountS = request.getParameter("buy");
//        int buyAmount = Integer.parseInt(buyAmountS);
        String swimdateCommaFamilyIdS = request.getParameter("regret_id");
        String[] swimdateFamilyIdA = swimdateCommaFamilyIdS.split(",");
        String swimdateS = swimdateFamilyIdA[0];
        Timestamp swimdate = Timestamp.valueOf(swimdateS);
        String familyIdS = swimdateFamilyIdA[1];
        int buyFromFamilyId = Integer.parseInt(familyIdS);
        int buyAmount = Integer.parseInt(swimdateFamilyIdA[2]);
        User user = (User) session.getAttribute("user");
        int buyerFamilyId = user.getFamilyId();

        swimMapper.regretBuying(swimdate, buyFromFamilyId, buyAmount, buyerFamilyId);
        TicketsHaveBeenMoved t = new TicketsHaveBeenMoved(true);
        //t.setTicketsHaveBeenMoved(true);

        Forsale forsale = new Forsale();
        forsale.execute(request, response);
        return "forsale";
    }
}