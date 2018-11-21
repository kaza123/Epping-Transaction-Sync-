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
public class TGreenLeavesWeighDetail implements Serializable {

    private Integer indexNo;
    private Integer bagCount;
    private BigDecimal quantity;
    private Integer tGreenLeavesWeigh;

    public TGreenLeavesWeighDetail() {
    }

    public TGreenLeavesWeighDetail(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getBagCount() {
        return bagCount;
    }

    public void setBagCount(Integer bagCount) {
        this.bagCount = bagCount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getTGreenLeavesWeigh() {
        return tGreenLeavesWeigh;
    }

    public void setTGreenLeavesWeigh(Integer tGreenLeavesWeigh) {
        this.tGreenLeavesWeigh = tGreenLeavesWeigh;
    }

    
}
