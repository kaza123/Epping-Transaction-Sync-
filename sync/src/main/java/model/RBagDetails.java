/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author kasun
 */
public class RBagDetails implements Serializable {

    private Integer indexNo;
    private String bagNo;
    private BigDecimal quantity;
    private Integer tGreenLeavesWeighDetail;

    public RBagDetails() {
    }

    public RBagDetails(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getBagNo() {
        return bagNo;
    }

    public void setBagNo(String bagNo) {
        this.bagNo = bagNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getTGreenLeavesWeighDetail() {
        return tGreenLeavesWeighDetail;
    }

    public void setTGreenLeavesWeighDetail(Integer tGreenLeavesWeighDetail) {
        this.tGreenLeavesWeighDetail = tGreenLeavesWeighDetail;
    }

}
