/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author kasun
 */
public class TGreenLeavesWeigh implements Serializable {

   
    private Integer indexNo;
    private BigDecimal boiledLeaves;
    private Integer branch;
    private BigDecimal coarseLeaves;
    private String date;
    private BigDecimal generalDeduction;
    private BigDecimal generalDeductionPercent;
    private String greenLeavesType;
    private String colNo;//login user
    private BigDecimal netWeight;
    private String routeNo;//route
    private String serial;
    private String status;
    private String supNo;//supplier
    private BigDecimal tareCalculated;
    private BigDecimal tareDeduction;
    private String tempSupplierRemark;
    private Integer totalBagCount;
    private BigDecimal totalWeight;
    private BigDecimal waterDeduction;

    public TGreenLeavesWeigh() {
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public BigDecimal getBoiledLeaves() {
        return boiledLeaves;
    }

    public void setBoiledLeaves(BigDecimal boiledLeaves) {
        this.boiledLeaves = boiledLeaves;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public BigDecimal getCoarseLeaves() {
        return coarseLeaves;
    }

    public void setCoarseLeaves(BigDecimal coarseLeaves) {
        this.coarseLeaves = coarseLeaves;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getGeneralDeduction() {
        return generalDeduction;
    }

    public void setGeneralDeduction(BigDecimal generalDeduction) {
        this.generalDeduction = generalDeduction;
    }

    public BigDecimal getGeneralDeductionPercent() {
        return generalDeductionPercent;
    }

    public void setGeneralDeductionPercent(BigDecimal generalDeductionPercent) {
        this.generalDeductionPercent = generalDeductionPercent;
    }

    public String getGreenLeavesType() {
        return greenLeavesType;
    }

    public void setGreenLeavesType(String greenLeavesType) {
        this.greenLeavesType = greenLeavesType;
    }

    public String getColNo() {
        return colNo;
    }

    public void setColNo(String colNo) {
        this.colNo = colNo;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupNo() {
        return supNo;
    }

    public void setSupNo(String supNo) {
        this.supNo = supNo;
    }

    public BigDecimal getTareCalculated() {
        return tareCalculated;
    }

    public void setTareCalculated(BigDecimal tareCalculated) {
        this.tareCalculated = tareCalculated;
    }

    public BigDecimal getTareDeduction() {
        return tareDeduction;
    }

    public void setTareDeduction(BigDecimal tareDeduction) {
        this.tareDeduction = tareDeduction;
    }

    public String getTempSupplierRemark() {
        return tempSupplierRemark;
    }

    public void setTempSupplierRemark(String tempSupplierRemark) {
        this.tempSupplierRemark = tempSupplierRemark;
    }

    public Integer getTotalBagCount() {
        return totalBagCount;
    }

    public void setTotalBagCount(Integer totalBagCount) {
        this.totalBagCount = totalBagCount;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getWaterDeduction() {
        return waterDeduction;
    }

    public void setWaterDeduction(BigDecimal waterDeduction) {
        this.waterDeduction = waterDeduction;
    }

    
}
