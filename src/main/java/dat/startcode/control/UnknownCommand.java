package dat.startcode.control;

import dat.startcode.model.exceptions.DatabaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends Command {

    @Override
    String execute( HttpServletRequest request, HttpServletResponse response ) throws DatabaseException
    {
        String msg = "Du opdaterede garanteret siden, det er mit program ikke bygget til.. endnu i hvert fald";
        throw new DatabaseException( msg );
    }

}
