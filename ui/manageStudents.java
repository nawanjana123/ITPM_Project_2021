/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.ui;

import cazzendra.pos.control.CommonController;
import cazzendra.pos.core.CommonConstants;
import cazzendra.pos.core.HttpSMSClient;
import cazzendra.pos.core.Loading;
import cazzendra.pos.core.QR;
import cazzendra.pos.core.Validations;
import cazzendra.pos.ui.GenarateStudentIdCard;
import com.ttms.controller.commonController;
import com.ttms.controller.studentController;
import com.ttms.daoimpl.studentDaoImpl;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import org.json.simple.parser.ParseException;


public class manageStudents extends javax.swing.JFrame {
public static String stime;

    public manageStudents() {
        initComponents();
        loadStudentDataToTable();
        PanelMain.setBackground(Loading.getColorCode());
        PanelSub.setBackground(Loading.getColorCode());
        setNextIdAndQrCode();
         Date currentTime = new Date();
        String formatTimeStr = "dd MMM YYYY";
        DateFormat formatYR = new SimpleDateFormat(formatTimeStr);
         stime= formatYR.format(currentTime);
    }
    private void setNextIdAndQrCode() {
        try {
            int nextId = new studentDaoImpl().getNextStudentId();
            txtRegNo.setText("SW-"+Integer.toString(nextId));
            
        } catch (SQLException ex) {
            Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearAll() {
        txtContactNo.setText("");
        txtEmail1.setText("");
        txtRegNo.setText("");
        txtStudentName.setText("");
        txtCurrentAddress.setText("");
        txtGuardianContact.setText("");
        txtGuardianName.setText("");
        txtStudentFullName.setText("");
        calDob.setDate(null);
        
    }

    private void addStudent() throws SQLException, ParseException  {

        if (txtStudentName.getText().trim().equalsIgnoreCase(null) || txtStudentName.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please enter student Name !", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (calDob.getDate() == null || calDob.getDate().equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter student Date Of Birth !", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

//        if (txtRegNo.getText().trim().equalsIgnoreCase(null) || txtRegNo.getText().trim().equalsIgnoreCase("")) {
//            JOptionPane.showMessageDialog(this, "Please enter student Registration No !", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        if (txtContactNo.getText().trim().equalsIgnoreCase(null) || txtContactNo.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Please enter student Contact No !", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to save this student details ?");
            if (option == JOptionPane.YES_OPTION) {

                boolean status = studentController.addStudent(txtStudentName.getText().trim(), txtEmail1.getText().trim(),
                        txtGuardianName.getText(), txtRegNo.getText().trim(), txtContactNo.getText().trim(), txtStudentFullName.getText(), txtGuardianContact.getText().trim(),
                        comboGender.getSelectedItem().toString(), txtCurrentAddress.getText().trim(),
                        commonController.getMysqlDateFromJDateChooser(calDob),stime);

                if (status) {
                    String[] s=txtRegNo.getText().split("-");
                    String[] aa=txtGuardianName.getText().split(" ");
                    generateQRCode(s[1]);
                    if (HttpSMSClient.sendSms(3, txtGuardianContact.getText(), txtGuardianName.getText(),
                                comboGender.getSelectedItem().toString(), txtStudentName.getText(), "", txtRegNo.getText(), "2567", "", "", "")) {
                            System.out.println("SMS sent to parent success");
                        }
                    if (HttpSMSClient.sendSms(4, txtContactNo.getText(), txtGuardianName.getText(),
                                comboGender.getSelectedItem().toString(), txtStudentName.getText(), "", txtRegNo.getText(), "", "", "", "")) {
                            System.out.println("SMS sent to Student success");
                        }
                    JOptionPane.showMessageDialog(this, "Student registered successfully !");
                    clearAll();
                    loadStudentDataToTable();
                }
            }
        } 
    

    private void loadStudentDataToTable() {
        try {
            ResultSet rset = new studentDaoImpl().getAllStudents();
            /*
            student_id, student_name, student_email_1, student_email_2, student_reg_no, 
            student_contact_no, student_detail, student_status, student_batch_id, 
            student_group_id, student_special_id, student_address, student_contact_no_2, 
            student_image_path, student_guardian_name, student_guardian_contact_no, student_dob
            
            batch id = gender
            group id = guardian contact no
            special id = address 
            
             */
            String[] columnList = {"student_id", "student_reg_no", "student_name", 
                "student_batch_id", "student_contact_no", "student_special_id", "student_email_2",
                "student_group_id"};
            commonController.loadDataToTable(tblStudentDetails, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchStudentByMoreAttributes(String attribute) {
        try {

            ArrayList<String[]> attributeConditionValueList = new ArrayList<>();

            String[] ACV1 = {"student_id", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV1);

            String[] ACV2 = {"student_name", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV2);

            String[] ACV3 = {"student_dob", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV3);

            String[] ACV4 = {"student_batch_id", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV4);

            String[] ACV5 = {"student_contact_no", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV5);

            String[] ACV6 = {"student_special_id", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV6);

            String[] ACV7 = {"student_email_1", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV7);

            String[] ACV8 = {"student_group_id", CommonConstants.sql.LIKE, "%" + attribute + "%"};
            attributeConditionValueList.add(ACV8);

            ResultSet rset = new studentDaoImpl().getStudentsByMoreAttributes(attributeConditionValueList, CommonConstants.sql.OR);
            /*
            student_id, student_name, student_email_1, student_email_2, student_reg_no,
            student_contact_no, student_detail, student_status, student_batch_id,
            student_group_id, student_special_id, student_address, student_contact_no_2,
            student_image_path, student_guardian_name, student_guardian_contact_no, student_dob
            
            batch id = gender
            group id = guardian contact no
            special id = address 
            
             */
            String[] columnList = {"student_id", "student_reg_no", "student_name",
                "student_batch_id", "student_contact_no", "student_special_id", "student_email_2",
                "student_group_id"};
            commonController.loadDataToTable(tblStudentDetails, rset, columnList);
        } catch (SQLException ex) {
            Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editSelectedStudent() {
        int selectedRaw = tblStudentDetails.getSelectedRow();
        if (selectedRaw == -1) {
            JOptionPane.showMessageDialog(this, "Please select the row you want to update !", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel dtm = (DefaultTableModel) tblStudentDetails.getModel();
        int studentId = commonController.getIntOrZeroFromString(dtm.getValueAt(selectedRaw, 0).toString());
        new editStudent(this, true, studentId).setVisible(true);
    }
    
    private void deleteSelectedStudent() throws SQLException {
        int selectedRaw = tblStudentDetails.getSelectedRow();
        if (selectedRaw == -1) {
            JOptionPane.showMessageDialog(this, "Please select the row you want to Delete..!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int a = JOptionPane.showConfirmDialog(this, "Are you sure want to Delete?", "Warning", JOptionPane.WARNING_MESSAGE);
            if (a == JOptionPane.YES_OPTION) {
                DefaultTableModel dtm = (DefaultTableModel) tblStudentDetails.getModel();
                int studentId = commonController.getIntOrZeroFromString(dtm.getValueAt(selectedRaw, 0).toString());
                boolean status = studentController.deleteStudentDetails(studentId);
                if (status) {
                    JOptionPane.showMessageDialog(this, "Student Deleted successfully !");
                    this.dispose();
                }
            }
        }
    }
    
    private void viewSelectedGroup() {
        int selectedRaw = tblStudentDetails.getSelectedRow();
        if (selectedRaw == -1) {
            JOptionPane.showMessageDialog(this, "Please select the row you want to view !", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel dtm = (DefaultTableModel) tblStudentDetails.getModel();
        int studentId = commonController.getIntOrZeroFromString(dtm.getValueAt(selectedRaw, 0).toString());
        new viewStudent(this, true, studentId).setVisible(true);
    }

    private void viewStudentProfile() {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelMain = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudentDetails = new javax.swing.JTable();
        PanelSub = new javax.swing.JPanel();
        txtStudentName = new javax.swing.JTextField();
        btSave = new javax.swing.JButton();
        txtEmail1 = new javax.swing.JTextField();
        txtRegNo = new javax.swing.JTextField();
        txtContactNo = new javax.swing.JTextField();
        comboGender = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        calDob = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        txtGuardianContact = new javax.swing.JTextField();
        txtGuardianName = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtStudentFullName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCurrentAddress = new javax.swing.JTextArea();
        btnEdit = new javax.swing.JButton();
        btViewStudentDetails = new javax.swing.JButton();
        txtSearchAttribute = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btViewStudentDetails1 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Management");
        setMaximumSize(new java.awt.Dimension(1300, 700));
        setMinimumSize(new java.awt.Dimension(1300, 700));
        setResizable(false);

        PanelMain.setBackground(new java.awt.Color(0, 19, 52));
        PanelMain.setMaximumSize(new java.awt.Dimension(1269, 643));
        PanelMain.setMinimumSize(new java.awt.Dimension(1269, 643));
        PanelMain.setPreferredSize(new java.awt.Dimension(1269, 643));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblStudentDetails.setAutoCreateRowSorter(true);
        tblStudentDetails.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        tblStudentDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Reg. No", "Name", "", "Contact", "Address", "G. Name", "G.Contact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudentDetails.setAutoscrolls(false);
        tblStudentDetails.setRowHeight(20);
        tblStudentDetails.getTableHeader().setFont(new Font("Ubuntu", Font.BOLD, 18));
        tblStudentDetails.getTableHeader().setOpaque(false);
        tblStudentDetails.getTableHeader().setBackground(new Color(0, 0, 102));
        tblStudentDetails.getTableHeader().setForeground(new Color(255, 255, 255));
        tblStudentDetails.setRowMargin(2);
        tblStudentDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblStudentDetails);
        if (tblStudentDetails.getColumnModel().getColumnCount() > 0) {
            tblStudentDetails.getColumnModel().getColumn(0).setMinWidth(0);
            tblStudentDetails.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblStudentDetails.getColumnModel().getColumn(0).setMaxWidth(0);
            tblStudentDetails.getColumnModel().getColumn(1).setMinWidth(90);
            tblStudentDetails.getColumnModel().getColumn(1).setPreferredWidth(90);
            tblStudentDetails.getColumnModel().getColumn(1).setMaxWidth(90);
            tblStudentDetails.getColumnModel().getColumn(2).setResizable(false);
            tblStudentDetails.getColumnModel().getColumn(3).setMinWidth(70);
            tblStudentDetails.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblStudentDetails.getColumnModel().getColumn(3).setMaxWidth(70);
            tblStudentDetails.getColumnModel().getColumn(4).setMinWidth(100);
            tblStudentDetails.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblStudentDetails.getColumnModel().getColumn(4).setMaxWidth(100);
            tblStudentDetails.getColumnModel().getColumn(5).setPreferredWidth(185);
            tblStudentDetails.getColumnModel().getColumn(6).setMinWidth(150);
            tblStudentDetails.getColumnModel().getColumn(6).setPreferredWidth(150);
            tblStudentDetails.getColumnModel().getColumn(6).setMaxWidth(150);
            tblStudentDetails.getColumnModel().getColumn(7).setMinWidth(100);
            tblStudentDetails.getColumnModel().getColumn(7).setPreferredWidth(100);
            tblStudentDetails.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        PanelSub.setBackground(new java.awt.Color(0, 19, 52));

        txtStudentName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtStudentName.setToolTipText("Student Name");
        txtStudentName.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtStudentName.setSelectionColor(new java.awt.Color(255, 255, 0));

        btSave.setBackground(new java.awt.Color(1, 172, 241));
        btSave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btSave.setForeground(new java.awt.Color(255, 255, 255));
        btSave.setText("Save");
        btSave.setToolTipText("Add new student ");
        btSave.setBorder(null);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        txtEmail1.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtEmail1.setToolTipText("Email ");
        txtEmail1.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtEmail1.setSelectionColor(new java.awt.Color(255, 255, 0));

        txtRegNo.setEditable(false);
        txtRegNo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtRegNo.setToolTipText("Registration No");
        txtRegNo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtRegNo.setSelectionColor(new java.awt.Color(255, 255, 0));

        txtContactNo.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtContactNo.setToolTipText("Contact No");
        txtContactNo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtContactNo.setSelectionColor(new java.awt.Color(255, 255, 0));

        comboGender.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        comboGender.setToolTipText("Batch");

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Reg_No.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Name.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelicons3/email.png"))); // NOI18N
        jLabel3.setToolTipText("Email");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Contact_Num.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Locaion.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Batch.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Group.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Date Of Birth");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Guardian Contact");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Address");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Gender");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Contact No");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Registration No");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Email");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Student Name");

        calDob.setToolTipText("Week Begining Date");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Date.png"))); // NOI18N

        txtGuardianContact.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtGuardianContact.setToolTipText("Contact No");
        txtGuardianContact.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtGuardianContact.setSelectionColor(new java.awt.Color(255, 255, 0));

        txtGuardianName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtGuardianName.setToolTipText("Contact No");
        txtGuardianName.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtGuardianName.setSelectionColor(new java.awt.Color(255, 255, 0));

        jLabel19.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/Module_Code.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Guardian Name");

        txtStudentFullName.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtStudentFullName.setToolTipText("Student Name");
        txtStudentFullName.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtStudentFullName.setSelectionColor(new java.awt.Color(255, 255, 0));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Student Full Name");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/lableIcons/User_Title.png"))); // NOI18N

        txtCurrentAddress.setColumns(20);
        txtCurrentAddress.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtCurrentAddress.setRows(5);
        txtCurrentAddress.setToolTipText("");
        jScrollPane2.setViewportView(txtCurrentAddress);

        javax.swing.GroupLayout PanelSubLayout = new javax.swing.GroupLayout(PanelSub);
        PanelSub.setLayout(PanelSubLayout);
        PanelSubLayout.setHorizontalGroup(
            PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSubLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelSubLayout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanelSubLayout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelSubLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelSubLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGuardianName, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelSubLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSubLayout.createSequentialGroup()
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(PanelSubLayout.createSequentialGroup()
                                    .addGap(56, 56, 56)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSubLayout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(PanelSubLayout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboGender, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(PanelSubLayout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtGuardianContact, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(31, 31, 31)
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calDob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2)))
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanelSubLayout.createSequentialGroup()
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(txtStudentFullName)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))))
        );
        PanelSubLayout.setVerticalGroup(
            PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSubLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSubLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(calDob, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelSubLayout.createSequentialGroup()
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtStudentName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGuardianName, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelSubLayout.createSequentialGroup()
                                        .addComponent(comboGender, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGuardianContact, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtStudentFullName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(PanelSubLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelSubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(395, 395, 395))
        );

        btnEdit.setBackground(new java.awt.Color(1, 172, 241));
        btnEdit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Delete");
        btnEdit.setToolTipText("Edit Student Details");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btViewStudentDetails.setBackground(new java.awt.Color(1, 172, 241));
        btViewStudentDetails.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btViewStudentDetails.setForeground(new java.awt.Color(255, 255, 255));
        btViewStudentDetails.setText("Edit");
        btViewStudentDetails.setToolTipText("Edit");
        btViewStudentDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewStudentDetailsActionPerformed(evt);
            }
        });

        txtSearchAttribute.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtSearchAttribute.setToolTipText("Search by Student Name / Registration Number");
        txtSearchAttribute.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtSearchAttribute.setSelectionColor(new java.awt.Color(255, 255, 0));
        txtSearchAttribute.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchAttributeKeyReleased(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText(" Search");

        btViewStudentDetails1.setBackground(new java.awt.Color(1, 172, 241));
        btViewStudentDetails1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btViewStudentDetails1.setForeground(new java.awt.Color(255, 255, 255));
        btViewStudentDetails1.setText("Veiw Student Details");
        btViewStudentDetails1.setToolTipText("Veiw Student Details");
        btViewStudentDetails1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewStudentDetails1ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Student Details");
        jLabel22.setName("Student Details"); // NOI18N

        javax.swing.GroupLayout PanelMainLayout = new javax.swing.GroupLayout(PanelMain);
        PanelMain.setLayout(PanelMainLayout);
        PanelMainLayout.setHorizontalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMainLayout.createSequentialGroup()
                        .addComponent(PanelSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMainLayout.createSequentialGroup()
                        .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelMainLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearchAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btViewStudentDetails1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btViewStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)))
                        .addGap(244, 244, 244))))
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelMainLayout.setVerticalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelSub, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btViewStudentDetails1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btViewStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel22.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1259, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 559, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
    try {
        addStudent();
        setNextIdAndQrCode();
    } catch (SQLException ex) {
        Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParseException ex) {
        Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
    }

    }//GEN-LAST:event_btSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
    try {
        deleteSelectedStudent();
    } catch (SQLException ex) {
        Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
    }
        loadStudentDataToTable();
    }//GEN-LAST:event_btnEditActionPerformed

    private void txtSearchAttributeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAttributeKeyReleased
        searchStudentByMoreAttributes(txtSearchAttribute.getText().trim());
    }//GEN-LAST:event_txtSearchAttributeKeyReleased

    private void btViewStudentDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewStudentDetailsActionPerformed
        try {
            int selectedRow = tblStudentDetails.getSelectedRow();
            if (selectedRow != -1) {
                int studentId = Validations.getIntOrZeroFromString(tblStudentDetails.getValueAt(selectedRow, 0).toString());
                HashMap<String, Object> hm = new HashMap<>();
                hm.put("student_id", studentId);
                CommonController.printCommonReport("student_profile", hm);
            }
        } catch (SQLException | JRException ex) {
            Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btViewStudentDetailsActionPerformed

    private void btViewStudentDetails1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewStudentDetails1ActionPerformed
        int selectedRow = tblStudentDetails.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int studentId = Validations.getIntOrZeroFromString(tblStudentDetails.getValueAt(selectedRow, 0).toString());
                 System.out.println(studentId);   
                new GenarateStudentIdCard(studentId).setVisible(true);
//                GenarateStudentIdCard()
                
                
//                CommonController.printCommonReport("student_profile", hm);
            } catch (IOException ex) {
                Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btViewStudentDetails1ActionPerformed

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
            java.util.logging.Logger.getLogger(manageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manageStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new manageStudents().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelMain;
    private javax.swing.JPanel PanelSub;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btViewStudentDetails;
    private javax.swing.JButton btViewStudentDetails1;
    private javax.swing.JButton btnEdit;
    private com.toedter.calendar.JDateChooser calDob;
    private javax.swing.JComboBox<String> comboGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblStudentDetails;
    private javax.swing.JTextField txtContactNo;
    private javax.swing.JTextArea txtCurrentAddress;
    private javax.swing.JTextField txtEmail1;
    private javax.swing.JTextField txtGuardianContact;
    private javax.swing.JTextField txtGuardianName;
    private javax.swing.JTextField txtRegNo;
    private javax.swing.JTextField txtSearchAttribute;
    private javax.swing.JTextField txtStudentFullName;
    private javax.swing.JTextField txtStudentName;
    // End of variables declaration//GEN-END:variables

    
    private void generateQRCode(String nextId) {
try {
                QR.generateQRCode(nextId, 898, 898, ".tmp\\qr\\" + nextId + ".png");
            } catch (Exception ex) {
                Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private GenarateStudentIdCard GenarateStudentIdCard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
