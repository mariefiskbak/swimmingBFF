package dat.startcode.control;

import dat.startcode.model.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

abstract class Command
{

    private static HashMap<String, Command> commands;

    private static void initCommands() {
        commands = new HashMap<>();
        commands.put("login", new Login());
        commands.put("logout", new Logout());
        commands.put("about", new About());
        commands.put("userpage", new Userpage());
        commands.put("sell", new Sell());
        commands.put("regretSettingForSale", new RegretSettingForSale());
        commands.put("forsale", new Forsale());
        commands.put("index", new Index());
        commands.put("buy", new Buy());
        commands.put("deletemessage", new DeleteMessage());
        commands.put("buyMessage", new BuyMessage());
        commands.put("regretBuying", new RegretBuying());
        commands.put("pay", new Pay());
        commands.put("reserve", new Reserve());
        commands.put("wishfortickets", new WishForTickets());
        commands.put("wish", new Wish());
        commands.put("admin", new Admin());
        commands.put("createSwimdays", new CreateSwimdays());
    }

    static Command from( HttpServletRequest request ) {
        String commandName = request.getParameter( "command" );
        if ( commands == null ) {
            initCommands();
        }
        return commands.getOrDefault(commandName, new UnknownCommand() );   // unknowncommand er default.
    }

    abstract String execute( HttpServletRequest request, HttpServletResponse response )
            throws DatabaseException, ServletException, IOException;



}
