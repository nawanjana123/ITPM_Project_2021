/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ttms.ui;

import cazzendra.pos.core.CommonConstants;
import com.ttms.controller.commonController;
import com.ttms.controller.studentController;
import com.ttms.daoimpl.studentDaoImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nawa
 */
public class ViewStudentDetail extends javax.swing.JFrame {

    /**
     * Creates new form AddStudentGroup
     */
    public ViewStudentDetail() {
        initComponents();
        loadStudentDataToTable();
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudentDetails = new javax.swing.JTable();
        txtSearchAttribute = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btViewStudentDetails = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 19, 52));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Manage Student");

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
        tblStudentDetails.setRowMargin(2);
        tblStudentDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblStudentDetails);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btViewStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchAttribute, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(btViewStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchAttributeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAttributeKeyReleased
        searchStudentByMoreAttributes(txtSearchAttribute.getText().trim());
    }//GEN-LAST:event_txtSearchAttributeKeyReleased

    private void btViewStudentDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewStudentDetailsActionPerformed
        editSelectedStudent();
        loadStudentDataToTable();
    }//GEN-LAST:event_btViewStudentDetailsActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try {
            deleteSelectedStudent();
        } catch (SQLException ex) {
            Logger.getLogger(manageStudents.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadStudentDataToTable();
    }//GEN-LAST:event_btnEditActionPerformed

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
            java.util.logging.Logger.getLogger(ViewStudentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewStudentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewStudentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewStudentDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewStudentDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btViewStudentDetails;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblStudentDetails;
    private javax.swing.JTextField txtSearchAttribute;
    // End of variables declaration//GEN-END:variables
}
