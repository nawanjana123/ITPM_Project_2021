/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.group;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nethmi Dasanayaka
 */
public interface groupDao {
    
    boolean addGroup(group group) throws SQLException;
    
    ResultSet getAllGroups() throws SQLException;
    
    ResultSet getGroupByOneAttribute(String attribute, String condition, String value) throws SQLException;
    
    boolean updateGroup(group group) throws SQLException;
    
    boolean deleteGroup(int groupId) throws SQLException;
    
}
