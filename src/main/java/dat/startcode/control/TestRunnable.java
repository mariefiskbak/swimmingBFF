package dat.startcode.control;

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

    public TestRunnable(Timestamp swimdate, int buyFromFamilyId, int reserveAmount, int buyerFamilyId, boolean ticketsHaveBeenMoved, ConnectionPool connectionPool) {
        this.swimdate = swimdate;
        this.buyFromFamilyId = buyFromFamilyId;
        this.reserveAmount = reserveAmount;
        this.buyerFamilyId = buyerFamilyId;
        this.ticketsHaveBeenMoved = ticketsHaveBeenMoved;
        this.connectionPool = connectionPool;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MINUTES.sleep(4);
            if(!ticketsHaveBeenMoved) {
                SwimMapper swimMapper = new SwimMapper(connectionPool);
                swimMapper.regretBuying(swimdate, buyFromFamilyId, reserveAmount, buyerFamilyId);
                //TODO måske den så også skal logge brugeren ud, risikerer man ellers at en langsom person køber nogle andres reserverede billetter?
            }
        } catch (InterruptedException | DatabaseException e) {
            e.printStackTrace();
        }
    }
}
