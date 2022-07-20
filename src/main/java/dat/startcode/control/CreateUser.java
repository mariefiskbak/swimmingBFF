package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.AdminMapper;
import dat.startcode.model.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUser extends Command {
    private ConnectionPool connectionPool;

    public CreateUser() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        int familyID = Integer.parseInt(request.getParameter("family"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String primaryUser = request.getParameter("primary");

        AdminMapper adminMapper = new AdminMapper(connectionPool);
        adminMapper.createUser(familyID, name, email, phone, password, primaryUser);

        return "admin";
    }
}
