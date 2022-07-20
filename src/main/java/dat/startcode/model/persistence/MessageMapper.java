package dat.startcode.model.persistence;

import dat.startcode.model.dtos.ForSaleDTO;
import dat.startcode.model.entities.Message;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageMapper {

    ConnectionPool connectionPool;

    public MessageMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void putMessageInDB(int buyFromFamilyId, String message) throws DatabaseException {
        //TODO det kunne godt være brugerens navn, da man nok kan antage at den der er logget ind også er den,
        // der overfører. Kræver måske en større ændring, også af getMessageList, evt kan begge navne fremgå.
        // IDE, hvis det ikke er primary user der køber, SÅ står begge navne der.
        Logger.getLogger("web").log(Level.INFO, "");
        String buyFromFamilyIdS = "" + buyFromFamilyId;
        String sql = "insert into swimming.messages (family_id, message) values (?,?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, buyFromFamilyIdS);
                ps.setString(2, message);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                } else {
                    throw new DatabaseException("The message could not be inserted into the database");
                }
            }
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex, "Could not insert email into database");
        }

    }

    public List<Message> getMessageList(int familyId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        String familyIdS = "" + familyId;

        List<Message> messageList = new ArrayList<>();

        String sql = "SELECT * FROM swimming.messages WHERE swimming.messages.family_id = ? ORDER BY swimming.messages.message_id DESC";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, familyIdS);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String message = rs.getString("message");
                    int messageId = rs.getInt("message_id");

                    Message messageObject = new Message(message, messageId, familyId);
                    messageList.add(messageObject);

                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning fra databasen");
        }
        return messageList;

    }

    public void deleteMessage(int messageId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        String messageIdS = "" + messageId;

        String sql = "DELETE FROM swimming.messages WHERE swimming.messages.message_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, messageIdS);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                } else {
                    throw new DatabaseException("Beskederne blev ikke opdateret");
                }
            }
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex, "Kunne ikke opdatere beskeder");
        }
    }
}
