package dat.startcode.control;

import dat.startcode.model.entities.TicketsHaveBeenMoved;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Runnable implements java.lang.Runnable {
    private Timestamp swimdate;
    private int buyFromFamilyId;
    private int reserveAmount;
    private int buyerFamilyId;
    private ConnectionPool connectionPool;
    private HttpSession session;

    public Runnable(Timestamp swimdate, int buyFromFamilyId, int reserveAmount, int buyerFamilyId, ConnectionPool connectionPool, HttpSession session) {
        this.swimdate = swimdate;
        this.buyFromFamilyId = buyFromFamilyId;
        this.reserveAmount = reserveAmount;
        this.buyerFamilyId = buyerFamilyId;
        this.connectionPool = connectionPool;
        this.session = session;
    }

    @Override
    public void run() {
        try {
            TicketsHaveBeenMoved t = new TicketsHaveBeenMoved(false);
            session.setAttribute("ticketsHaveBeenMoved", t);
            TimeUnit.MINUTES.sleep(1);
            t = (TicketsHaveBeenMoved) session.getAttribute("ticketsHaveBeenMoved");
            if(!t.getTicketsHaveBeenMoved()) {
                SwimMapper swimMapper = new SwimMapper(connectionPool);
                swimMapper.regretBuying(swimdate, buyFromFamilyId, reserveAmount, buyerFamilyId);
            }
        } catch (InterruptedException | DatabaseException e) {
            e.printStackTrace();
        }
    }
}
