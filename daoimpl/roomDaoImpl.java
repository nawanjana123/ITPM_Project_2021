/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.roomDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class roomDaoImpl implements roomDao {

    private String selectQuery = "select room_id, room_name, room_code, room_detail, room_status from room";

    @Override
    public boolean addRoom(room room) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into room(room_name, room_code, room_detail, room_status) values (?,?,?,?)");
        ps.setString(1, room.getName());
        ps.setString(2, room.getCode());
        ps.setString(3, room.getDetail());
        ps.setInt(4, room.getStatus());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean updateRoom(room room) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update room set room_name=?, room_code=?, room_detail=?, room_status=? where room_id=?");
        ps.setString(1, room.getName());
        ps.setString(2, room.getCode());
        ps.setString(3, room.getDetail());
        ps.setInt(4, room.getStatus());
        ps.setInt(5, room.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllRooms() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getRoomByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public boolean deleteRoom(int roomId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from room where room_id=?");
        ps.setInt(1, roomId);
        ps.executeUpdate();
        ps.close();
        return true;
    }
}
