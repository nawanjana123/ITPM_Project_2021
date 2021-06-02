/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.ui;

import com.ttms.controller.commonController;
import com.ttms.controller.deliveryPlanController;
import com.ttms.controller.lecturerController;
import com.ttms.controller.roomController;
import com.ttms.controller.subjectController;
import com.ttms.model.DataObject;
import com.ttms.model.lecturer;
import com.ttms.model.subject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Amal
 */
public class manageDeliveryPlan extends javax.swing.JFrame {

    subject subject = null;
    lecturer lecturer = null;
    int subjectId = 0;
    int lecturerId = 0;

    private String day1 = "";
    private String day2 = "";
    private String day3 = "";
    private String day4 = "";
    private String day5 = "";

    /**
     * Creates new form addStudent
     */
    public manageDeliveryPlan() {
        initComponents();
        loadRoomDataObjectsToCombo();
        setInitials();
        loadDataToTable();
    }

    private void loadRoomDataObjectsToCombo() {
        try {
            ResultSet rset = roomController.getAllRoomDetails();
            String[] columnList = {"room_id", "room_name", "room_code", "room_detail", "room_status"};
            commonController.loadDataObjectsIntoComboBox(comboLocation, rset, columnList, "room_name");
        } catch (SQLException ex) {
            Logger.getLogger(manageDeliveryPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPreferenceDateToTable() {
        boolean status = false;
        DefaultTableModel dtm = (DefaultTableModel) tblPreferenceDay.getModel();
        Object[] obj = {Integer.toString(comboPreferenceDay.getSelectedIndex()), comboPreferenceDay.getSelectedItem().toString()};
        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (commonController.getIntOrZeroFromString(tblPreferenceDay.getValueAt(i, 0).toString()) == comboPreferenceDay.getSelectedIndex()) {
                status = true;
                break;
            }
        }
        if (!status) {
            dtm.addRow(obj);
        } else {
            JOptionPane.showMessageDialog(this, "Selected day already in the table !", "Error !", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedPreferenceDate() {
        int selectedRaw = tblPreferenceDay.getSelectedRow();
        if (selectedRaw != -1) {
            DefaultTableModel dtm = (DefaultTableModel) tblPreferenceDay.getModel();
            dtm.removeRow(selectedRaw);
        } else {
            JOptionPane.showMessageDialog(this, "Select a day to remove from tble !", "Error !", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setInitials() {
        comboLevel.setSelectedItem(null);
        comboModuleCode.setSelectedItem(null);
        subjectId = 0;
        checkBoxRepeatStudents.setSelected(false);
        calWeekBeginningDate.setDate(null);
        comboCalenderWeek.setSelectedItem(null);
        calContactWeek.setSelectedItem(null);
        comboYear.setSelectedItem(null);
        comboLecturer.setSelectedItem(null);
        lecturerId = 0;
        comboHours.setSelectedItem(null);
        comboCalenderWeek.removeAllItems();
        comboLecturer.removeAllItems();
        comboModuleCode.removeAllItems();
    }

    private boolean preferenceDates() {
        boolean status = false;
        int rawCount = tblPreferenceDay.getRowCount();
        if (rawCount > 0) {
            for (int i = 0; i < tblPreferenceDay.getRowCount(); i++) {
                day1 = tblPreferenceDay.getValueAt(0, 1).toString();
                day2 = tblPreferenceDay.getValueAt(1, 1).toString();
                day3 = tblPreferenceDay.getValueAt(2, 1).toString();
                day4 = tblPreferenceDay.getValueAt(3, 1).toString();
                day5 = tblPreferenceDay.getValueAt(4, 1).toString();
            }
            status = true;
        } else {
            JOptionPane.showMessageDialog(this, "Please select preference dates !", "Error !", JOptionPane.ERROR_MESSAGE);
        }
        return status;
    }

    private void addDeliveryPlan() {
        try {
            if (comboLevel.getSelectedItem() == null || comboLevel.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select level !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboModuleCode.getSelectedItem() == null || comboModuleCode.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select module !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboCalenderWeek.getSelectedItem() == null || comboCalenderWeek.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select calender week !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (calWeekBeginningDate.getDate() == null || calWeekBeginningDate.getDate().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select week beginning date !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboLecturer.getSelectedItem() == null || comboLecturer.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select lecturer !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboHours.getSelectedItem() == null || comboHours.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select lecture hour !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboYear.getSelectedItem() == null || comboYear.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select year !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboType.getSelectedItem() == null || comboType.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select type !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (comboLocation.getSelectedItem() == null || comboLocation.getSelectedItem().toString().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Please select location !", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (preferenceDates()) {
                DataObject dataObj = (DataObject) comboLocation.getSelectedItem();
                deliveryPlanController.addDeliveryPlan(0, comboLevel.getSelectedItem().toString(), subjectId,
                        checkBoxRepeatStudents.isSelected(), commonController.getMysqlDateFromJDateChooser(calWeekBeginningDate),
                        comboCalenderWeek.getSelectedItem().toString(), calContactWeek.getSelectedItem().toString(),
                        commonController.getIntOrZeroFromString(comboYear.getSelectedItem().toString()), comboType.getSelectedItem().toString(),
                        lecturerId, commonController.getBigDecimalOrZeroFromString(comboHours.getSelectedItem().toString()),
                        commonController.getIntOrZeroFromString(dataObj.get("room_id")), txtRemark.getText().trim(), day1, day2, day3, day4, day5);

                int option = JOptionPane.showConfirmDialog(this, "Want to clear data ?", "Confirm", JOptionPane.INFORMATION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    clearData();
                    setInitials();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(manageDeliveryPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearData() {
        calContactWeek.setSelectedIndex(0);
        comboCalenderWeek.setSelectedItem(null);
        calWeekBeginningDate.setDate(null);
        txtRemark.setText(null);
        comboLevel.setSelectedIndex(0);
        comboModuleCode.setSelectedItem(null);
        comboModuleCode.removeAllItems();
        comboType.setSelectedIndex(0);
        comboLocation.setSelectedIndex(0);
        comboHours.setSelectedIndex(0);
        comboLecturer.setSelectedItem(null);
        comboLecturer.removeAllItems();
        comboYear.setSelectedIndex(0);
        checkBoxRepeatStudents.setSelected(false);
        DefaultTableModel dtm = (DefaultTableModel) tblPreferenceDay.getModel();
        dtm.setRowCount(0); 
    }

    private void setDateRelatedComponentData() {
        if (calWeekBeginningDate.getDate() != null) {
            String selectedDateString = new SimpleDateFormat("w").format(calWeekBeginningDate.getDate()).toString();
            String selectedYearSring = new SimpleDateFormat("y").format(calWeekBeginningDate.getDate()).toString();
            comboCalenderWeek.removeAllItems();
            comboYear.removeAllItems();
            comboCalenderWeek.addItem("CW " + selectedDateString);
            comboYear.addItem(selectedYearSring);
        }
    }

    private void loadDataToTable() {
        try {
            ResultSet rset = deliveryPlanController.getAllDeliveryPlansWithJoinTables();
            String[] columnList = {"delivery_plan_calender_week", "delivery_plan_class_contact_week", "delivery_plan_week_begining_date",
                "subject_name", "delivery_plan_remark", "delivery_plan_level_str", "subject_name", "delivery_plan_module_id",
                "delivery_plan_type", "delivery_plan_room_id", "room_name", "delivery_plan_lecture_hours", "lecturer_name",
                "delivery_plan_lecturer_id", "delivery_plan_year", "delivery_plan_repeat_students_available"};
            commonController.loadDataToTable(tblDeliveryReportData, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(manageDeliveryPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendarBeanInfo1 = new com.toedter.calendar.JCalendarBeanInfo();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeliveryReportData = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        comboLevel = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        comboModuleCode = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        txtModuleName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btSearchModule = new javax.swing.JButton();
        calContactWeek = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        calWeekBeginningDate = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        comboType = new javax.swing.JComboBox<>();
        btSearchLecturer = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        comboLecturer = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btAddDataToMainTble = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        comboLocation = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        comboHours = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        checkBoxRepeatStudents = new javax.swing.JCheckBox();
        comboYear = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPreferenceDay = new javax.swing.JTable();
        comboPreferenceDay = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btRemoveFromPrefTable = new javax.swing.JButton();
        btAddToPreferenceTable = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtRemark = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        comboCalenderWeek = new javax.swing.JComboBox<>();
        btPreviewFullDetails = new javax.swing.JButton();
        btRemoveDataFromMainTable = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Delivery Plan Management");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));

        tblDeliveryReportData.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        tblDeliveryReportData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Calender Week", "Class Cont. Week", "Week Beginning", "Lec / Tutorial / Lab", "Remarks", "hid_level", "hid_module code", "hid_module id", "hid_type", "hid_location_id", "hid_location", "hid_lecture hours", "hid_lecturer", "hid_lecturer id", "hid_year", "hid_repeat students"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDeliveryReportData.setRowHeight(20);
        tblDeliveryReportData.setRowMargin(2);
        tblDeliveryReportData.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDeliveryReportData);
        if (tblDeliveryReportData.getColumnModel().getColumnCount() > 0) {
            tblDeliveryReportData.getColumnModel().getColumn(0).setMinWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(0).setPreferredWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(0).setMaxWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(1).setMinWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(1).setMaxWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(2).setMinWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(2).setMaxWidth(150);
            tblDeliveryReportData.getColumnModel().getColumn(3).setResizable(false);
            tblDeliveryReportData.getColumnModel().getColumn(4).setResizable(false);
            tblDeliveryReportData.getColumnModel().getColumn(5).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(5).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(6).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(6).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(7).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(7).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(8).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(8).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(9).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(9).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(10).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(10).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(11).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(11).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(12).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(12).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(12).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(13).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(13).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(14).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(14).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(14).setMaxWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(15).setMinWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(15).setPreferredWidth(0);
            tblDeliveryReportData.getColumnModel().getColumn(15).setMaxWidth(0);
        }

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Level");

        comboLevel.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Level 4", "Level 5", "Level 6" }));
        comboLevel.setToolTipText("Level");
        comboLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLevelActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Module Code");

        comboModuleCode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboModuleCode.setToolTipText("Module Code");
        comboModuleCode.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Module Name");

        txtModuleName.setEditable(false);
        txtModuleName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtModuleName.setText("Module Name");
        txtModuleName.setToolTipText("Module Name");
        txtModuleName.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtModuleName.setSelectionColor(new java.awt.Color(255, 255, 0));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Course_Type.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Module_Code.png"))); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Level.png"))); // NOI18N

        btSearchModule.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/searchIcon.png"))); // NOI18N
        btSearchModule.setToolTipText("Search");
        btSearchModule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchModuleActionPerformed(evt);
            }
        });

        calContactWeek.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        calContactWeek.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Week 0", "Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6", "Week 7", "Week 8", "Week 9", "Week 10", "Week 11", "Week 12", "Week 13", "Week 14", "Week 15", "Week 16", "Week 17", "Week 18", "Week 19", "Week 20" }));
        calContactWeek.setToolTipText("Class Contact Week");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Date.png"))); // NOI18N

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Date.png"))); // NOI18N

        calWeekBeginningDate.setToolTipText("Week Begining Date");
        calWeekBeginningDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                calWeekBeginningDateFocusLost(evt);
            }
        });
        calWeekBeginningDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                calWeekBeginningDateMouseExited(evt);
            }
        });
        calWeekBeginningDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calWeekBeginningDatePropertyChange(evt);
            }
        });
        calWeekBeginningDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calWeekBeginningDateKeyReleased(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Week Beginning Date");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Class Contact Week");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Calender Week");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Date.png"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Lecturer");

        comboType.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lecture", "Tutorial", "Lab", "Holiday", "Examination", "Revision", "Induction Week", "ICA" }));
        comboType.setToolTipText("Type ");

        btSearchLecturer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/searchIcon.png"))); // NOI18N
        btSearchLecturer.setToolTipText("Search");
        btSearchLecturer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchLecturerActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Type.png"))); // NOI18N

        comboLecturer.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboLecturer.setToolTipText("Lecturer");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Lecturer.png"))); // NOI18N

        btAddDataToMainTble.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        btAddDataToMainTble.setForeground(new java.awt.Color(255, 255, 255));
        btAddDataToMainTble.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/addIncome.png"))); // NOI18N
        btAddDataToMainTble.setToolTipText("Add Delivery Plan");
        btAddDataToMainTble.setBorder(null);
        btAddDataToMainTble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddDataToMainTbleActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Type");

        comboLocation.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboLocation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "room" }));
        comboLocation.setToolTipText("Location");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Room.png"))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Location");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Hours.png"))); // NOI18N

        comboHours.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboHours.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00.30", "01.00", "01.30", "02.00", "02.30", "03.00", "03.30", "04.00", "04.30", "05.00" }));
        comboHours.setToolTipText("Lecture Hours");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Lecture Hours");

        checkBoxRepeatStudents.setBackground(new java.awt.Color(0, 0, 102));
        checkBoxRepeatStudents.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxRepeatStudents.setText("Repeat Students Available");
        checkBoxRepeatStudents.setToolTipText("Repeat Students Available");

        comboYear.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboYear.setToolTipText("Year");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Year.png"))); // NOI18N

        tblPreferenceDay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date ID", "Day"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPreferenceDay.setToolTipText("Priority Level");
        jScrollPane2.setViewportView(tblPreferenceDay);
        if (tblPreferenceDay.getColumnModel().getColumnCount() > 0) {
            tblPreferenceDay.getColumnModel().getColumn(0).setMinWidth(0);
            tblPreferenceDay.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblPreferenceDay.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        comboPreferenceDay.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboPreferenceDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" }));
        comboPreferenceDay.setToolTipText("Preference Date");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Priority");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Week.png"))); // NOI18N

        btRemoveFromPrefTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/deleteIcon.png"))); // NOI18N
        btRemoveFromPrefTable.setToolTipText("Delete Preference Date");
        btRemoveFromPrefTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveFromPrefTableActionPerformed(evt);
            }
        });

        btAddToPreferenceTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/addToTableSmall.png"))); // NOI18N
        btAddToPreferenceTable.setToolTipText("Add Preference Date");
        btAddToPreferenceTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddToPreferenceTableActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Preference");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Year");

        txtRemark.setToolTipText("Remarks");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Remark");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Remarks.png"))); // NOI18N

        comboCalenderWeek.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboCalenderWeek.setToolTipText("Calender Week");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboModuleCode, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSearchModule, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(comboLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtModuleName, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(checkBoxRepeatStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calContactWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboCalenderWeek, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                            .addComponent(calWeekBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(comboLecturer, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btSearchLecturer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboPreferenceDay, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btRemoveFromPrefTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btAddToPreferenceTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(comboLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(comboHours, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtRemark, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btAddDataToMainTble)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addComponent(jLabel20)
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboModuleCode, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btSearchModule, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtModuleName, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel30))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboPreferenceDay, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btAddToPreferenceTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btRemoveFromPrefTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(comboType, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel25)
                                                .addGap(5, 5, 5)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(comboLecturer, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btSearchLecturer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel24))
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(calWeekBeginningDate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel26)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(comboCalenderWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel28)
                                                .addGap(9, 9, 9)
                                                .addComponent(calContactWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel31)
                                .addGap(9, 9, 9)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(checkBoxRepeatStudents)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(comboLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btAddDataToMainTble)
                                    .addComponent(txtRemark, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(comboHours, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(363, 363, 363))
        );

        btPreviewFullDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/viewButton.png"))); // NOI18N
        btPreviewFullDetails.setToolTipText("Show Delivery Plan Details");
        btPreviewFullDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPreviewFullDetailsActionPerformed(evt);
            }
        });

        btRemoveDataFromMainTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/deleteIcon.png"))); // NOI18N
        btRemoveDataFromMainTable.setToolTipText("Delete Dilivery Plan Details");
        btRemoveDataFromMainTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveDataFromMainTableActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Delivery Plan for - ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btPreviewFullDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btRemoveDataFromMainTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btPreviewFullDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRemoveDataFromMainTable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 650));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddDataToMainTbleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddDataToMainTbleActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "All the details you entered are correct ? ", "Confirm !", JOptionPane.INFORMATION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            addDeliveryPlan();
            setDateRelatedComponentData();
            loadDataToTable();
        }
    }//GEN-LAST:event_btAddDataToMainTbleActionPerformed

    private void btSearchModuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchModuleActionPerformed
        try {
            searchSubject searchSub = new searchSubject(this, true);
            searchSub.setVisible(true);
            subjectId = searchSub.getSelectedSubjectId();
            if (subjectId != 0) {
                subject = subjectController.getSubjectBySubjectId(subjectId);
                comboModuleCode.removeAllItems();
                comboModuleCode.addItem(subject.getModuleCode());
                txtModuleName.setText(subject.getName());
            }
        } catch (SQLException ex) {
            Logger.getLogger(manageDeliveryPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSearchModuleActionPerformed

    private void btSearchLecturerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchLecturerActionPerformed
        searchLecturer searchLec = new searchLecturer(this, true);
        searchLec.setVisible(true);
        lecturerId = searchLec.getSelectedLecturerId();
        if (lecturerId != 0) {
            try {
                lecturer = lecturerController.getLecturerByLecturerId(lecturerId);
                comboLecturer.removeAllItems();
                comboLecturer.addItem(lecturer.getName());
            } catch (SQLException ex) {
                Logger.getLogger(manageDeliveryPlan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btSearchLecturerActionPerformed

    private void comboLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboLevelActionPerformed

    private void btRemoveFromPrefTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveFromPrefTableActionPerformed
        removeSelectedPreferenceDate();
    }//GEN-LAST:event_btRemoveFromPrefTableActionPerformed

    private void btAddToPreferenceTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddToPreferenceTableActionPerformed
        addPreferenceDateToTable();
    }//GEN-LAST:event_btAddToPreferenceTableActionPerformed

    private void btPreviewFullDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPreviewFullDetailsActionPerformed
        new viewDeliveryPlanTableInfo(this, true, 1).setVisible(true);
    }//GEN-LAST:event_btPreviewFullDetailsActionPerformed

    private void btRemoveDataFromMainTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveDataFromMainTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btRemoveDataFromMainTableActionPerformed

    private void calWeekBeginningDateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calWeekBeginningDateKeyReleased

    }//GEN-LAST:event_calWeekBeginningDateKeyReleased

    private void calWeekBeginningDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_calWeekBeginningDateFocusLost

    }//GEN-LAST:event_calWeekBeginningDateFocusLost

    private void calWeekBeginningDateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calWeekBeginningDateMouseExited

    }//GEN-LAST:event_calWeekBeginningDateMouseExited

    private void calWeekBeginningDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calWeekBeginningDatePropertyChange
        setDateRelatedComponentData();
    }//GEN-LAST:event_calWeekBeginningDatePropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(manageDeliveryPlan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manageDeliveryPlan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manageDeliveryPlan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manageDeliveryPlan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new manageDeliveryPlan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddDataToMainTble;
    private javax.swing.JButton btAddToPreferenceTable;
    private javax.swing.JButton btPreviewFullDetails;
    private javax.swing.JButton btRemoveDataFromMainTable;
    private javax.swing.JButton btRemoveFromPrefTable;
    private javax.swing.JButton btSearchLecturer;
    private javax.swing.JButton btSearchModule;
    private javax.swing.JComboBox<String> calContactWeek;
    private com.toedter.calendar.JDateChooser calWeekBeginningDate;
    private javax.swing.JCheckBox checkBoxRepeatStudents;
    private javax.swing.JComboBox<String> comboCalenderWeek;
    private javax.swing.JComboBox<String> comboHours;
    private javax.swing.JComboBox<String> comboLecturer;
    private javax.swing.JComboBox<String> comboLevel;
    private javax.swing.JComboBox<String> comboLocation;
    private javax.swing.JComboBox<String> comboModuleCode;
    private javax.swing.JComboBox<String> comboPreferenceDay;
    private javax.swing.JComboBox<String> comboType;
    private javax.swing.JComboBox<String> comboYear;
    private com.toedter.calendar.JCalendarBeanInfo jCalendarBeanInfo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDeliveryReportData;
    private javax.swing.JTable tblPreferenceDay;
    private javax.swing.JTextField txtModuleName;
    private javax.swing.JTextField txtRemark;
    // End of variables declaration//GEN-END:variables
}
