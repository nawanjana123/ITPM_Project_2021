/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.tag;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface tagDao {

    boolean addBatch(tag batch) throws SQLException; 

    ResultSet getAllBatches() throws SQLException;

    ResultSet getBatchByOneAttribute(String attribute, String condition, String value) throws SQLException;

    boolean updateBatch(tag batch) throws SQLException;

    boolean deleteBatch(int batchId) throws SQLException;

}
