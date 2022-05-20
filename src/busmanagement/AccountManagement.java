/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package busmanagement;


import busmanagement.Account.Account;
import busmanagement.employee.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import views.PhanCong;
import views.QLChuyen;
import views.QLTime;
import views.QLVe;
import views.Thongke;

/**
 *
 * @author Admin
 */
public class AccountManagement extends javax.swing.JFrame {
    
    
    public static String infor;
    
      public static Connection establishCon()
    {
        
        try{
            String connectionUrl
            = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=BusManagement;"
            + "encrypt=true;trustServerCertificate=true";
            //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(connectionUrl, "sa", "viet1234");
            return con;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
      
      
      public ArrayList<Account> getAccountList(){
        ArrayList<Account> accs = new ArrayList<>();
        Connection con = establishCon();
        Statement st =null;
        try {
            
            String accountQueryAll = "SELECT * FROM ACCOUNT";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(accountQueryAll);
            while(rs.next())
            {
                Account acc = new Account(rs.getString("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("phone"), rs.getString("emp_id"));
                accs.add(acc);
               
            }
            
        }
         catch (Exception e){
             e.printStackTrace();
         }
        finally{
            if(st !=null)
               {
                   try{
                       st.close();
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
               }
               
              if(con !=null)
               {
                   try{
                       con.close();
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
               }
        }
        return accs;
    
    }
      public void displayEmpList()
    {
        ArrayList<Account> accs = getAccountList();
        DefaultTableModel model = (DefaultTableModel)accTable.getModel();
       
        Object [] row = new Object[10];
        for(int i=0; i<accs.size(); i++)
        {
            
            row[0] = accs.get(i).getUserId();
            row[1] = accs.get(i).getUsername();
          
            row[2] = accs.get(i).getPassword();
            row[3] = accs.get(i).getEmpId();
            row[4] = accs.get(i).getEmail();
            row[5] = accs.get(i).getPhoneNumber();
            
            model.addRow(row);
        }
    }
    /**
     * Creates new form AccountManagement
     */
    public ArrayList<String> getEmpId()
    {
         ArrayList<String> empIds = new ArrayList<>();
        Connection con = establishCon();
        Statement st =null;
        try {
            
            String empQueryAll = "SELECT * FROM EMPLOYEE";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(empQueryAll);
            while(rs.next())
            {
                String empId =rs.getString("emp_id");
                empIds.add(empId); // sua sau khong de moi id duoc
            }
            
        }
         catch (Exception e){
             e.printStackTrace();
         }
        finally{
            if(st !=null)
               {
                   try{
                       st.close();
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
               }
               
              if(con !=null)
               {
                   try{
                       con.close();
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
               }
              
    }
        return empIds;
    }
      
    public AccountManagement() {
        initComponents();
        displayEmpList();
        ArrayList<String> empsId = getEmpId();
        // cho empIds vao combobox;
        for(int i=0; i<empsId.size(); i++)
        {
            empIdCombo.addItem(empsId.get(i));
        }
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
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
        inforPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inforText = new javax.swing.JTextPane();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        empmButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        passwordText = new javax.swing.JTextField();
        emailText = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        phoneNumberText = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        accTable = new javax.swing.JTable();
        empIdCombo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(256, 720));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông tin người dùng");
        jLabel1.setToolTipText("");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        inforText.setEditable(false);
        inforText.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        inforText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inforText.setText("this is some random text");
        inforText.setAutoscrolls(false);
        inforText.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inforText.setEnabled(false);
        jScrollPane2.setViewportView(inforText);

        jButton6.setText("Phân công");
        jButton6.setFocusPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton4.setText("Quản lý chuyến xe");
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setText("Thống kê");
        jButton7.setFocusPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setText("Đăng xuất");
        jButton5.setFocusPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton10.setText("Quản lý tài khoản");
        jButton10.setFocusPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Quản lý vé");
        jButton11.setFocusPainted(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        empmButton.setText("Quản lý nhân viên");
        empmButton.setFocusPainted(false);
        empmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empmButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Quản lý xe bus");
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Quản lý tuyến xe");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inforPanelLayout = new javax.swing.GroupLayout(inforPanel);
        inforPanel.setLayout(inforPanelLayout);
        inforPanelLayout.setHorizontalGroup(
            inforPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inforPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
            .addGroup(inforPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
            .addGroup(inforPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inforPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(empmButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        inforPanelLayout.setVerticalGroup(
            inforPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inforPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(empmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(inforPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(inforPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(983, 720));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên tài khoản:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Mật khẩu:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Địa chỉ email:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Mã nhân viên:");

        usernameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextActionPerformed(evt);
            }
        });

        emailText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextActionPerformed(evt);
            }
        });

        addButton.setText("Thêm");
        addButton.setFocusPainted(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Xóa");
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Sửa");
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        phoneNumberText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberTextActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Số điện thoại:");

        jButton1.setText("Đặt lại");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        accTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tên tài khoản", "Mật khẩu", "Mã nhân viên", "Email", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        accTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(accTable);
        if (accTable.getColumnModel().getColumnCount() > 0) {
            accTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        empIdCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Mã nhân viên--" }));
        empIdCombo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        empIdCombo.setFocusable(false);
        empIdCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empIdComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(106, 106, 106)
                            .addComponent(phoneNumberText, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empIdCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(210, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneNumberText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empIdCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void empmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empmButtonActionPerformed
        // TODO add your handling code here:
            EmployeeManagement emp = new EmployeeManagement();
                emp.setVisible(true);
                emp.setLocationRelativeTo(null);
                //System.out.println(this.infor);
               emp.setInfor(this.infor);
              // System.out.println(bus.infor);
               this.dispose();
    }//GEN-LAST:event_empmButtonActionPerformed

    private void usernameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextActionPerformed

    private void emailTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        
        String username, password, email, phoneNumber, empId;
        
        username = usernameText.getText();
        password = passwordText.getText();
        email = emailText.getText();
        phoneNumber = phoneNumberText.getText();
        empId = empIdCombo.getSelectedItem().toString();
        Connection con = establishCon();
        PreparedStatement ps = null;
        ResultSet rs;
        if(username.equals("") || password.equals("") || email.equals("") || phoneNumber.equals("") || empId.equals("--Mã nhân viên--"))
        {
            JOptionPane.showMessageDialog(this, "Chưa nhập đủ thông tin");
        }
        else{
            if(phoneNumber.length() !=10)
            {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 chữ số");
                phoneNumberText.setText("");
            }
            else{
            try{
                String addStatement = "EXEC addAccount @username='" +username+"',@password= '"+password+"', @emp_id="+empId+", @email='"+email+"', @phone= '"+phoneNumber+"'";
                ps = con.prepareCall(addStatement);
               
               
                
             
                // tomorrow clear table and debugs
                
                rs = ps.executeQuery();
                rs.next();
                int id = rs.getInt("user_id");
                
                Object[] row = new Object[6];
                row[0] = id;
                row[1] = username;
                row[2] = password;
                row[3] = empId;
                row[4] = email;
                row[5] = phoneNumber;
                
                DefaultTableModel model = (DefaultTableModel)accTable.getModel();
                model.addRow(row);
                        accTable.getSelectionModel().clearSelection();
                usernameText.setText("");
                passwordText.setText("");
                emailText.setText("");
                empIdCombo.setSelectedItem("--Mã nhân viên--");
                phoneNumberText.setText("");
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally{
                if(ps != null)
               {
                   try{
                       ps.close();
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
               }
               
              if(con !=null)
               {
                   try{
                       con.close();
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
               }
            }
        }
        }

        

    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        int row = accTable.getSelectedRow();
        String cell = accTable.getModel().getValueAt(row, 0).toString();
        String deleteQuery = "DELETE FROM ACCOUNT WHERE emp_id= "+cell;
        Connection con = establishCon();
        Statement st =null;
        try{
            st = con.createStatement();
            st.executeUpdate(deleteQuery);
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            
            DefaultTableModel dm = (DefaultTableModel)accTable.getModel();
            dm.removeRow(row);
                    accTable.getSelectionModel().clearSelection();
        usernameText.setText("");
        passwordText.setText("");
        emailText.setText("");
        empIdCombo.setSelectedItem("--Mã nhân viên--");
        phoneNumberText.setText("");
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
         
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(con!=null)
            {
                try{
                    con.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(st != null)
            {
                try
                {
                    st.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_deleteButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        //ve implement phan sua
        int selectedRow = accTable.getSelectedRow();
        DefaultTableModel model =(DefaultTableModel) accTable.getModel();
        if(selectedRow >=0)
        {
            String username, password, email, phoneNumber;
            String empId;
            
            username = usernameText.getText();
            password = passwordText.getText();
            email = emailText.getText();
            phoneNumber = phoneNumberText.getText();
            empId = empIdCombo.getSelectedItem().toString();
            
            model.setValueAt(username, selectedRow, 1);
            model.setValueAt(password, selectedRow, 2);
            model.setValueAt(email, selectedRow, 4);
            model.setValueAt(empId, selectedRow, 3);
            model.setValueAt(phoneNumber, selectedRow, 5);
            
            Connection con = establishCon();
            Statement st;
            String userId = accTable.getModel().getValueAt(selectedRow, 0).toString();
             try{
                String updateSql = "UPDATE ACCOUNT "+
                        "SET username= '" +username+"', password= '"+password+"', emp_id= "+empId +", email = '"+email+"', phone='" +phoneNumber
                        + "' WHERE user_id =" +userId; 
                st = con.createStatement();
                st.executeUpdate(updateSql);
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                
                        accTable.getSelectionModel().clearSelection();
        usernameText.setText("");
        passwordText.setText("");
        emailText.setText("");
        empIdCombo.setSelectedItem("--Mã nhân viên--");
        phoneNumberText.setText("");
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
       
    }//GEN-LAST:event_updateButtonActionPerformed

    private void phoneNumberTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberTextActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        accTable.getSelectionModel().clearSelection();
        usernameText.setText("");
        passwordText.setText("");
        emailText.setText("");
        empIdCombo.setSelectedItem("--Mã nhân viên--");
        phoneNumberText.setText("");
        
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel3MouseClicked

    private void accTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accTableMouseClicked
        // TODO add your handling code here:
        int selectedRow = accTable.getSelectedRow();
        DefaultTableModel dfModel = (DefaultTableModel) accTable.getModel();
        usernameText.setText(dfModel.getValueAt(selectedRow, 1).toString());
        passwordText.setText(dfModel.getValueAt(selectedRow, 2).toString());
        empIdCombo.setSelectedItem(dfModel.getValueAt(selectedRow, 3).toString());
        emailText.setText(dfModel.getValueAt(selectedRow, 4).toString());
        phoneNumberText.setText(dfModel.getValueAt(selectedRow, 5).toString());
        deleteButton.setEnabled(true);
        updateButton.setEnabled(true);
    }//GEN-LAST:event_accTableMouseClicked

    private void empIdComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empIdComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_empIdComboActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
          BusManagement bus = new BusManagement();
                bus.setVisible(true);
                bus.setLocationRelativeTo(null);
                //System.out.println(this.infor);
               bus.setInfor(this.infor);
              // System.out.println(bus.infor);
               this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
   Login log = new Login();
                log.setVisible(true);
                log.setLocationRelativeTo(null);
                //System.out.println(this.infor);
               
              // System.out.println(bus.infor);
               this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        QLVe tick = new QLVe();
                tick.setVisible(true);
                tick.setLocationRelativeTo(null);
               // System.out.println(this.infor);
               tick.setInfor(this.infor);
               this.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        QLTime time = new QLTime();
                time.setVisible(true);
                time.setLocationRelativeTo(null);
               // System.out.println(this.infor);
               time.setInfor(this.infor);
               this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
         QLChuyen route = new QLChuyen();
                route.setVisible(true);
                route.setLocationRelativeTo(null);
               // System.out.println(this.infor);
               route.setInfor(this.infor);
               this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        PhanCong work = new PhanCong();
                work.setVisible(true);
                work.setLocationRelativeTo(null);
               // System.out.println(this.infor);
               work.setInfor(this.infor);
               this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Thongke st = new Thongke();
                st.setVisible(true);
                st.setLocationRelativeTo(null);
               // System.out.println(this.infor);
               st.setInfor(this.infor);
               this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AccountManagement().setVisible(true);
            }
        });
    }
   public void setInfor(String infor)
    {
        this.infor = infor;
        this.inforText.setText(infor);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable accTable;
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField emailText;
    private javax.swing.JComboBox<String> empIdCombo;
    private javax.swing.JButton empmButton;
    private javax.swing.JPanel inforPanel;
    private javax.swing.JTextPane inforText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField passwordText;
    private javax.swing.JTextField phoneNumberText;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
