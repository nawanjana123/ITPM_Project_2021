/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.lecturerAvailabilityDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.lecturerAvailability;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class lecturerAvailabilityDaoImpl implements lecturerAvailabilityDao {

    private String selectQuery = "select lecturer_availablity_id, lecturer_availablity_lec_id, lecturer_availablity_unavailable_date, "
            + " lecturer_availablity_unavailable_time_from, lecturer_availablity_unavailable_time_to, lecturer_availablity_status,"
            + " lecturer_availablity_detail from lecturer_availablity";

    private String selectQuery2 = "select lecturer_availablity_id, lecturer_availablity_lec_id, "
            + " lecturer_availablity_unavailable_date, lecturer_availablity_unavailable_time_from, "
            + " lecturer_availablity_unavailable_time_to, lecturer_availablity_status, lecturer_name, "
            + " lecturer_availablity_detail from lecturer_availablity left join lecturer on"
            + " lecturer_availablity_lec_id=lecturer_id";

    @Override
    public boolean addLecturerAvailability(lecturerAvailability availability) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into lecturer_availablity (lecturer_availablity_lec_id, lecturer_availablity_unavailable_date, "
                + " lecturer_availablity_unavailable_time_from, lecturer_availablity_unavailable_time_to, lecturer_availablity_status,"
                + " lecturer_availablity_detail) values (?,?,?,?,?,?)");
        ps.setInt(1, availability.getLecturerId());
        ps.setDate(2, availability.getUnavailableDate());
        ps.setString(3, availability.getUnavailableTimeFrom());
        ps.setString(4, availability.getUnavailableTimeTo());
        ps.setInt(5, availability.getStatus());
        ps.setString(6, availability.getDetail());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllUnavailableLecturerRecords() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getAvailableLectureByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public boolean updateAvailableLecture(lecturerAvailability availability) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update lecturer_availablity set lecturer_availablity_lec_id=?, "
                + " lecturer_availablity_unavailable_date=?, lecturer_availablity_unavailable_time_from=?, lecturer_availablity_unavailable_time_to=?, "
                + " lecturer_availablity_status=?, lecturer_availablity_detail=? where lecturer_availablity_id=?");
        ps.setInt(1, availability.getLecturerId());
        ps.setDate(2, availability.getUnavailableDate());
        ps.setString(3, availability.getUnavailableTimeFrom());
        ps.setString(4, availability.getUnavailableTimeTo());
        ps.setInt(5, availability.getStatus());
        ps.setString(6, availability.getDetail());
        ps.setInt(7, availability.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deleteLecturerAvailability(int lecturerAvailabilityId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from lecturer_availablity where lecturer_availablity_id=?");
        ps.setInt(1, lecturerAvailabilityId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    public ResultSet getLecUnavaDetailsWithJoinQuery() throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement(selectQuery2);
        return ps.executeQuery();
    }

    public ResultSet getAvailableLectureByOneAttributeWithJoinQuery(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery2, attribute, condition, value);
    }

}
