package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.AdminMapper;
import dat.startcode.model.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateRegistration extends Command {
    private ConnectionPool connectionPool;

    public CreateRegistration() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        int familyID = Integer.parseInt(request.getParameter("familyid"));
        String teamID = request.getParameter("team");
        int amount = Integer.parseInt(request.getParameter("amount"));

        AdminMapper adminMapper = new AdminMapper(connectionPool);
        adminMapper.createRegistration(familyID, teamID, amount);

        return "admin";
    }
}
