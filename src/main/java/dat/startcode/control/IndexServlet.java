package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.dtos.Swimday;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/IndexServlet")
public class IndexServlet extends HttpServlet {

    private ConnectionPool connectionPool;

    public IndexServlet() {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        SwimMapper swimMapper = new SwimMapper(connectionPool);
        List<Swimday> swimdayList = null;
        try {
            swimdayList = swimMapper.getSwimdays();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        session.setAttribute("swimdayList", swimdayList);

        //request.getRequestDispatcher("index").forward(request, response);
        response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
