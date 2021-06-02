/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.courseDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class courseDaoImpl implements courseDao {

    private String selectQuery = "select course_id, course_name, course_type, course_detail, course_satus from course";

    @Override
    public boolean addCourse(course course) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into course (course_name, course_type, course_detail) values (?,?,?)");
        ps.setString(1, course.getName());
        ps.setString(2, course.getType());
        ps.setString(3, course.getDetail());
//        ps.setInt(4, course.getStatus());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllCourses() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getCourseByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public boolean updateCourse(course course) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update course set course_name=?, course_type=?, course_detail=?, course_satus=? where course_id=?");
        ps.setString(1, course.getName());
        ps.setString(2, course.getType());
        ps.setString(3, course.getDetail());
        ps.setInt(4, course.getStatus());
        ps.setInt(5, course.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deleteCourse(int courseId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from course where course_id=?");
        ps.setInt(1, courseId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

}
