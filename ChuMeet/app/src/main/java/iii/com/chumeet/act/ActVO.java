package iii.com.chumeet.act;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ActVO implements Serializable {
    private Integer actID;
    private Integer memID;
    private Timestamp actCreateDate;
    private String actName;
    private Integer actStatID;
    private Integer actTimeID;
    private Integer actPriID;
    private Integer actLocID;
    private Timestamp actStartDate;
    private Timestamp actEndDate;
    private Timestamp actSignStartDate;
    private Timestamp actSignEndDate;
    private Integer actITVType;
    private String actITVCount;
    private Integer actMemMax;
    private Integer actMemMin;
    private byte[] actImg;
    private byte[] actBN;
    private String actContent;
    private String actWeather;
    private String actWD;
    private String actWR;
    private Integer actIsHot;
    private Double actLong;
    private Double actLat;
    private String actLocName;
    private String actAdr;
    public Integer getActID() {
        return actID;
    }
    public void setActID(Integer actID) {
        this.actID = actID;
    }
    public Integer getMemID() {
        return memID;
    }
    public void setMemID(Integer memID) {
        this.memID = memID;
    }
    public String getActCreateDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd \nHH:mm a").format(actCreateDate);
        return date;
    }
    public void setActCreateDate(Timestamp actCreateDate) {
        this.actCreateDate = actCreateDate;
    }
    public String getActName() {
        return actName;
    }
    public void setActName(String actName) {
        this.actName = actName;
    }
    public Integer getActStatID() {
        return actStatID;
    }
    public void setActStatID(Integer actStatID) {
        this.actStatID = actStatID;
    }
    public Integer getActTimeID() {
        return actTimeID;
    }
    public void setActTimeID(Integer actTimeID) {
        this.actTimeID = actTimeID;
    }
    public Integer getActPriID() {
        return actPriID;
    }
    public void setActPriID(Integer actPriID) {
        this.actPriID = actPriID;
    }
    public Integer getActLocID() {
        return actLocID;
    }
    public void setActLocID(Integer actLocID) {
        this.actLocID = actLocID;
    }
    public String getActStartDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(actStartDate);
        return date;
    }
    public void setActStartDate(Timestamp actStartDate) {
        this.actStartDate = actStartDate;
    }
    public String getActEndDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(actEndDate);
        return date;
    }
    public void setActEndDate(Timestamp actEndDate) {
        this.actEndDate = actEndDate;
    }
    public String getActSignStartDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(actSignStartDate);
        return date;
    }
    public void setActSignStartDate(Timestamp actSignStartDate) {
        this.actSignStartDate = actSignStartDate;
    }
    public String getActSignEndDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(actSignEndDate);
        return date;
    }
    public void setActSignEndDate(Timestamp actSignEndDate) {
        this.actSignEndDate = actSignEndDate;
    }
    public Integer getActITVType() {
        return actITVType;
    }
    public void setActITVType(Integer actITVType) {
        this.actITVType = actITVType;
    }
    public String getActITVCount() {
        return actITVCount;
    }
    public void setActITVCount(String actITVCount) {
        this.actITVCount = actITVCount;
    }
    public Integer getActMemMax() {
        return actMemMax;
    }
    public void setActMemMax(Integer actMemMax) {
        this.actMemMax = actMemMax;
    }
    public Integer getActMemMin() {
        return actMemMin;
    }
    public void setActMemMin(Integer actMemMin) {
        this.actMemMin = actMemMin;
    }
    public byte[] getActImg() {
        return actImg;
    }
    public void setActImg(byte[] actImg) {
        this.actImg = actImg;
    }
    public byte[] getActBN() {
        return actBN;
    }
    public void setActBN(byte[] actBN) {
        this.actBN = actBN;
    }
    public String getActContent() {
        return actContent;
    }
    public void setActContent(String actContent) {
        this.actContent = actContent;
    }
    public String getActWeather() {
        return actWeather;
    }
    public void setActWeather(String actWeather) {
        this.actWeather = actWeather;
    }
    public String getActWD() {
        return actWD;
    }
    public void setActWD(String actWD) {
        this.actWD = actWD;
    }
    public String getActWR() {
        return actWR;
    }
    public void setActWR(String actWR) {
        this.actWR = actWR;
    }
    public Integer getActIsHot() {
        return actIsHot;
    }
    public void setActIsHot(Integer actIsHot) {
        this.actIsHot = actIsHot;
    }
    public Double getActLong() {
        return actLong;
    }
    public void setActLong(Double actLong) {
        this.actLong = actLong;
    }
    public Double getActLat() {
        return actLat;
    }
    public void setActLat(Double actLat) {
        this.actLat = actLat;
    }
    public String getActLocName() {
        return actLocName;
    }
    public void setActLocName(String actLocName) {
        this.actLocName = actLocName;
    }
    public String getActAdr() {
        return actAdr;
    }
    public void setActAdr(String actAdr) {
        this.actAdr = actAdr;
    }

}

