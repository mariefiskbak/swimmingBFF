package dat.startcode.control;

import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.MessageMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteMessage extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException, ServletException, IOException {
        ConnectionPool connectionPool = new ConnectionPool();
        HttpSession session = request.getSession();

        MessageMapper messageMapper = new MessageMapper(connectionPool);
        int messageId = Integer.parseInt(request.getParameter("deletemessage"));
        messageMapper.deleteMessage(messageId);

        Userpage userpage = new Userpage();
        userpage.execute(request, response);
        return "userpage";
    }
}
