package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.dtos.ForSaleDTO;
import dat.startcode.model.dtos.SwimTableDTO;
import dat.startcode.model.entities.Message;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

public class Userpage extends Command {
    private ConnectionPool connectionPool;

    public Userpage()
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
        String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
            HttpSession session = request.getSession();

            User user = (User) session.getAttribute("user");
            String name = user.getName();
            session.setAttribute("name", name);

            SwimMapper swimMapper = new SwimMapper(connectionPool);
            int familyId = user.getFamilyId();

            MessageMapper messageMapper = new MessageMapper(connectionPool);
            List<Message> messageList = messageMapper.getMessageList(familyId);
            session.setAttribute("messageList", messageList);

            List<SwimTableDTO> swimTableDTOList = swimMapper.getSwimTableDTOList(familyId);
            session.setAttribute("swimTableDTOList", swimTableDTOList);

            SalesMapper salesMapper = new SalesMapper(connectionPool);
            List<ForSaleDTO> forSaleDTOList = salesMapper.getForSaleDTOList();
            session.setAttribute("forSaleDTOList", forSaleDTOList);

            return "userpage";
        }
    }

