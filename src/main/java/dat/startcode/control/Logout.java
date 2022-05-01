package dat.startcode.control;

import dat.startcode.model.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logout extends Command {
    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DatabaseException {
        HttpSession session = request.getSession();
        session.invalidate();

        Index index = new Index();
        index.execute(request, response);
        return "index";
    }
}