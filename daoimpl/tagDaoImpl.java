/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.dao.tagDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public class tagDaoImpl implements tagDao {

    private String selectQuery = "select batch_id, batch_year, batch_level, batch_detail, batch_status from tag";

    @Override
    public boolean addBatch(tag batch) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into tag (batch_year, batch_level, batch_detail, batch_status) values (?,?,?,?)");
        ps.setString(1, batch.getYear());
        ps.setString(2, batch.getLevel());
        ps.setString(3, batch.getDetail());
        ps.setInt(4, batch.getStatus());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public ResultSet getAllBatches() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    @Override
    public ResultSet getBatchByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public boolean updateBatch(tag batch) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update tag set batch_year=?, batch_level=?, batch_detail=?, batch_status=? where batch_id=?");
        ps.setString(1, batch.getYear());
        ps.setString(2, batch.getLevel());
        ps.setString(3, batch.getDetail());
        ps.setInt(4, batch.getStatus());
        ps.setInt(5, batch.getId());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deleteBatch(int batchId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("delete from tag where batch_id=?");
        ps.setInt(1, batchId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

    public boolean updateBatchAsDeleted(int batchId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update tag set batch_status=? where batch_id=?");
        ps.setInt(1, tag.INACTIVE_BATCH);
        ps.setInt(2, batchId);
        ps.executeUpdate();
        ps.close();
        return true;
    }

}
