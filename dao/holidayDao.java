/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.holiday;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface holidayDao {

    public boolean addHoliday(holiday holiday) throws SQLException;

    public boolean updateHoliday(holiday holiday) throws SQLException;

    public ResultSet getAllHolidays() throws SQLException;

    public ResultSet getHolidayByOneAttribute(String attribute, String condition, String value) throws SQLException;

    public ResultSet getHolidayByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException;

    public boolean deleteHoliday(int holidayId) throws SQLException;

}
