package dat.startcode.model.dtos;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

public class ForSaleDTO {
    private Timestamp swimday;
    private String splitSwimday;
    private int familyId;
    private int weekNo;
    private String team;
    private int amountForSaleFromOneFamily;
    private Timestamp timestamp;
    private int familyPhoneNo;
    private String familyName;

    //public ForSaleDTO(LocalDate swimday, int familyId, int weekNo, int amountForSaleFromOneFamily, Timestamp timestamp) {
    public ForSaleDTO(Timestamp swimday, String splitSwimday, int familyId, int weekNo, String team, int amountForSaleFromOneFamily, int familyPhoneNo, String familyName) {
        this.swimday = swimday;
        this.splitSwimday = splitSwimday;
        this.familyId = familyId;
        this.weekNo = weekNo;
        this.team = team;
        this.amountForSaleFromOneFamily = amountForSaleFromOneFamily;
        this.familyPhoneNo = familyPhoneNo;
        this.familyName = familyName;
        //this.timestamp = timestamp;
    }

    public Timestamp getSwimday() {
        return swimday;
    }

    public String getSplitSwimday() {
        return splitSwimday;
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

    @Override
    public String toString() {
        return "ForSaleDTO{" +
                "swimday=" + swimday +
                ", splitSwimday='" + splitSwimday + '\'' +
                ", familyId=" + familyId +
                ", weekNo=" + weekNo +
                ", team='" + team + '\'' +
                ", amountForSaleFromOneFamily=" + amountForSaleFromOneFamily +
                ", timestamp=" + timestamp +
                ", familyPhoneNo=" + familyPhoneNo +
                ", familyName='" + familyName + '\'' +
                '}';
    }
}
