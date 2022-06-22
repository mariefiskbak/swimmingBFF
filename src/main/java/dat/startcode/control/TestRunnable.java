package dat.startcode.control;

import dat.startcode.model.entities.TicketsHaveBeenMoved;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.SwimMapper;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class TestRunnable implements Runnable {
    private Timestamp swimdate;
    private int buyFromFamilyId;
    private int reserveAmount;
    private int buyerFamilyId;
    private boolean ticketsHaveBeenMoved;
    private ConnectionPool connectionPool;

    public TestRunnable(Timestamp swimdate, int buyFromFamilyId, int reserveAmount, int buyerFamilyId, ConnectionPool connectionPool) {
        this.swimdate = swimdate;
        this.buyFromFamilyId = buyFromFamilyId;
        this.reserveAmount = reserveAmount;
        this.buyerFamilyId = buyerFamilyId;
        this.connectionPool = connectionPool;
    }

    @Override
    public void run() {
        try {
            TicketsHaveBeenMoved t = new TicketsHaveBeenMoved(false);
            TimeUnit.MINUTES.sleep(1);
            //TODO dette trin virker ikke, booleanen er false selvom den er blevet sat til true ved fortryd køb
            ticketsHaveBeenMoved = t.getTicketsHaveBeenMoved();
            if(!ticketsHaveBeenMoved) {
                SwimMapper swimMapper = new SwimMapper(connectionPool);
                swimMapper.regretBuying(swimdate, buyFromFamilyId, reserveAmount, buyerFamilyId);
            }
        } catch (InterruptedException | DatabaseException e) {
            e.printStackTrace();
        }
    }
}
