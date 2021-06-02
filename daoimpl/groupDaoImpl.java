/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.groupDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Amal
 */
public class groupDaoImpl implements groupDao {

    private String selectQuery = "select group_id, group_name, group_batch_id, group_type, group_detail, group_status from group_info";

    @Override
    public boolean addGroup(group group) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into group_info (group_name, group_batch_id, group_type, group_detail, group_status) values (?,?,?,?,?)");
        ps.setString(1, group.getName());
        ps.setInt(2, group.getBatchId());
        ps.setString(3, group.getType());
        ps.setString(4, group.getDetail());
        ps.setInt(5, group.getStatus());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllGroups() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getGroupByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    public group getGroupsByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException {
        ResultSet rset = new commonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditionValueList, operator);
        group Group = null;
        while (rset.next()) {
            Group = new group();
            Group.setId(rset.getInt("group_id"));
            Group.setBatchId(rset.getInt("group_batch_id"));
            Group.setName(rset.getString("group_name"));
            Group.setType(rset.getString("group_type")); //courseId
            Group.setDetail(rset.getString("group_detail")); // level
            Group.setStatus(rset.getInt("group_status"));
        }
        return Group;
    }

    @Override
    public boolean updateGroup(group group) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update group_info set group_name=?, group_batch_id=?, group_type=?, group_detail=?, group_status=? where group_id=?");
        ps.setString(1, group.getName());
        ps.setInt(2, group.getBatchId());
        ps.setString(3, group.getType());
        ps.setString(4, group.getDetail());
        ps.setInt(5, group.getStatus());
        ps.setInt(6, group.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deleteGroup(int groupId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from group_info where group_id=?");
        ps.setInt(1, groupId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    public ResultSet getAllNormalGroups() throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select group_id, group_name, group_batch_id, group_type, "
                + "group_detail, group_status from group_info where group_type=1");
        return ps.executeQuery();
    }

    public ResultSet getAllSpecialGroups() throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select group_id, group_name, group_batch_id, group_type, "
                + "group_detail, group_status from group_info where group_type=2");
        return ps.executeQuery();
    }

}
