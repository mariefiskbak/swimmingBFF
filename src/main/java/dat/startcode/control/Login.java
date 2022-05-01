package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.services.UserFacade;
import dat.startcode.model.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends Command
{
    private ConnectionPool connectionPool;

    public Login()
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("user", null); // adding empty user object to session scope
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = UserFacade.login(email, password, connectionPool);
        session = request.getSession();
        session.setAttribute("user", user); // adding user object to session scope

        Userpage userpage = new Userpage();
        userpage.execute(request, response);

//        FrontController fc = new FrontController();
//        fc.processRequest(request, response);
        return "userpage";
    }
}