package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.dtos.Swimday;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Index extends Command {

    private ConnectionPool connectionPool;

    public Index() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }
    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
        List<Swimday> swimdayList = swimMapper.getSwimdays();
        session.setAttribute("swimdayList", swimdayList);


        return "index";
    }
}
