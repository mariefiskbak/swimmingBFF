package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sell extends Command {
    private ConnectionPool connectionPool;

    public Sell()
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
        String sellAmountS = request.getParameter("sell");
        int sellAmount = Integer.parseInt(sellAmountS);
        String swimdateS = request.getParameter("sell_id");
        Timestamp swimdate = Timestamp.valueOf(swimdateS);
        User user = (User) session.getAttribute("user");
        int familyId = user.getFamilyId();
        Timestamp timestamp = (Timestamp) Timestamp.valueOf(LocalDateTime.now());
        swimMapper.sell(swimdate, familyId, sellAmount, timestamp);

        Userpage userpage = new Userpage();
        userpage.execute(request, response);
        return "userpage";
    }
}
