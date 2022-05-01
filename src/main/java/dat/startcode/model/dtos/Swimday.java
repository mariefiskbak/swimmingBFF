package dat.startcode.model.dtos;

import java.time.LocalDate;

public class Swimday {
    private LocalDate swimday;
    private int weekNo;
    private String team;

    public Swimday(LocalDate swimday, int weekNo, String team) {
        this.swimday = swimday;
        this.weekNo = weekNo;
        this.team = team;
    }

    public LocalDate getSwimday() {
        return swimday;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public String getTeam() {
        return team;
    }
}
