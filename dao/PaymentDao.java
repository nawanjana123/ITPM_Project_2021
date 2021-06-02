/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.dao;

import com.ttms.model.Payment;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public interface PaymentDao {

    void AddPayment(Payment Payment) throws SQLException;

    ResultSet GetAllPayments() throws SQLException;

    ResultSet GetPaymentByAttribute(String Attribute, String Condition, String Value) throws SQLException;

    ResultSet GetPaymentByMoreAttributes(ArrayList<String[]> AttributeCOnditionValueList, String Operator) throws SQLException;

}
