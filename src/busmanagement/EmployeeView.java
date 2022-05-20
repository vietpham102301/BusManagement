/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package busmanagement;

import busmanagement.Account.Account;
import static busmanagement.AccountManagement.establishCon;
import busmanagement.employee.Employee;
import busmanagement.schedule.Schedule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class EmployeeView extends javax.swing.JFrame {
    
    public static int empId;
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
    public static String infor;
        

        
        public ArrayList<ArrayList<Schedule>> getScheduleOf(int week, int month, int year, int empId){
        
        ArrayList<ArrayList<Schedule>> scheWeek = new ArrayList<>();
        
        
        for(int i=1; i<=7;i++)
        {
            ArrayList<Schedule> scheDay = new ArrayList<>();
            Connection con = establishCon();
            Statement st =null;
             try {
            int month2= month+1;
            String scheduleQuery = "EXEC schedule "+month+", "+week+", "+i+", "+year+", "+empId;
            String cheduleQuery2= "EXEC schedule "+ month2+", "+week+", "+i+", "+year+", "+empId;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(scheduleQuery);
          
        
            while(rs.next())
            {
                Schedule sche = new Schedule(rs.getInt("bus_num"), rs.getString("name"), rs.getString("start_time"), rs.getString("end_time"), rs.getString("work_day"), rs.getString("license_plate"), rs.getString("start_point"));
                //System.out.println(sche.getName()+" "+ sche.getWorkDay());
                scheDay.add(sche);
            }
            
//            System.out.println(i);
//            System.out.println(cheduleQuery2);
            rs = st.executeQuery(cheduleQuery2);
            
            
           
        
            
            while(rs.next())
            {
                //System.out.println("run");
                Schedule sche = new Schedule(rs.getInt("bus_num"), rs.getString("name"), rs.getString("start_time"), rs.getString("end_time"), rs.getString("work_day"), rs.getString("license_plate"), rs.getString("start_point"));
                //System.out.println(sche.getName()+" "+ sche.getWorkDay());
               // System.out.println(sche.getWorkDay());
                scheDay.add(sche);
            }
            
            scheWeek.add(scheDay);
//            System.out.println(scheDay.size()==0);
//            System.out.println(scheWeek.size());
            
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
        }
       
        return scheWeek;
    
    }
    public void displaySchedule(int week, int month, int year, int empId )
    {
       ArrayList<ArrayList<Schedule>> scheWeek = getScheduleOf(week, month, year ,empId);
        DefaultTableModel model = (DefaultTableModel)scheduleTable.getModel();
//        System.out.println("run here");
            //System.out.println(scheWeek.get(4).size());
       
        
         int max = 0;
        
        for(int i=0; i<scheWeek.size(); i++)
        {
           if(scheWeek.get(i).size() > max)
           {
               max = scheWeek.get(i).size();
           }
        }
        
        for(int i=0; i< max; i++) 
        {
            Object [] row = new Object[7];
           //"<html>hello<br>hello</html>";
                if(i<scheWeek.get(0).size() && !(scheWeek.get(0).size() == 0))
                {
                    String st[] = scheWeek.get(0).get(i).getStartTime().split(":");
                    String timeFormat1 = st[0] +":"+ st[1];
                    String et[] = scheWeek.get(0).get(i).getEndTime().split(":");
                    String timeFormat2 = et[0]+":"+et[1];
                   // System.out.println(timeFormat1);
                   
                    row[0] = "<html>"+"xe bus số:" +scheWeek.get(0).get(i).getBusNumber() +"<br> biển số: "+scheWeek.get(0).get(i).getLicensePlate() +"<br>"+" ca: "+timeFormat1+" - "+timeFormat2+"<br> Điểm đi: "+scheWeek.get(0).get(i).getStartPoint()+"</html>";
                }
                else
                    row[0] = " ";
                if(i<scheWeek.get(1).size() && !(scheWeek.get(1).size() == 0) )
                {
                     String st[] = scheWeek.get(1).get(i).getStartTime().split(":");
                    String timeFormat1 = st[0] +":"+ st[1];
                    String et[] = scheWeek.get(1).get(i).getEndTime().split(":");
                    String timeFormat2 = et[0]+":"+et[1];
                    row[1] = "<html>"+"xe bus số:" +scheWeek.get(1).get(i).getBusNumber()+"<br> biển số: "+scheWeek.get(1).get(i).getLicensePlate()  +"<br>"+" ca: "+timeFormat1+" - "+timeFormat2+"<br> Điểm đi: "+scheWeek.get(1).get(i).getStartPoint()+"</html>";

                }
                else
                    row[1] = " ";
                if(i<scheWeek.get(2).size() && !(scheWeek.get(2).size() == 0))
                {
                    String st[] = scheWeek.get(2).get(i).getStartTime().split(":");
                    String timeFormat1 = st[0] +":"+ st[1];
                    String et[] = scheWeek.get(2).get(i).getEndTime().split(":");
                    String timeFormat2 = et[0]+":"+et[1];
                   row[2] = "<html>"+"xe bus số:" +scheWeek.get(2).get(i).getBusNumber()+"<br> biển số: "+scheWeek.get(2).get(i).getLicensePlate() +"<br>"+" ca: "+timeFormat1+" - "+timeFormat2+"<br> Điểm đi: "+scheWeek.get(2).get(i).getStartPoint()+"</html>";

                }
                else
                    row[2] = " ";
                if(i<scheWeek.get(3).size() && !(scheWeek.get(3).size() == 0))
                {
                     String st[] = scheWeek.get(3).get(i).getStartTime().split(":");
                    String timeFormat1 = st[0] +":"+ st[1];
                    String et[] = scheWeek.get(3).get(i).getEndTime().split(":");
                    String timeFormat2 = et[0]+":"+et[1];
                    row[3] = "<html>"+"xe bus số:" +scheWeek.get(3).get(i).getBusNumber()+"<br> biển số: "+scheWeek.get(3).get(i).getLicensePlate() +"<br>"+" ca: "+timeFormat1+" - "+timeFormat2+"<br> Điểm đi: "+scheWeek.get(3).get(i).getStartPoint()+"</html>";

                }
                else
                    row[3] = " ";
                if(i<scheWeek.get(4).size() && !(scheWeek.get(4).size() == 0))
                {
                     String st[] = scheWeek.get(4).get(i).getStartTime().split(":");
                    String timeFormat1 = st[0] +":"+ st[1];
                    String et[] = scheWeek.get(4).get(i).getEndTime().split(":");
                    String timeFormat2 = et[0]+":"+et[1];
                   row[4] = "<html>"+"xe bus số:" +scheWeek.get(4).get(i).getBusNumber()+"<br> biển số: "+scheWeek.get(4).get(i).getLicensePlate() +"<br>"+" ca: "+timeFormat1+" - "+timeFormat2+"<br> Điểm đi: "+scheWeek.get(4).get(i).getStartPoint()+"</html>";

                }
                else
                    row[4] = " ";
                if(i<scheWeek.get(5).size() && !(scheWeek.get(5).size() == 0))
                {
                     String st[] = scheWeek.get(5).get(i).getStartTime().split(":");
                    String timeFormat1 = st[0] +":"+ st[1];
                    String et[] = scheWeek.get(5).get(i).getEndTime().split(":");
                    String timeFormat2 = et[0]+":"+et[1];
                    row[5] = "<html>"+"xe bus số:" +scheWeek.get(5).get(i).getBusNumber()+"<br> biển số: "+scheWeek.get(5).get(i).getLicensePlate() +"<br>"+" ca: "+timeFormat1+" - "+timeFormat2+"<br> Điểm đi: "+scheWeek.get(5).get(i).getStartPoint()+"</html>";

                }
                else
                    row[5] = " ";
                if(i <scheWeek.get(6).size() && !(scheWeek.get(6).size() == 0))
                {
                    String st[] = scheWeek.get(6).get(i).getStartTime().split(":");
                    String timeFormat1 = st[0] +":"+ st[1];
                    String et[] = scheWeek.get(6).get(i).getEndTime().split(":");
                    String timeFormat2 = et[0]+":"+et[1];
                    row[6] = "<html>"+"xe bus số:" +scheWeek.get(6).get(i).getBusNumber()+"<br> biển số: "+scheWeek.get(6).get(i).getLicensePlate() +"<br>"+" ca: "+timeFormat1+" - "+timeFormat2+"<br> Điểm đi: "+scheWeek.get(6).get(i).getStartPoint()+"</html>";

                }
                else
                    row[6] = " ";
                
            
   
            model.addRow(row);
        }
    }

    /**
     * Creates new form EmployeeView
     */
     private static int getCurrentWeek() {
    LocalDate date = LocalDate.now();
    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    return date.get(weekFields.weekOfWeekBasedYear());
     }
    
    public EmployeeView(int empId) {
        
        initComponents();
        for(int i=1; i<=getCurrentWeek()+1; i++)
        {
            weekCombo.addItem(Integer.toString(i));
        }
        weekCombo.setSelectedItem(Integer.toString(getCurrentWeek()));
        for(int i=1; i<=12; i++)
        {
            monthCombo.addItem(Integer.toString(i));
        }
        LocalDate currentdate = LocalDate.now();
        
        int currentMonth = currentdate.getMonthValue();
        monthCombo.setSelectedItem(Integer.toString(currentMonth));
        
        yearCombo.addItem(Integer.toString(currentdate.getYear()));
        yearCombo.setSelectedIndex(1);
        //System.out.println(this.empId);
        displaySchedule(getCurrentWeek(), currentMonth, currentdate.getYear(), empId);
        
        this.empId = empId;
        //System.out.println(empId);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        scheduleTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inforText = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();
        yearCombo = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        monthCombo = new javax.swing.JComboBox<>();
        weekCombo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setPreferredSize(new java.awt.Dimension(983, 720));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        scheduleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thứ 2", "Thứ 3", "Thứ 4", " Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scheduleTable.setRowHeight(100);
        scheduleTable.getTableHeader().setReorderingAllowed(false);
        scheduleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scheduleTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(scheduleTable);

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

        jButton1.setText("Đăng xuất");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        yearCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Năm--" }));
        yearCombo.setFocusable(false);

        jButton2.setText("Tuần trước");
        jButton2.setFocusable(false);
        jButton2.setPreferredSize(new java.awt.Dimension(98, 22));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Tuần hiện tại");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Tuần kế");
        jButton4.setFocusable(false);
        jButton4.setPreferredSize(new java.awt.Dimension(98, 22));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        monthCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tháng--" }));
        monthCombo.setFocusable(false);
        monthCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                monthComboItemStateChanged(evt);
            }
        });

        weekCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Tuần--" }));
        weekCombo.setFocusable(false);
        weekCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                weekComboItemStateChanged(evt);
            }
        });
        weekCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekComboActionPerformed(evt);
            }
        });

        jLabel2.setText("Tháng:");

        jLabel3.setText("Tuần thứ:");

        jLabel4.setText("Năm:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(435, 435, 435)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(370, 370, 370)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jButton3)
                        .addGap(61, 61, 61)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(weekCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(monthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weekCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1143, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void scheduleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scheduleTableMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_scheduleTableMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void weekComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_weekComboItemStateChanged
        // TODO add your handling code here:
        //System.out.println(weekCombo.getSelectedItem().toString());
        if(weekCombo.getSelectedIndex() !=0 && monthCombo.getSelectedIndex() !=0 && yearCombo.getSelectedIndex() !=0)
        {
            DefaultTableModel dtm = (DefaultTableModel) scheduleTable.getModel();
                dtm.setRowCount(0);
                    displaySchedule(Integer.parseInt(weekCombo.getSelectedItem().toString()), Integer.parseInt(monthCombo.getSelectedItem().toString()), Integer.parseInt(yearCombo.getSelectedItem().toString()), this.empId) ;

        }
        
    }//GEN-LAST:event_weekComboItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
          Login log = new Login();
                log.setVisible(true);
                log.setLocationRelativeTo(null);
                //System.out.println(this.infor);
               
              // System.out.println(bus.infor);
               this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void weekComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weekComboActionPerformed

    private void monthComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_monthComboItemStateChanged
        // TODO add your handling code here:
           // System.out.println(weekCombo.getSelectedItem().toString());
        if(weekCombo.getSelectedIndex() !=0 && monthCombo.getSelectedIndex() !=0 && yearCombo.getSelectedIndex() !=0)
        {
            DefaultTableModel dtm = (DefaultTableModel) scheduleTable.getModel();
                dtm.setRowCount(0);
                    displaySchedule(Integer.parseInt(weekCombo.getSelectedItem().toString()), Integer.parseInt(monthCombo.getSelectedItem().toString()), Integer.parseInt(yearCombo.getSelectedItem().toString()), this.empId) ;

        }
        
    }//GEN-LAST:event_monthComboItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) scheduleTable.getModel();
        //dtm.setRowCount(0);
        //System.out.println("run");
        
        LocalDate now = LocalDate.now();
         
        weekCombo.setSelectedItem(Integer.toString(getCurrentWeek()));
        monthCombo.setSelectedItem(Integer.toString(now.getMonthValue())); // neu de setrowcount(0) o tren thi su kien indexchanged xay ra in ra cai table xong lai tiep tuc in ra table lan nua
        dtm.setRowCount(0);
        displaySchedule(getCurrentWeek(), now.getMonthValue(), now.getYear(), empId);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) scheduleTable.getModel();
                dtm.setRowCount(0);
                    displaySchedule(Integer.parseInt(weekCombo.getSelectedItem().toString())-1, Integer.parseInt(monthCombo.getSelectedItem().toString()), Integer.parseInt(yearCombo.getSelectedItem().toString()), this.empId) ;
                    weekCombo.setSelectedIndex(Integer.parseInt(weekCombo.getSelectedItem().toString())-1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
          DefaultTableModel dtm = (DefaultTableModel) scheduleTable.getModel();
                dtm.setRowCount(0);
        if(Integer.parseInt(weekCombo.getSelectedItem().toString())+1<=getCurrentWeek()+1)
        {
          
                    displaySchedule(Integer.parseInt(weekCombo.getSelectedItem().toString())+1, Integer.parseInt(monthCombo.getSelectedItem().toString()), Integer.parseInt(yearCombo.getSelectedItem().toString()), this.empId) ;
                    weekCombo.setSelectedIndex(Integer.parseInt(weekCombo.getSelectedItem().toString())+1);
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeView(8).setVisible(true);
            }
        });
    }
    
        public void setInfor(String infor)
    {
        this.infor = infor;
        this.inforText.setText(infor);
    }
      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane inforText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> monthCombo;
    private javax.swing.JTable scheduleTable;
    private javax.swing.JComboBox<String> weekCombo;
    private javax.swing.JComboBox<String> yearCombo;
    // End of variables declaration//GEN-END:variables
}
