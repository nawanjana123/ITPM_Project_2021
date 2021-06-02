/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.course;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nethmi Dasanayaka
 */
public interface courseDao {
    
    boolean addCourse(course course) throws SQLException;
    
    ResultSet getAllCourses() throws SQLException;
   
    ResultSet getCourseByOneAttribute(String attribute, String condition, String value) throws SQLException;
   
    boolean updateCourse(course course) throws SQLException;
   
    boolean deleteCourse(int courseId) throws SQLException;
      
}
