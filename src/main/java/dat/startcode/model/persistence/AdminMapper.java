package dat.startcode.model.persistence;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminMapper {
    ConnectionPool connectionPool;

    public AdminMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void createSwimdays(String date, int weekNo, String week) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        String dateTime1 = date + " 09:00:00";
        String dateTime2 = date + " 10:00:00";

        String weekNoS = "" + weekNo;

        String teamId1 = "";
        String teamId2 = "";
        if(week.equals("l")){
            teamId1 = "lige uge fre 9-10";
            teamId2 = "lige uge fre 10-11";
        }
        if(week.equals("u")){
            teamId1 = "ulige uge fre 9-10";
            teamId2 = "ulige uge fre 10-11";
        } //TODO skal have den til at brokke sig, hvis man ikke fik skrevet l eller u

        String sql = "INSERT INTO swimming.swimday (swimming.swimday.swimdate, swimming.swimday.week_no, swimming.swimday.team_id) VALUES(?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, dateTime1);
                ps.setString(2, weekNoS);
                ps.setString(3, teamId1);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                } else {
                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                }
            }
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex, "Kunne ikke opdatere svømmebilletter");
        }

        String sql2 = "INSERT INTO swimming.swimday (swimming.swimday.swimdate, swimming.swimday.week_no, swimming.swimday.team_id) VALUES(?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql2)) {
                ps.setString(1, dateTime2);
                ps.setString(2, weekNoS);
                ps.setString(3, teamId2);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                } else {
                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                }
            }
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex, "Kunne ikke opdatere svømmebilletter");
        }
    }
}
