/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.sesion;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nethmi Dasanayaka
 */
public interface sesionDao {
    
    boolean addSubject(sesion subject) throws SQLException;
    
    ResultSet getAllSubject() throws SQLException;
    
    ResultSet getSubjectByOneAttribute(String attribute, String condition, String value) throws SQLException;
    
    boolean updateSubject(sesion subject) throws SQLException;
    
    boolean deleteSubject(int subjectId) throws SQLException;  
}
