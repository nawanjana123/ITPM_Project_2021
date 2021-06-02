/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.lecturerAvailability;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface lecturerAvailabilityDao {

    boolean addLecturerAvailability(lecturerAvailability availability) throws SQLException;

    ResultSet getAllUnavailableLecturerRecords() throws SQLException;

    ResultSet getAvailableLectureByOneAttribute(String attribute, String condition, String value) throws SQLException;

    boolean updateAvailableLecture(lecturerAvailability availability) throws SQLException;

    boolean deleteLecturerAvailability(int lecturerAvailabilityId) throws SQLException;

}
