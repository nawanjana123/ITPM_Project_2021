/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.holidayDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.holiday;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class holidayDaoImpl implements holidayDao {

    private String selectQuery = "select holiday_id, holiday_date_from, holiday_date_to, holiday_detail, holiday_status from holiday";

    @Override
    public boolean addHoliday(holiday holiday) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into holiday (holiday_date_from, holiday_date_to, "
                + "holiday_detail) values (?,?,?)");
        ps.setDate(1, holiday.getFromDate());
        ps.setDate(2, holiday.getToDate());
        ps.setString(3, holiday.getDetail());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean updateHoliday(holiday holiday) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update holiday set holiday_date_from=?, holiday_date_to=?, holiday_detail=? where holiday_id=?");
        ps.setDate(1, holiday.getFromDate());
        ps.setDate(2, holiday.getToDate());
        ps.setString(3, holiday.getDetail());
        ps.setInt(4, holiday.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllHolidays() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getHolidayByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public ResultSet getHolidayByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException {
        return new commonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditionValueList, operator);
    }

    @Override
    public boolean deleteHoliday(int holidayId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from holiday where holiday_id=?");
        ps.setInt(1, holidayId);
        ps.executeUpdate();
        ps.close();
        return true;
    }
}
