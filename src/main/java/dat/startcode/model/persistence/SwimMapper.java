package dat.startcode.model.persistence;

import ch.qos.logback.core.db.dialect.DBUtil;
import dat.startcode.model.dtos.ForSaleDTO;
import dat.startcode.model.dtos.SwimTableDTO;
import dat.startcode.model.dtos.Swimday;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwimMapper {
    ConnectionPool connectionPool;

    public SwimMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<SwimTableDTO> getSwimTableDTOList(int familyId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<SwimTableDTO> swimTableDTOList = new ArrayList<>();
        String familyIdS = "" + familyId;

        //Connection connection = null;

        String sql = "SELECT swimming.swimdaytickets.swimdate, swimming.swimday.week_no, swimming.swimday.team_id, swimming.swimdaytickets.current_ticket_amount, swimming.swimdaytickets.tickets_for_sale FROM swimming.swimdaytickets INNER JOIN swimming.swimday ON swimming.swimdaytickets.swimdate=swimming.swimday.swimdate WHERE swimming.swimdaytickets.family_id = ? AND swimming.swimday.swimdate >= NOW() - INTERVAL 1 HOUR AND (swimming.swimdaytickets.current_ticket_amount > 0 OR swimming.swimdaytickets.tickets_for_sale > 0) ORDER BY swimming.swimdaytickets.swimdate";
        try (Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
        //try (connection = connectionPool.getConnection()) {
            //try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, familyIdS);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String swimdateS = "" + rs.getTimestamp("swimdate");
                    Timestamp swimdate = Timestamp.valueOf(swimdateS);
                    int weekNo = rs.getInt("week_no");
                    String teamID = rs.getString("team_id");
                    int currentTicketAmount = rs.getInt("current_ticket_amount");
                    int ticketsForSale = rs.getInt("tickets_for_sale");

                    int toTime = Integer.parseInt(swimdateS.substring(11, 13)) + 1;
                    String splitSwimdate = swimdateS.substring(8, 10) + "/" + swimdateS.substring(5, 7) + " : " + swimdateS.substring(11, 13) + "-" + toTime;
                    SwimTableDTO swimTableDTO = new SwimTableDTO(swimdate, splitSwimdate, weekNo, teamID, currentTicketAmount, ticketsForSale);
                    swimTableDTOList.add(swimTableDTO);

//                    rs.close();
//                    ps.close();
//                    connection.close();
                }
//            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning fra databasen");
        } // finally {
//            try { connection.close(); } catch (Exception e) { /* Ignored */ }
//        }
        return swimTableDTOList;
    }


    public void sell(Timestamp swimdate, int familyId, int sellAmount, Timestamp timestamp) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        //Move tickets from currentTicketAmount to ticketsForSale

        String sellAmountS = "" + sellAmount;
        String swimdateS = "" + swimdate;
        String familyIdS = "" + familyId;
        String timestampS = "" + timestamp;

        String sql = "UPDATE swimming.swimdaytickets SET current_ticket_amount = current_ticket_amount - ? , tickets_for_sale = tickets_for_sale + ?, timestamp_sale = ? WHERE swimdate = ? AND family_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, sellAmountS);
                ps.setString(2, sellAmountS);
                ps.setString(3, timestampS);
                ps.setString(4, swimdateS);
                ps.setString(5, familyIdS);
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


    public void regret(Timestamp swimdate, int familyId, int regretAmount) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        //Move tickets from currentTicketAmount to ticketsForSale

        String regretAmountS = "" + regretAmount;
        String swimdateS = "" + swimdate;
        String familyIdS = "" + familyId;

        String sql = "UPDATE swimming.swimdaytickets SET tickets_for_sale = tickets_for_sale - ? , current_ticket_amount = current_ticket_amount + ? WHERE swimdate = ? AND family_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, regretAmountS);
                ps.setString(2, regretAmountS);
                ps.setString(3, swimdateS);
                ps.setString(4, familyIdS);
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


    public List<ForSaleDTO> getForSaleDTOList() throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<ForSaleDTO> forSaleDTOList = new ArrayList<>();

        //TODO tror at family tabellen er ligegyldig
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
                    String splitSwimdate = swimdateS.substring(8, 10) + "/" + swimdateS.substring(5, 7) + " : " + swimdateS.substring(11, 13) + "-" + toTime;
                    ForSaleDTO forSaleDTO = new ForSaleDTO(swimdate, splitSwimdate, familyId, weekNo, team, ticketsForSaleFromOneFamily, familyPhoneNo, familyName);
                    forSaleDTOList.add(forSaleDTO);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning fra databasen");
        }
        return forSaleDTOList;
    }


    public List<Swimday> getSwimdays() throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<Swimday> swimdayList = new ArrayList<>();

        String sql = "SELECT * FROM swimming.swimday WHERE swimming.swimday.swimdate >= NOW() - INTERVAL 1 HOUR ORDER BY swimming.swimday.swimdate ASC";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String swimdateS = "" + rs.getTimestamp("swimdate");
                    Timestamp swimdate = Timestamp.valueOf(swimdateS);
                    int weekNo = rs.getInt("week_no");
                    String team = rs.getString("team_id");

                    int toTime = Integer.parseInt(swimdateS.substring(11, 13)) + 1;
                    String splitSwimdate = swimdateS.substring(8, 10) + "/" + swimdateS.substring(5, 7) + " : " + swimdateS.substring(11, 13) + "-" + toTime;
                    Swimday swimday = new Swimday(splitSwimdate, weekNo, team);
                    swimdayList.add(swimday);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning fra databasen");
        }
        return swimdayList;


    }

//    public void buy(Timestamp swimdate, int buyFromFamilyId, int buyAmount, int buyerFamilyId) throws DatabaseException {
//        Logger.getLogger("web").log(Level.INFO, "");
//
//        //Move tickets from reservedTickets from buyFromFamilyId to currentTicketAmount at buyerFamilyId
//
//        String buyAmountS = "" + buyAmount;
//        String swimdateS = "" + swimdate;
//        String buyFromFamilyIdS = "" + buyFromFamilyId;
//        String buyerFamilyIdS = "" + buyerFamilyId;
//
//        //But first Check wether buyer has any swimdaytickets
//        String sqlCheck = "SELECT * FROM swimming.swimdaytickets WHERE swimdate = ? AND family_id = ?";
//        try (Connection connection = connectionPool.getConnection()) {
//            try (PreparedStatement ps = connection.prepareStatement(sqlCheck)) {
//                ps.setString(1, swimdateS);
//                ps.setString(2, buyerFamilyIdS);
//                ResultSet rs = ps.executeQuery();
//                if (!rs.next()) {
//
//                //If empty, then create a row
//                    String sqlCreate = "INSERT INTO swimming.swimdaytickets (swimdate, family_id, current_ticket_amount) VALUES (?, ?, ?)";
//                    try (Connection connection2 = connectionPool.getConnection()) {
//                        try (PreparedStatement ps2 = connection2.prepareStatement(sqlCreate)) {
//                            ps2.setString(1, swimdateS);
//                            ps2.setString(2, buyerFamilyIdS);
//                            ps2.setString(3, "0");
//                            int rowsAffected = ps2.executeUpdate();
//                            if (rowsAffected == 1) {
//                            } else {
//                                throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
//                            }
//                        }
//                    } catch (SQLException | DatabaseException exe) {
//                        throw new DatabaseException(exe, "Kunne ikke opdatere svømmebilletter");
//                    }
//                }
//            }
//        } catch (SQLException exep) {
//            throw new DatabaseException(exep, "Kunne ikke lede efter svømmebilletter");
//        }
//
//
//        String sql = "UPDATE swimming.swimdaytickets SET reserved_tickets = reserved_tickets - ? WHERE swimdate = ? AND family_id = ?";
//        try (Connection connection = connectionPool.getConnection()) {
//            try (PreparedStatement ps = connection.prepareStatement(sql)) {
//                ps.setString(1, buyAmountS);
//                ps.setString(2, swimdateS);
//                ps.setString(3, buyFromFamilyIdS);
//                int rowsAffected = ps.executeUpdate();
//                if (rowsAffected == 1) {
//
//                } else {
//                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
//                }
//            }
//        } catch (SQLException | DatabaseException ex) {
//            throw new DatabaseException(ex, "Kunne ikke opdatere svømmebilletter");
//        }
//
//        String sql2 = "UPDATE swimming.swimdaytickets SET current_ticket_amount = current_ticket_amount + ? WHERE swimdate = ? AND family_id = ?";
//        try (Connection connection = connectionPool.getConnection()) {
//            try (PreparedStatement ps = connection.prepareStatement(sql2)) {
//                ps.setString(1, buyAmountS);
//                ps.setString(2, swimdateS);
//                ps.setString(3, buyerFamilyIdS);
//                int rowsAffected = ps.executeUpdate();
//                if (rowsAffected == 1) {
//
//                } else {
//                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
//                }
//            }
//        } catch (SQLException | DatabaseException ex) {
//            throw new DatabaseException(ex, "Kunne ikke opdatere svømmebilletter");
//        }
//
//    }

    public void buy(Timestamp swimdate, int buyFromFamilyId, int buyAmount, int buyerFamilyId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        //Move tickets from reservedTickets from buyFromFamilyId to currentTicketAmount at buyerFamilyId

        String buyAmountS = "" + buyAmount;
        String swimdateS = "" + swimdate;
        String buyFromFamilyIdS = "" + buyFromFamilyId;
        String buyerFamilyIdS = "" + buyerFamilyId;

        //But first Check wether buyer has any swimdaytickets
        String sqlCheck = "SELECT * FROM swimming.swimdaytickets WHERE swimdate = ? AND family_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlCheck)) {
                ps.setString(1, swimdateS);
                ps.setString(2, buyerFamilyIdS);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {

                    //If empty, then create a row
                    String sqlCreate = "INSERT INTO swimming.swimdaytickets (swimdate, family_id, current_ticket_amount) VALUES (?, ?, ?)";
                    try (PreparedStatement ps2 = connection.prepareStatement(sqlCreate)) {
                        ps2.setString(1, swimdateS);
                        ps2.setString(2, buyerFamilyIdS);
                        ps2.setString(3, "0");
                        int rowsAffected = ps2.executeUpdate();
                        if (rowsAffected == 1) {
                        } else {
                            throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                        }
                    }
                }
            }

            String sql = "UPDATE swimming.swimdaytickets SET reserved_tickets = reserved_tickets - ? WHERE swimdate = ? AND family_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, buyAmountS);
                ps.setString(2, swimdateS);
                ps.setString(3, buyFromFamilyIdS);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                    String sql2 = "UPDATE swimming.swimdaytickets SET current_ticket_amount = current_ticket_amount + ? WHERE swimdate = ? AND family_id = ?";
                    try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                        ps2.setString(1, buyAmountS);
                        ps2.setString(2, swimdateS);
                        ps2.setString(3, buyerFamilyIdS);
                        int rowsAffected2 = ps2.executeUpdate();
                        if (rowsAffected2 == 1) {

                        } else {
                            throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                        }
                    }

                } else {
                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                }
            }

        } catch (SQLException exep) {
            throw new DatabaseException(exep, "Kunne ikke lede efter svømmebilletter");
        }
    }


    public String getFamilyName(int buyerFamilyId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        String buyerFamilyIdS = "" + buyerFamilyId;
        String buyerFamilyName = "";

        String sql = "SELECT swimming.user.name FROM swimming.user WHERE swimming.user.family_id = ? AND swimming.user.primary_user = 'yes'";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, buyerFamilyIdS);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    buyerFamilyName = rs.getString("name");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning fra databasen");
        }
        return buyerFamilyName;
    }


    public void reserve(Timestamp swimdate, int buyFromFamilyId, int reserveAmount, int buyerFamilyId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        //Move tickets from ticketsForSale from buyFromFamilyId to reservedTickets

        String reserveAmountS = "" + reserveAmount;
        String swimdateS = "" + swimdate;
        String buyFromFamilyIdS = "" + buyFromFamilyId;
        String buyerFamilyIdS = "" + buyerFamilyId;

        String sql = "UPDATE swimming.swimdaytickets SET tickets_for_sale = tickets_for_sale - ? WHERE swimdate = ? AND family_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, reserveAmountS);
                ps.setString(2, swimdateS);
                ps.setString(3, buyFromFamilyIdS);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    String sql2 = "UPDATE swimming.swimdaytickets SET reserved_tickets = reserved_tickets + ? WHERE swimdate = ? AND family_id = ?";
                    try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                        ps2.setString(1, reserveAmountS);
                        ps2.setString(2, swimdateS);
                        ps2.setString(3, buyFromFamilyIdS);
                        int rowsAffected2 = ps2.executeUpdate();
                        if (rowsAffected2 == 1) {

                        } else {
                            throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                        }
                    }

                } else {
                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                }
            }
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex, "Kunne ikke opdatere svømmebilletter");
        }

    }


    public void regretBuying(Timestamp swimdate, int buyFromFamilyId, int buyAmount, int buyerFamilyId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        //Move tickets from reservedTickets from buyFromFamilyId to ticketsForSale

        String buyAmountS = "" + buyAmount;
        String swimdateS = "" + swimdate;
        String buyFromFamilyIdS = "" + buyFromFamilyId;
        String buyerFamilyIdS = "" + buyerFamilyId;

        String sql = "UPDATE swimming.swimdaytickets SET reserved_tickets = reserved_tickets - ? WHERE swimdate = ? AND family_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, buyAmountS);
                ps.setString(2, swimdateS);
                ps.setString(3, buyFromFamilyIdS);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                    String sql2 = "UPDATE swimming.swimdaytickets SET tickets_for_sale = tickets_for_sale + ? WHERE swimdate = ? AND family_id = ?";
                    try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                        ps2.setString(1, buyAmountS);
                        ps2.setString(2, swimdateS);
                        ps2.setString(3, buyFromFamilyIdS);
                        int rowsAffected2 = ps2.executeUpdate();
                        if (rowsAffected2 == 1) {

                        } else {
                            throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                        }
                    }

                } else {
                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                }
            }
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex, "Kunne ikke opdatere svømmebilletter");
        }
    }
}
