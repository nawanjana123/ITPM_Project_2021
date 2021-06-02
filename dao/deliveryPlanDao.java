/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.deliveryPlan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface deliveryPlanDao {

    int addDeliveryPlan(deliveryPlan plan) throws SQLException;

    ResultSet getDeliveryPlanByOneAttribute(String attribute, String condition, String value) throws SQLException;

    boolean updateDeliveryPlan(deliveryPlan plan) throws SQLException;

    ResultSet getAllDeliveryPlans() throws SQLException;

    ResultSet getDeliveryPlanByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException;

    boolean deleteDeliveryPlan(int deliveryPlanId) throws SQLException;
    
    int getNextDeliveryPlanId() throws SQLException;

}
