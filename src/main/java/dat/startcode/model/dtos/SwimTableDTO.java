package dat.startcode.model.dtos;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class SwimTableDTO {

    private Timestamp swimday;
    private String splitSwimday;
    private int weekNo;
    private int currentTicketAmount;
    private int ticketsForSale;

    public SwimTableDTO(Timestamp swimday, String splitSwimday, int weekNo, int currentTicketAmount, int ticketsForSale) {
        this.swimday = swimday;
        this.splitSwimday = splitSwimday;
        this.weekNo = weekNo;
        this.currentTicketAmount = currentTicketAmount;
        this.ticketsForSale = ticketsForSale;
    }

    public Timestamp getSwimday() {
        return swimday;
    }

    public String getSplitSwimday() {
        return splitSwimday;
    }

    public int getTicketsForSale() {
        return ticketsForSale;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public int getCurrentTicketAmount() {
        return currentTicketAmount;
    }
}
