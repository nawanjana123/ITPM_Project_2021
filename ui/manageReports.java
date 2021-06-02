/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.ui;

import com.ttms.controller.commonController;
import com.ttms.controller.lecturerController;
import com.ttms.controller.roomController;
import com.ttms.controller.subjectController;
import com.ttms.daoimpl.commonDaoImpl;
import com.ttms.databaseConnection.DatabaseConnection;
import com.ttms.model.lecturer;
import com.ttms.model.subject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author personal
 */
public class manageReports extends javax.swing.JFrame  {

    int lecturerId = 0;
    int subjectId = 0;

    /**
     * Creates new form editBatch
     *
     */
    public manageReports() {
        initComponents();
        loadRoomDataObjectsToCombo();
    }

    // ok
    public void printMasterTimeTable() {
        try {
            HashMap<String, Object> hm = new HashMap<>();
            Connection con = DatabaseConnection.getDatabaseConnection();
            JasperDesign jsd = JRXmlLoader.load("reports\\time_table_2020.jrxml"); //src\\cazzendra\\pos\\
            JasperReport jr = JasperCompileManager.compileReport(jsd);
            hm.put("sem_starting_date", commonController.getMysqlDateFromJDateChooser(calSemBeginningDate));
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
//          JasperViewer jasperViewer = new JasperViewer(jp, false);
//          JasperPrintManager.printReport(jp, false);
            JasperViewer jasperViewer = new JasperViewer(jp, false);
            jasperViewer.setVisible(true);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //
    public void printLevelSchedule() {
        try {
            String Level = comboLevel.getSelectedItem().toString();
            HashMap<String, Object> hm = new HashMap<>();
            Connection con = DatabaseConnection.getDatabaseConnection();
            JasperDesign jsd = JRXmlLoader.load("reports\\level_wise_time_table.jrxml"); //src\\cazzendra\\pos\\
            JasperReport jr = JasperCompileManager.compileReport(jsd);
            hm.put("level", Level);
            hm.put("sem_starting_date", commonController.getMysqlDateFromJDateChooser(calSemBeginningDate));
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
//          JasperViewer jasperViewer = new JasperViewer(jp, false);
//          JasperPrintManager.printReport(jp, false);
            JasperViewer jasperViewer = new JasperViewer(jp, false);
            jasperViewer.setVisible(true);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printLecturerSchedule() {
        try {
            HashMap<String, Object> hm = new HashMap<>();
            Connection con = DatabaseConnection.getDatabaseConnection();
            JasperDesign jsd = JRXmlLoader.load("reports\\lecturer_wise_time_table.jrxml"); //src\\cazzendra\\pos\\
            JasperReport jr = JasperCompileManager.compileReport(jsd);
            hm.put("lecturer_name", comboLecturer.getSelectedItem().toString());
            hm.put("sem_starting_date", commonController.getMysqlDateFromJDateChooser(calSemBeginningDate));
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
//          JasperViewer jasperViewer = new JasperViewer(jp, false);
//          JasperPrintManager.printReport(jp, false);
            JasperViewer jasperViewer = new JasperViewer(jp, false);
            jasperViewer.setVisible(true);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadRoomDataObjectsToCombo() {
        try {
            ResultSet rset = roomController.getAllRoomDetails();
            String[] columnList = {"room_id", "room_name", "room_code", "room_detail", "room_status"};
            commonController.loadDataObjectsIntoComboBox(comboLocation, rset, columnList, "room_name");
        } catch (SQLException ex) {
            Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printRoomSchedule() {
        try {
            HashMap<String, Object> hm = new HashMap<>();
            Connection con = DatabaseConnection.getDatabaseConnection();
            JasperDesign jsd = JRXmlLoader.load("reports\\room_wise_time_table.jrxml"); //src\\cazzendra\\pos\\
            JasperReport jr = JasperCompileManager.compileReport(jsd);
            hm.put("room_name", comboLocation.getSelectedItem().toString());
            hm.put("sem_starting_date", commonController.getMysqlDateFromJDateChooser(calSemBeginningDate));
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
//          JasperViewer jasperViewer = new JasperViewer(jp, false);
//          JasperPrintManager.printReport(jp, false);
            JasperViewer jasperViewer = new JasperViewer(jp, false);
            jasperViewer.setVisible(true);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printModuleSchedule() {
        try {
            HashMap<String, Object> hm = new HashMap<>();
            Connection con = DatabaseConnection.getDatabaseConnection();
            JasperDesign jsd = JRXmlLoader.load("reports\\module_wise_time_table.jrxml"); //src\\cazzendra\\pos\\
            JasperReport jr = JasperCompileManager.compileReport(jsd);
            hm.put("sem_starting_date", commonController.getMysqlDateFromJDateChooser(calSemBeginningDate));
            hm.put("module_code", comboModuleCode.getSelectedItem().toString());
            hm.put("no_of_lectuers", new commonDaoImpl().GetLectureCountByDateAndCoduleCode(
                    commonController.getMysqlDateFromJDateChooser(calSemBeginningDate), comboModuleCode.getSelectedItem().toString()));
            hm.put("no_of_tutorials", new commonDaoImpl().GetTutorialCountByDateAndCoduleCode(
                    commonController.getMysqlDateFromJDateChooser(calSemBeginningDate), comboModuleCode.getSelectedItem().toString()));
            hm.put("no_of_labs", new commonDaoImpl().GetLabCountByDateAndCoduleCode(
                    commonController.getMysqlDateFromJDateChooser(calSemBeginningDate), comboModuleCode.getSelectedItem().toString()));
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, con);
//          JasperViewer jasperViewer = new JasperViewer(jp, false);
//          JasperPrintManager.printReport(jp, false);
            JasperViewer jasperViewer = new JasperViewer(jp, false);
            jasperViewer.setVisible(true);
        } catch (SQLException | JRException ex) {
            Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadLecturerdataObjectsToComboBox() {
        lecturer lecturer = null;
        searchLecturer searchLec = new searchLecturer(null, true);
        searchLec.setVisible(true);
        lecturerId = searchLec.getSelectedLecturerId();
        if (lecturerId != 0) {
            try {
                lecturer = lecturerController.getLecturerByLecturerId(lecturerId);
            } catch (SQLException ex) {
                Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
            }
            comboLecturer.removeAllItems();
            comboLecturer.addItem(lecturer.getName());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        calSemBeginningDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboLevel = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        comboLecturer = new javax.swing.JComboBox<>();
        btSearchLecturer = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        comboLocation = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboModuleCode = new javax.swing.JComboBox<>();
        btSearchModule = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Reports");

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jPanel4.setBackground(new java.awt.Color(0, 0, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Print Master Time Table");
        jButton1.setToolTipText("Print Master Time Table");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 220, 44));

        jButton2.setText("Print Level Schedule");
        jButton2.setToolTipText("Print Level Schedule");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 200, 44));

        jButton3.setText("Print Room Schedule");
        jButton3.setToolTipText("Print Room Schedule");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, 200, 44));

        jButton4.setText("Print Lecturer Schedule");
        jButton4.setToolTipText("Print Lecturer Schedule");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 200, 44));

        jButton5.setText("Print Module Schedule");
        jButton5.setToolTipText("Print Module Schedule");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 290, 200, 44));

        calSemBeginningDate.setToolTipText("Semester Strating Date");
        calSemBeginningDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                calSemBeginningDateFocusLost(evt);
            }
        });
        calSemBeginningDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                calSemBeginningDateMouseExited(evt);
            }
        });
        calSemBeginningDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calSemBeginningDatePropertyChange(evt);
            }
        });
        calSemBeginningDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calSemBeginningDateKeyReleased(evt);
            }
        });
        jPanel4.add(calSemBeginningDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 220, 44));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Master Time Table");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 220, -1));

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 11, 220));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Level");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 200, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Level Schedule");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 200, -1));

        comboLevel.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Level 4", "Level 5", "Level 6" }));
        comboLevel.setToolTipText("Level");
        comboLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLevelActionPerformed(evt);
            }
        });
        jPanel4.add(comboLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 200, 44));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Semester Starting Date");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 220, -1));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 11, 220));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Lecturer Schedule");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 200, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Lecturer Name");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 200, -1));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 11, 220));

        comboLecturer.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboLecturer.setToolTipText("Lecturer Name");
        jPanel4.add(comboLecturer, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 150, 44));

        btSearchLecturer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/searchIcon.png"))); // NOI18N
        btSearchLecturer.setToolTipText("Search Lecturer Name");
        btSearchLecturer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchLecturerActionPerformed(evt);
            }
        });
        jPanel4.add(btSearchLecturer, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 40, 40));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Room Schedule");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 200, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Room Name");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, 200, -1));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 140, 11, 220));

        comboLocation.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboLocation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "room" }));
        comboLocation.setToolTipText("Location Name");
        jPanel4.add(comboLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 220, 200, 44));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Module Schedule");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 150, 200, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Module Code");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 190, 200, -1));

        comboModuleCode.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        comboModuleCode.setToolTipText("Module Code");
        comboModuleCode.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.add(comboModuleCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 220, 150, 44));

        btSearchModule.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/ttms/labelIcons2/searchIcon.png"))); // NOI18N
        btSearchModule.setToolTipText("Search Module Code");
        btSearchModule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchModuleActionPerformed(evt);
            }
        });
        jPanel4.add(btSearchModule, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 220, 40, 40));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 960, 10));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 140, 11, 220));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1003, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        printMasterTimeTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void calSemBeginningDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_calSemBeginningDateFocusLost

    }//GEN-LAST:event_calSemBeginningDateFocusLost

    private void calSemBeginningDateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calSemBeginningDateMouseExited

    }//GEN-LAST:event_calSemBeginningDateMouseExited

    private void calSemBeginningDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calSemBeginningDatePropertyChange
    }//GEN-LAST:event_calSemBeginningDatePropertyChange

    private void calSemBeginningDateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calSemBeginningDateKeyReleased

    }//GEN-LAST:event_calSemBeginningDateKeyReleased

    private void comboLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboLevelActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        printLevelSchedule();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        printModuleSchedule();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btSearchLecturerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchLecturerActionPerformed
        loadLecturerdataObjectsToComboBox();
    }//GEN-LAST:event_btSearchLecturerActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        printLecturerSchedule();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        printRoomSchedule();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btSearchModuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchModuleActionPerformed
        subject subject = null;
        searchSubject searchSub = new searchSubject(null, true);
        searchSub.setVisible(true);
        subjectId = searchSub.getSelectedSubjectId();
        if (subjectId != 0) {
            try {
                subject = subjectController.getSubjectBySubjectId(subjectId);
            } catch (SQLException ex) {
                Logger.getLogger(manageReports.class.getName()).log(Level.SEVERE, null, ex);
            }
            comboModuleCode.removeAllItems();
            comboModuleCode.addItem(subject.getModuleCode());
        }
    }//GEN-LAST:event_btSearchModuleActionPerformed

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
            java.util.logging.Logger.getLogger(manageReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manageReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manageReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manageReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
      java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new manageReportsNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSearchLecturer;
    private javax.swing.JButton btSearchModule;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser calSemBeginningDate;
    private javax.swing.JComboBox<String> comboLecturer;
    private javax.swing.JComboBox<String> comboLevel;
    private javax.swing.JComboBox<String> comboLocation;
    private javax.swing.JComboBox<String> comboModuleCode;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    // End of variables declaration//GEN-END:variables
}
