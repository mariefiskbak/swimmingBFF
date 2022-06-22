package dat.startcode.model.entities;

import javax.servlet.http.HttpSession;

public class TicketsHaveBeenMoved {
    private Boolean ticketsHaveBeenMoved;

    public TicketsHaveBeenMoved(Boolean ticketsHaveBeenMoved) {
        this.ticketsHaveBeenMoved = ticketsHaveBeenMoved;
    }

    public Boolean getTicketsHaveBeenMoved() {
        return ticketsHaveBeenMoved;
    }

    public void setTicketsHaveBeenMoved(Boolean ticketsHaveBeenMoved) {
        this.ticketsHaveBeenMoved = ticketsHaveBeenMoved;
    }
}
