package dat.startcode.model.dtos;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Swimday {
    private String swimday;
    private int weekNo;
    private String team;

    public Swimday(String swimday, int weekNo, String team) {
        this.swimday = swimday;
        this.weekNo = weekNo;
        this.team = team;
    }

    public String getSwimday() {
        return swimday;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public String getTeam() {
        return team;
    }
}
