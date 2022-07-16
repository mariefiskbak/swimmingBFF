package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.AdminMapper;
import dat.startcode.model.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CreateSwimdays extends Command {
    private ConnectionPool connectionPool;

    public CreateSwimdays() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        String date = request.getParameter("date");
        int weekNo = Integer.parseInt(request.getParameter("weekno"));
        String week = request.getParameter("week");

        AdminMapper adminMapper = new AdminMapper(connectionPool);
        adminMapper.createSwimdays(date, weekNo, week);

        return "admin";
    }
}
