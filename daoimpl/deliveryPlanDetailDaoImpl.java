/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.daoimpl;

import com.ttms.controller.commonConstants;
import com.ttms.controller.deliveryPlanDetailsController;
import com.ttms.dao.deliveryPlanDetailsDao;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.deliveryPlanDetails;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author personal
 */
public class deliveryPlanDetailDaoImpl implements deliveryPlanDetailsDao {

    private String selectQuery = "select delivery_plan_details_id, delivery_plan_details_delivery_plan_id, "
            + "delivery_plan_details_date, delivery_plan_details_time, delivery_plan_details_time_order_no, "
            + "delivery_plan_details_status, delivery_plan_details_remark, delivery_plan_details_day, "
            + "delivery_plan_details_level, delivery_plan_details_module_name, delivery_plan_details_module_code, "
            + "delivery_plan_details_type, delivery_plan_details_lecturer_name, delivery_plan_details_room_name, "
            + "delivery_plan_details_course_name, delivery_plan_details_group_name, delivery_plan_details_start_time, "
            + "delivery_plan_details_lecture_duration, delivery_plan_details_end_time from delivery_plan_details";

    private String selectQuery2 = "select delivery_plan_details_id, delivery_plan_details_delivery_plan_id, "
            + "delivery_plan_details_date, delivery_plan_details_time, delivery_plan_details_time_order_no, "
            + "delivery_plan_details_status, delivery_plan_details_remark, delivery_plan_details_day, "
            + "delivery_plan_details_level, delivery_plan_details_module_name, delivery_plan_details_module_code, "
            + "delivery_plan_details_type, delivery_plan_details_lecturer_name, delivery_plan_details_room_name, "
            + "delivery_plan_details_course_name, delivery_plan_details_group_name, delivery_plan_details_start_time, "
            + "delivery_plan_details_lecture_duration, delivery_plan_details_end_time from "
            + "delivery_plan_details order by delivery_plan_details_date, delivery_plan_details_time_order_no";

    @Override
    public boolean addDeliveryPlanDetailRecord(deliveryPlanDetails planDetails) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("insert into delivery_plan_details ("
                + "delivery_plan_details_delivery_plan_id, delivery_plan_details_date, delivery_plan_details_time, "
                + "delivery_plan_details_time_order_no, delivery_plan_details_status, delivery_plan_details_remark, "
                + "delivery_plan_details_day, delivery_plan_details_level, delivery_plan_details_module_name, "
                + "delivery_plan_details_module_code, delivery_plan_details_type, delivery_plan_details_lecturer_name, "
                + "delivery_plan_details_room_name, delivery_plan_details_course_name, delivery_plan_details_group_name, "
                + "delivery_plan_details_start_time, delivery_plan_details_lecture_duration, delivery_plan_details_end_time) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, planDetails.getDeliveryPlanid());
        ps.setDate(2, planDetails.getDate());
        ps.setString(3, planDetails.getTimeString());
        ps.setInt(4, planDetails.getTimeOrder());
        ps.setInt(5, 1);
        ps.setString(6, planDetails.getRemark());
        ps.setString(7, planDetails.getDay());
        ps.setString(8, planDetails.getLevel());
        ps.setString(9, planDetails.getModueName());
        ps.setString(10, planDetails.getModuleCode());
        ps.setString(11, planDetails.getType());
        ps.setString(12, planDetails.getLecturerName());
        ps.setString(13, planDetails.getRoomName());
        ps.setString(14, planDetails.getCourseName());
        ps.setString(15, planDetails.getGroupName());
        ps.setTime(16, planDetails.getLectureStartTime());
        ps.setString(17, planDetails.getDuration());
        ps.setTime(18, planDetails.getLectureEndTime());
        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean updateDeliveryPlanDetails(deliveryPlanDetails planDetails) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update delivery_plan_details set delivery_plan_details_delivery_plan_id=?, "
                + "delivery_plan_details_date=?, delivery_plan_details_time=?, delivery_plan_details_time_order_no=?, "
                + "delivery_plan_details_status=?, delivery_plan_details_remark=?, delivery_plan_details_day=? where delivery_plan_details_id=?");

        ps.executeUpdate();
        ps.close();
        return true;
    }

    @Override
    public boolean deleteDeliveryPlanDetails(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet getAllDeliveryPlanDetails() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery);
    }

    public ResultSet getAllOrderedDeliveryPlanDetails() throws SQLException {
        return new commonDaoImpl().getAllRecords(selectQuery2);
    }

    @Override
    public ResultSet getDeliveryPlanDetailsByOneAttribute(String attribute, String condition, String value) throws SQLException {
        return new commonDaoImpl().getResultByAttribute(selectQuery, attribute, condition, value);
    }

    @Override
    public ResultSet getDeliveryPlanDetailsByMoreAttributes(ArrayList<String[]> attributeConditionValueList, String operator) throws SQLException {
        return new commonDaoImpl().getResultByAttributesWithJoinOperator(selectQuery, attributeConditionValueList, operator);
    }

    public int getNextTimeOrderNo(Date date) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("select count(delivery_plan_details_id) as "
                + "date_count from delivery_plan_details where delivery_plan_details_date=?");
        ResultSet rset = ps.executeQuery();
        int count = 0;
        while (rset.next()) {
            count = rset.getInt("date_count");
        }
        return ++count;
    }

    public ResultSet isRecordAvailableInDeliveryPlanDetailUiTable(Date SelectedDate,
            String LectureStartTime, String Level, String Location, String LecName) throws SQLException {

        ArrayList<String[]> AttributeConditionValueList = new ArrayList();

        String[] Av1 = {"delivery_plan_details_date", commonConstants.Sql.EQUAL, SelectedDate.toString()};
        AttributeConditionValueList.add(Av1);

        String[] Av2 = {"delivery_plan_details_start_time", commonConstants.Sql.EQUAL, LectureStartTime};
        AttributeConditionValueList.add(Av2);

        String[] Av3 = {"delivery_plan_details_level", commonConstants.Sql.EQUAL, Level};
        AttributeConditionValueList.add(Av3);

        String[] Av4 = {"delivery_plan_details_lecturer_name", commonConstants.Sql.EQUAL, LecName};
        AttributeConditionValueList.add(Av4);

        String[] Av5 = {"delivery_plan_details_room_name", commonConstants.Sql.EQUAL, Location};
        AttributeConditionValueList.add(Av5);

        return deliveryPlanDetailsController.getDeliveryPlanDetailsByMoreAttributes(AttributeConditionValueList, commonConstants.Sql.AND);
    }

    public boolean UpdateDeliveryPlanStartTimeAndDate(String StartTime, String EndTime, String Duration, int DeliveryPlanDetailId) throws SQLException {
        Connection con = DatabaseConnection.getDatabaseConnection();
        PreparedStatement ps = con.prepareStatement("update delivery_plan_details set delivery_plan_details_time=?, "
                + " delivery_plan_details_start_time=?, delivery_plan_details_lecture_duration=? , delivery_plan_details_end_time=? where delivery_plan_details_id=?");
        ps.setString(1, StartTime);
        ps.setString(2, StartTime);
        ps.setString(3, Duration);
        ps.setString(4, EndTime);
        ps.setInt(5, DeliveryPlanDetailId);
        ps.executeUpdate();
        ps.close();
        return true;
    }
}
