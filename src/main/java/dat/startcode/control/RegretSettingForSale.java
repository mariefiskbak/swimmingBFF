package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDate;

public class RegretSettingForSale extends Command {
    private ConnectionPool connectionPool;

    public RegretSettingForSale()
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
        String sellAmountS = request.getParameter("regret");
        int regretAmount = Integer.parseInt(sellAmountS);
        String swimdateS = request.getParameter("regret_id");
        Timestamp swimdate = Timestamp.valueOf(swimdateS);
        User user = (User) session.getAttribute("user");
        int familyId = user.getFamilyId();
        swimMapper.regret(swimdate, familyId, regretAmount);

        Userpage userpage = new Userpage();
        userpage.execute(request, response);
        return "userpage";
    }
}
