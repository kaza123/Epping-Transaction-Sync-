/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author kasun
 */
public class Route {
    private String RNO;
    private String RNAME;
    private Integer EMPNO;
    private String USERID;
    private Float TranChg;
    private String Bank;
    private float EPRate;
    private String SSMA_TimeStamp;
    private boolean isSync;
    

    public Route() {
    }

    public Route(String RNO, String RNAME, Integer EMPNO, String USERID, Float TranChg, String Bank, float EPRate, String SSMA_TimeStamp, boolean isSync) {
        this.RNO = RNO;
        this.RNAME = RNAME;
        this.EMPNO = EMPNO;
        this.USERID = USERID;
        this.TranChg = TranChg;
        this.Bank = Bank;
        this.EPRate = EPRate;
        this.SSMA_TimeStamp = SSMA_TimeStamp;
        this.isSync = isSync;
    }

    public String getRNO() {
        return RNO;
    }

    public void setRNO(String RNO) {
        this.RNO = RNO;
    }

    public String getRNAME() {
        return RNAME;
    }

    public void setRNAME(String RNAME) {
        this.RNAME = RNAME;
    }

    public Integer getEMPNO() {
        return EMPNO;
    }

    public void setEMPNO(Integer EMPNO) {
        this.EMPNO = EMPNO;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public Float getTranChg() {
        return TranChg;
    }

    public void setTranChg(Float TranChg) {
        this.TranChg = TranChg;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String Bank) {
        this.Bank = Bank;
    }

    public float getEPRate() {
        return EPRate;
    }

    public void setEPRate(float EPRate) {
        this.EPRate = EPRate;
    }

    public String getSSMA_TimeStamp() {
        return SSMA_TimeStamp;
    }

    public void setSSMA_TimeStamp(String SSMA_TimeStamp) {
        this.SSMA_TimeStamp = SSMA_TimeStamp;
    }

    public boolean isIsSync() {
        return isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }

   
    


}
