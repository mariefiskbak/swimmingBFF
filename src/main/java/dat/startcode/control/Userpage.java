package dat.startcode.control;

import dat.startcode.model.dtos.ForSaleDTO;
import dat.startcode.model.dtos.SwimTableDTO;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SalesMapper;
import dat.startcode.model.persistence.SwimMapper;
import dat.startcode.model.persistence.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

public class Userpage extends Command {
        @Override
        String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
            HttpSession session = request.getSession();
            ConnectionPool connectionPool = new ConnectionPool();

            User user = (User) session.getAttribute("user");
            String name = user.getName();
            session.setAttribute("name", name);

            SwimMapper swimMapper = new SwimMapper(connectionPool);
            int familyId = user.getFamilyId();
            List<SwimTableDTO> swimTableDTOList = swimMapper.getSwimTableDTOList(familyId);
            session.setAttribute("swimTableDTOList", swimTableDTOList);

            ConnectionPool connectionPool1 = new ConnectionPool();
            SalesMapper salesMapper = new SalesMapper(connectionPool1);
            List<ForSaleDTO> forSaleDTOList = salesMapper.getForSaleDTOList();
            session.setAttribute("forSaleDTOList", forSaleDTOList);
            //TODO, vil kun liste billetter til slag, hvor datoen endnu ikke er nået

            return "userpage";
        }
    }

