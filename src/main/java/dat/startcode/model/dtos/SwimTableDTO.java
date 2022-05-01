package dat.startcode.model.dtos;

import java.time.LocalDate;
import java.util.Date;

public class SwimTableDTO {

    private LocalDate swimday;
    private int weekNo;
    private int currentTicketAmount;
    private int ticketsForSale;

    public SwimTableDTO(LocalDate swimday, int weekNo, int currentTicketAmount, int ticketsForSale) {
        this.swimday = swimday;
        this.weekNo = weekNo;
        this.currentTicketAmount = currentTicketAmount;
        this.ticketsForSale = ticketsForSale;
    }

    public LocalDate getSwimday() {
        return swimday;
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
