/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.deliveryPlanDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface deliveryPlanDetailsDao {

    boolean addDeliveryPlanDetailRecord(deliveryPlanDetails planDetails) throws SQLException;

    boolean updateDeliveryPlanDetails(deliveryPlanDetails planDetails) throws SQLException;

    boolean deleteDeliveryPlanDetails(int id) throws SQLException;

    ResultSet getAllDeliveryPlanDetails() throws SQLException;

    ResultSet getDeliveryPlanDetailsByOneAttribute(String attribute, String condition, String value) throws SQLException;

    ResultSet getDeliveryPlanDetailsByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException;
}
