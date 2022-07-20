package dat.startcode.model.persistence;
import dat.startcode.model.dtos.ForSaleDTO;
import dat.startcode.model.dtos.Swimday;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
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
        if (week.equals("l")) {
            teamId1 = "lige uge fre 9-10";
            teamId2 = "lige uge fre 10-11";
        }
        if (week.equals("u")) {
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

    public void insertUsers() throws DatabaseException {

        //String sql insert user

        // For each swimming.swimday.swimdate skal den sætte alle dagene på med 0 billetter
        // Det ovenfor vil give ca 2000 rækker i tabellen. Alternativt, så inden folk køber billetter, så tjekker den om de
        //hAR Nogle swimdaytickets og hvis ikke opretter den en række...
        //Alternativet er fixet nu


//        String sql = "INSERT INTO swimming.swimdaytickets (swimdate, family_id, current_ticket_amount) VALUES (?, ?, ?)";
//        try (Connection connection = connectionPool.getConnection()) {
//            try (PreparedStatement ps = connection.prepareStatement(sql)) {
//                ps.setString(1, swimdates);
//                ps.setInt(2, familyID);
//                ps.setInt(3, 0);
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
    }

    public void createRegistration(int familyID, String teamID, int amount) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
//TODO måske skal kun dagene efter dags dato tilføjes til registreringen. Kun relevant,
// hvis der kommer registreringer efter sæsonen er gået i gang og den konkrete familie tidligere har købt billetter
// og jeg vil kigge på databasen for at få noget info om tidligere billetter
        //Hente alle svømmedagene der hører til teamID og putte dem i en liste

        List<Swimday> swimdaysLists = new ArrayList<>();

        String sql = "SELECT * FROM swimming.swimday WHERE swimming.swimday.team_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, teamID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String swimdateS = "" + rs.getTimestamp("swimdate");
                    Timestamp swimdate = Timestamp.valueOf(swimdateS);
                    int weekNo = rs.getInt("week_no");
                    String team = rs.getString("team_id");

                    Swimday swimday = new Swimday(swimdateS, weekNo, team);
                    swimdaysLists.add(swimday);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning fra databasen");
        }

        //for every swimday on the list, create swimdaytickets on the day and the familyID with the amount
        for (Swimday swimdaysList : swimdaysLists) {
            String swimdateS = swimdaysList.getSwimday();
            // Look if there already exist a swimdayticket on the day with that familyID
            String sqlSearch = "SELECT * FROM swimming.swimdaytickets WHERE swimdate = ? AND family_id = ?";
            try (Connection connection1 = connectionPool.getConnection()) {
                try (PreparedStatement ps1 = connection1.prepareStatement(sqlSearch)) {
                    ps1.setString(1, swimdateS);
                    ps1.setInt(2, familyID);
                    ResultSet rs = ps1.executeQuery();
                    if (rs.next()) {
                        // If a swimdayticket already exists, then update it
                        String sqlUpdate = "UPDATE swimming.swimdaytickets SET current_ticket_amount = current_ticket_amount + ? WHERE swimdate = ? AND family_id = ?";
                        try (Connection connection = connectionPool.getConnection()) {
                            try (PreparedStatement ps = connection.prepareStatement(sqlUpdate)) {
                                ps.setInt(1, amount);
                                ps.setString(2, swimdateS);
                                ps.setInt(3, familyID);
                                int rowsAffected = ps.executeUpdate();
                                if (rowsAffected == 1) {

                                } else {
                                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                                }
                            }
                        } catch (SQLException | DatabaseException ex) {
                            throw new DatabaseException(ex, "Kunne ikke opdatere svømmebilletter");
                        }
                    } else {
                        // Otherwose create a new one
                        String sqlCreate = "INSERT INTO swimming.swimdaytickets (swimdate, family_id, current_ticket_amount) VALUES (?, ?, ?)";
                        try (Connection connection2 = connectionPool.getConnection()) {
                            try (PreparedStatement ps2 = connection2.prepareStatement(sqlCreate)) {
                                ps2.setString(1, swimdateS);
                                ps2.setInt(2, familyID);
                                ps2.setInt(3, amount);
                                int rowsAffected = ps2.executeUpdate();
                                if (rowsAffected == 1) {
                                } else {
                                    throw new DatabaseException("Svømmebilletterne blev ikke opdateret");
                                }
                            }
                        } catch (SQLException | DatabaseException exe) {
                            throw new DatabaseException(exe, "Kunne ikke opdatere svømmebilletter");
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        // Insert the registration to the registration table
        String sqlRegistration = "INSERT INTO swimming.registration (family_id, team_id, ticket_amount) VALUES (?, ?, ?)";
        try (Connection connection2 = connectionPool.getConnection()) {
            try (PreparedStatement ps2 = connection2.prepareStatement(sqlRegistration)) {
                ps2.setInt(1, familyID);
                ps2.setString(2, teamID);
                ps2.setInt(3, amount);
                int rowsAffected = ps2.executeUpdate();
                if (rowsAffected == 1) {
                } else {
                    throw new DatabaseException("Registreringen blev ikke opdateret");
                }
            }
        } catch (SQLException | DatabaseException exe) {
            throw new DatabaseException(exe, "Kunne ikke opdatere registreringen");
        }

    }

    //TODO familytabellen ER vist alligevel vigtig.
    // Altså designet kunne have været anderledes, men nu er der en masse fremmednøgler knyttet til den tabel
    // Brugerne skal skrives ind via siden, så family-tabellen automatisk kan blive udfyldt samtidig.
    // Men skal lige finde ud af i hvilken rækkefølge det kan lade sig gøre

    public void createUser(int familyID, String name, String email, String phone, String password, String primaryUser) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        //først sæt ind i family-tballen
        //Tjek om en fra familien allerede er i family-tabellen
        String sqlSearch = "SELECT * FROM swimming.family WHERE family_id = ?";
        try (Connection connection1 = connectionPool.getConnection()) {
            try (PreparedStatement ps1 = connection1.prepareStatement(sqlSearch)) {
                ps1.setInt(1, familyID);
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    // If the family is already there, do nothing
                } else {
                    // Otherwose create a new one
        //Sæt ind i family tabellen
                    String sqlCreate = "INSERT INTO swimming.family (family_id, primary_user_email) VALUES (?, ?)";
                    try (Connection connection2 = connectionPool.getConnection()) {
                        try (PreparedStatement ps2 = connection2.prepareStatement(sqlCreate)) {
                            ps2.setInt(1, familyID);
                            ps2.setString(2, email);
                            int rowsAffected = ps2.executeUpdate();
                            if (rowsAffected == 1) {
                            } else {
                                throw new DatabaseException("Familietabellen blev ikke opdateret");
                            }
                        }
                    } catch (SQLException | DatabaseException exe) {
                        throw new DatabaseException(exe, "Kunne ikke opdatere familietabellen");
                    }
                }
            } catch (SQLException | DatabaseException throwables) {
                throwables.printStackTrace();
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //SÅ sæt ind i user-tabellen
        String sqlUser = "INSERT INTO swimming.user (email, phone_no, name, role, family_id, password, primary_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection2 = connectionPool.getConnection()) {
            try (PreparedStatement ps2 = connection2.prepareStatement(sqlUser)) {
                ps2.setString(1, email);
                ps2.setString(2, phone);
                ps2.setString(3, name);
                ps2.setString(4, "user");
                ps2.setInt(5, familyID);
                ps2.setString(6, password);
                ps2.setString(7, primaryUser);
                int rowsAffected = ps2.executeUpdate();
                if (rowsAffected == 1) {
                } else {
                    throw new DatabaseException("Brugertabellen blev ikke opdateret");
                }
            }
        } catch (SQLException | DatabaseException exe) {
            throw new DatabaseException(exe, "Kunne ikke opdatere brugertabellen");
        }
    }

    //TODO en metode der kan fjerne en svømmedag og folks billetter osv, i tilfælde af aflysning fra svømmehallen

    //TODO Ville være fedt, hvis man ikke bare skal vide family-id, men kan få vist et navn eller noget så man ved hvem det er. i dropdpwn fx, når man skal lave regostreringer
}
