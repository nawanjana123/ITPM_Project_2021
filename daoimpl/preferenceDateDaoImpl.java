/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.preferenceDateDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.preferenceDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class preferenceDateDaoImpl implements preferenceDateDao {

    private String selectQuery = "select preference_date_id, preference_date_1, preference_date_2, preference_date_3, "
            + "preference_date_4, preference_date_5, preference_date_status, preference_date_remark, "
            + "preference_date_delivery_plan_id from preference_date";

    @Override
    public boolean addPreferenceDate(preferenceDate date) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into preference_date(preference_date_1, preference_date_2,"
                + " preference_date_3, preference_date_4, preference_date_5, preference_date_status, preference_date_remark, "
                + " preference_date_delivery_plan_id) values (?,?,?,?,?,?,?,?)");
        ps.setString(1, date.getDate1());
        ps.setString(2, date.getDate2());
        ps.setString(3, date.getDate3());
        ps.setString(4, date.getDate4());
        ps.setString(5, date.getDate5());
        ps.setInt(6, date.getStatus());
        ps.setString(7, date.getRemark());
        ps.setInt(8, date.getDeliveryPlanId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean updatePreferenceDate(preferenceDate date) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update preference_date set preference_date_1,"
                + " preference_date_2, preference_date_3, preference_date_4, preference_date_5, preference_date_status, "
                + " preference_date_remark, preference_date_delivery_plan_id where preference_date_id=?");
        ps.setString(1, date.getDate1());
        ps.setString(2, date.getDate2());
        ps.setString(3, date.getDate3());
        ps.setString(4, date.getDate4());
        ps.setString(5, date.getDate5());
        ps.setInt(6, date.getStatus());
        ps.setString(7, date.getRemark());
        ps.setInt(8, date.getDeliveryPlanId());
        ps.setInt(9, date.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deletePreferenceDate(int preferenceDateId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from preference_date where preference_date_id=?");
        ps.setInt(1, preferenceDateId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllPreferenceDates() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getPreferenceDateByAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public ResultSet getPreferenceDateByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException {
        return new commonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditionValueList, operator);
    }

}
