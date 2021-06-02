/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.preferenceDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface preferenceDateDao {
    
    boolean addPreferenceDate(preferenceDate date)throws SQLException;
    
    boolean updatePreferenceDate(preferenceDate date)throws SQLException;
    
    boolean deletePreferenceDate(int preferenceDateId)throws SQLException;
    
    ResultSet getAllPreferenceDates()throws SQLException;
    
    ResultSet getPreferenceDateByAttribute(String attribute, String condition, String value)throws SQLException;
    
    ResultSet getPreferenceDateByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator)throws SQLException;
    
}
