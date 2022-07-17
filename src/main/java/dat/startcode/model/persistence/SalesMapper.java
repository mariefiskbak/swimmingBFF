package dat.startcode.model.persistence;

import dat.startcode.model.dtos.ForSaleDTO;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesMapper {
    ConnectionPool connectionPool;

    public SalesMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<ForSaleDTO> getForSaleDTOList() throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<ForSaleDTO> forSaleDTOList = new ArrayList<>();

        //TODO jeg tror joinet med family-tabllen er ligegyldigt
        //String sql = "SELECT swimming.swimdaytickets.swimdate, swimming.swimdaytickets.family_id, swimming.swimday.week_no, swimming.swimday.team_id, swimming.swimdaytickets.tickets_for_sale, swimming.user.phone_no, swimming.user.name FROM swimming.swimdaytickets INNER JOIN swimming.swimday ON swimming.swimdaytickets.swimdate=swimming.swimday.swimdate INNER JOIN swimming.user ON swimming.swimdaytickets.family_id=swimming.user.family_id INNER JOIN swimming.family ON swimming.swimdaytickets.family_id=swimming.family.family_id WHERE swimming.swimdaytickets.tickets_for_sale > 0 AND swimming.user.primary_user = 'yes' AND swimming.swimday.swimdate >= NOW() - INTERVAL 1 HOUR ORDER BY swimming.swimdaytickets.swimdate ASC, swimming.swimdaytickets.timestamp_sale ASC";
        String sql = "SELECT swimming.swimdaytickets.swimdate, swimming.swimdaytickets.family_id, swimming.swimday.week_no, swimming.swimday.team_id, swimming.swimdaytickets.tickets_for_sale, swimming.user.phone_no, swimming.user.name FROM swimming.swimdaytickets INNER JOIN swimming.swimday ON swimming.swimdaytickets.swimdate=swimming.swimday.swimdate INNER JOIN swimming.user ON swimming.swimdaytickets.family_id=swimming.user.family_id WHERE swimming.swimdaytickets.tickets_for_sale > 0 AND swimming.user.primary_user = 'yes' AND swimming.swimday.swimdate >= NOW() - INTERVAL 1 HOUR ORDER BY swimming.swimdaytickets.swimdate ASC, swimming.swimdaytickets.timestamp_sale ASC";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String swimdateS = "" + rs.getTimestamp("swimdate");
                    Timestamp swimdate = Timestamp.valueOf(swimdateS);
                    int familyId = rs.getInt("family_id");
                    int weekNo = rs.getInt("week_no");
                    String team = rs.getString("team_id");
                    int ticketsForSaleFromOneFamily = rs.getInt("tickets_for_sale");
                    String familyPhoneNo = rs.getString("phone_no");
                    String familyName = rs.getString("name");
                    //Timestamp timestamp = rs.getTimestamp("timestamp");

                    //ForSaleDTO forSaleDTO = new ForSaleDTO(swimdate, familyId, weekNo, ticketsForSaleFromOneFamily, timestamp);
                    int toTime = Integer.parseInt(swimdateS.substring(11, 13)) + 1;
                    String splitSwimdate = swimdateS.substring(8, 10) + "/" + swimdateS.substring(5,7) + " : " + swimdateS.substring(11, 13) + "-" + toTime;
                    ForSaleDTO forSaleDTO = new ForSaleDTO(swimdate, splitSwimdate, familyId, weekNo, team, ticketsForSaleFromOneFamily, familyPhoneNo, familyName);
                    forSaleDTOList.add(forSaleDTO);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning fra databasen");
        }
        return forSaleDTOList;

    }
}
