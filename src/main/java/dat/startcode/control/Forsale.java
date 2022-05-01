package dat.startcode.control;

import dat.startcode.model.dtos.ForSaleDTO;
import dat.startcode.model.dtos.SwimTableDTO;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class Forsale extends Command {
    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
        HttpSession session = request.getSession();
        ConnectionPool connectionPool = new ConnectionPool();

        User user = (User) session.getAttribute("user");
        String name = user.getName();
        session.setAttribute("name", name);

        SwimMapper swimMapper = new SwimMapper(connectionPool);
        int familyId = user.getFamilyId();

        List<ForSaleDTO> forSaleDTOList = swimMapper.getForSaleDTOList();
        session.setAttribute("forSaleDTOList", forSaleDTOList);
        //TODO, vil kun liste billetter til slag, hvor datoen endnu ikke er nået

        return "forsale";
    }
}
