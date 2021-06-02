/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author personal
 */
public class deliveryPlan {

    private int id;
    private String levelString;
    private int moduleId;
    private boolean repeatStudentsAvailable;
    private Date weekBeginingDate;
    private String calenderWeek;
    private String classContactWeek;
    private int year;
    private String type;
    private int lecturerId;
    private BigDecimal lectureHours;
    private int roomId;
    private String remark;
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "deliveryPlan{" + "id=" + id + ", levelString=" + levelString + ", moduleId=" + moduleId
                + ", repeatStudentsAvailable=" + repeatStudentsAvailable + ", weekBeginingDate=" + weekBeginingDate
                + ", calenderWeek=" + calenderWeek + ", classContactWeek=" + classContactWeek + ", year=" + year
                + ", type=" + type + ", lecturerId=" + lecturerId + ", lectureHours=" + lectureHours + ", roomId=" + roomId
                + ", remark=" + remark + ", day1=" + day1 + ", day2=" + day2 + ", day3=" + day3 + ", day4=" + day4
                + ", day5=" + day5 + '}';
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the moduleId
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return the repeatStudentsAvailable
     */
    public boolean isRepeatStudentsAvailable() {
        return repeatStudentsAvailable;
    }

    /**
     * @param repeatStudentsAvailable the repeatStudentsAvailable to set
     */
    public void setRepeatStudentsAvailable(boolean repeatStudentsAvailable) {
        this.repeatStudentsAvailable = repeatStudentsAvailable;
    }

    /**
     * @return the weekBeginingDate
     */
    public Date getWeekBeginingDate() {
        return weekBeginingDate;
    }

    /**
     * @param weekBeginingDate the weekBeginingDate to set
     */
    public void setWeekBeginingDate(Date weekBeginingDate) {
        this.weekBeginingDate = weekBeginingDate;
    }

    /**
     * @return the calenderWeek
     */
    public String getCalenderWeek() {
        return calenderWeek;
    }

    /**
     * @param calenderWeek the calenderWeek to set
     */
    public void setCalenderWeek(String calenderWeek) {
        this.calenderWeek = calenderWeek;
    }

    /**
     * @return the classContactWeek
     */
    public String getClassContactWeek() {
        return classContactWeek;
    }

    /**
     * @param classContactWeek the classContactWeek to set
     */
    public void setClassContactWeek(String classContactWeek) {
        this.classContactWeek = classContactWeek;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the lectureHours
     */
    public BigDecimal getLectureHours() {
        return lectureHours;
    }

    /**
     * @param lectureHours the lectureHours to set
     */
    public void setLectureHours(BigDecimal lectureHours) {
        this.lectureHours = lectureHours;
    }

    /**
     * @return the roomId
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @param roomId the roomId to set
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
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
     * @return the levelString
     */
    public String getLevelString() {
        return levelString;
    }

    /**
     * @param levelString the levelString to set
     */
    public void setLevelString(String levelString) {
        this.levelString = levelString;
    }

    /**
     * @return the day1
     */
    public String getDay1() {
        return day1;
    }

    /**
     * @param day1 the day1 to set
     */
    public void setDay1(String day1) {
        this.day1 = day1;
    }

    /**
     * @return the day2
     */
    public String getDay2() {
        return day2;
    }

    /**
     * @param day2 the day2 to set
     */
    public void setDay2(String day2) {
        this.day2 = day2;
    }

    /**
     * @return the day3
     */
    public String getDay3() {
        return day3;
    }

    /**
     * @param day3 the day3 to set
     */
    public void setDay3(String day3) {
        this.day3 = day3;
    }

    /**
     * @return the day4
     */
    public String getDay4() {
        return day4;
    }

    /**
     * @param day4 the day4 to set
     */
    public void setDay4(String day4) {
        this.day4 = day4;
    }

    /**
     * @return the day5
     */
    public String getDay5() {
        return day5;
    }

    /**
     * @param day5 the day5 to set
     */
    public void setDay5(String day5) {
        this.day5 = day5;
    }
}
