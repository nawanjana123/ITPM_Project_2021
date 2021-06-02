/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.model;

/**
 *
 * @author personal
 */
public class preferenceDate {

    private int id;
    private String date1;
    private String date2;
    private String date3;
    private String date4;
    private String date5;
    private int status;
    private String remark;
    private int deliveryPlanId;

    @Override
    public String toString() {
        return "preferenceDate{" + "id=" + id + ", date1=" + date1 + ", date2="
                + date2 + ", date3=" + date3 + ", date4=" + date4 + ", date5="
                + date5 + ", status=" + status + ", remark=" + remark + ", deliveryPlanId="
                + deliveryPlanId + '}';
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the date1
     */
    public String getDate1() {
        return date1;
    }

    /**
     * @param date1 the date1 to set
     */
    public void setDate1(String date1) {
        this.date1 = date1;
    }

    /**
     * @return the date2
     */
    public String getDate2() {
        return date2;
    }

    /**
     * @param date2 the date2 to set
     */
    public void setDate2(String date2) {
        this.date2 = date2;
    }

    /**
     * @return the date3
     */
    public String getDate3() {
        return date3;
    }

    /**
     * @param date3 the date3 to set
     */
    public void setDate3(String date3) {
        this.date3 = date3;
    }

    /**
     * @return the date4
     */
    public String getDate4() {
        return date4;
    }

    /**
     * @param date4 the date4 to set
     */
    public void setDate4(String date4) {
        this.date4 = date4;
    }

    /**
     * @return the date5
     */
    public String getDate5() {
        return date5;
    }

    /**
     * @param date5 the date5 to set
     */
    public void setDate5(String date5) {
        this.date5 = date5;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the deliveryPlanId
     */
    public int getDeliveryPlanId() {
        return deliveryPlanId;
    }

    /**
     * @param deliveryPlanId the deliveryPlanId to set
     */
    public void setDeliveryPlanId(int deliveryPlanId) {
        this.deliveryPlanId = deliveryPlanId;
    }

}
