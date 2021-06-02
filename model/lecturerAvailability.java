/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Amal
 */
public class lecturerAvailability {

    public static int ACTIVE_AVAILABILITY_RECORD = 1;
    public static int INACTIVE_AVAILABILITY_RECORD = 0;

    private int id;
    private int lecturerId;
    private Date unavailableDate;
    private String unavailableTimeFrom;
    private String unavailableTimeTo;
    private int status;
    private String detail;

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
     * @return the lecturerId
     */
    public int getLecturerId() {
        return lecturerId;
    }

    /**
     * @param lecturerId the lecturerId to set
     */
    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    /**
     * @return the unavailableDate
     */
    public Date getUnavailableDate() {
        return unavailableDate;
    }

    /**
     * @param unavailableDate the unavailableDate to set
     */
    public void setUnavailableDate(Date unavailableDate) {
        this.unavailableDate = unavailableDate;
    }

    /**
     * @return the unavailableTimeFrom
     */
    public String getUnavailableTimeFrom() {
        return unavailableTimeFrom;
    }

    /**
     * @param unavailableTimeFrom the unavailableTimeFrom to set
     */
    public void setUnavailableTimeFrom(String unavailableTimeFrom) {
        this.unavailableTimeFrom = unavailableTimeFrom;
    }

    /**
     * @return the unavailableTimeTo
     */
    public String getUnavailableTimeTo() {
        return unavailableTimeTo;
    }

    /**
     * @param unavailableTimeTo the unavailableTimeTo to set
     */
    public void setUnavailableTimeTo(String unavailableTimeTo) {
        this.unavailableTimeTo = unavailableTimeTo;
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
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

}
