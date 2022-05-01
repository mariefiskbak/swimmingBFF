package dat.startcode.model.entities;

public class Message {
    private String message;
    private int messageId;
    private int familyId;

    public Message(String message, int messageId, int familyId) {
        this.message = message;
        this.messageId = messageId;
        this.familyId = familyId;
    }

    public String getMessage() {
        return message;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getFamilyId() {
        return familyId;
    }
}
