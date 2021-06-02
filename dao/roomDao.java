/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.room;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface roomDao {

    boolean addRoom(room room) throws SQLException;

    boolean updateRoom(room room) throws SQLException;

    ResultSet getAllRooms() throws SQLException;

    ResultSet getRoomByOneAttribute(String attribute, String condition, String value) throws SQLException;

    boolean deleteRoom(int roomId) throws SQLException;

}
