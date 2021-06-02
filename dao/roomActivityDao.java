/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.roomActivity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface roomActivityDao {

    boolean addRoomActivity(roomActivity activity) throws SQLException;

    boolean updateRoomActivity(roomActivity activity) throws SQLException;

    ResultSet getAllRoomActivities() throws SQLException;

    ResultSet getRoomActivityByOneAttribute(String attribute, String condition, String value) throws SQLException;

    boolean deleteRoomActivity(int roomActivityId) throws SQLException;  

}
