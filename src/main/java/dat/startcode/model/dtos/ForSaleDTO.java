package dat.startcode.model.dtos;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

public class ForSaleDTO {
    private LocalDate swimday;
    private int familyId;
    private int weekNo;
    private String team;
    private int amountForSaleFromOneFamily;
    private Timestamp timestamp;
    private int familyPhoneNo;
    private String familyName;

    //public ForSaleDTO(LocalDate swimday, int familyId, int weekNo, int amountForSaleFromOneFamily, Timestamp timestamp) {
    public ForSaleDTO(LocalDate swimday, int familyId, int weekNo, String team, int amountForSaleFromOneFamily, int familyPhoneNo, String familyName) {
        this.swimday = swimday;
        this.familyId = familyId;
        this.weekNo = weekNo;
        this.team = team;
        this.amountForSaleFromOneFamily = amountForSaleFromOneFamily;
        this.familyPhoneNo = familyPhoneNo;
        this.familyName = familyName;
        //this.timestamp = timestamp;
    }

    public LocalDate getSwimday() {
        return swimday;
    }

    public int getFamilyId() {
        return familyId;
    }

    public int getWeekNo() {
        return weekNo;
    }
    public String getTeam() {
        return team;
    }

    public int getAmountForSaleFromOneFamily() {
        return amountForSaleFromOneFamily;
    }

    public int getFamilyPhoneNo() {
        return familyPhoneNo;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
