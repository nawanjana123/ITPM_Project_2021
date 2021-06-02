/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.roomActivityDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.roomActivity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class roomActivityDaoImpl implements roomActivityDao {

    private String selectQuery = "select room_activity_id, room_activity_room_id, room_activity_date, room_activity_time, "
            + "room_activity_lecturer_id, room_activity_detail, room_activity_status from room_activity";

    @Override
    public boolean addRoomActivity(roomActivity activity) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into room_activity(room_activity_room_id, room_activity_date, room_activity_time, "
                + "room_activity_lecturer_id, room_activity_detail, room_activity_status) values (?,?,?,?,?,?)");
        ps.setInt(1, activity.getRoomId());
        ps.setDate(2, activity.getDate());
        ps.setTime(3, activity.getTime());
        ps.setInt(4, activity.getLecturerId());
        ps.setString(5, activity.getDetail());
        ps.setInt(6, activity.getStatus());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean updateRoomActivity(roomActivity activity) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update room_activity set room_activity_room_id=?, room_activity_date=?, room_activity_time=?, "
                + "room_activity_lecturer_id=?, room_activity_detail=?, room_activity_status=? where room_activity_id=?");
        ps.setInt(1, activity.getRoomId());
        ps.setDate(2, activity.getDate());
        ps.setTime(3, activity.getTime());
        ps.setInt(4, activity.getLecturerId());
        ps.setString(5, activity.getDetail());
        ps.setInt(6, activity.getStatus());
        ps.setInt(7, activity.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllRoomActivities() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getRoomActivityByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public boolean deleteRoomActivity(int roomActivityId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from room_activity where room_activity_id=?");
        ps.setInt(1, roomActivityId);
        ps.executeUpdate();
        ps.close();
        return true;
    }
}
