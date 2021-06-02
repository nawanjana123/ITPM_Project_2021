/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.batch;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Amal
 */
public interface batchDao {

    boolean addBatch(batch batch) throws SQLException; 

    ResultSet getAllBatches() throws SQLException;

    ResultSet getBatchByOneAttribute(String attribute, String condition, String value) throws SQLException;

    boolean updateBatch(batch batch) throws SQLException;

    boolean deleteBatch(int batchId) throws SQLException;

}
