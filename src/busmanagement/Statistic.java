/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package busmanagement;

import static busmanagement.AccountManagement.establishCon;
import busmanagement.Bus.Bus;
import busmanagement.StatisticObject.Statistic01;
import busmanagement.StatisticObject.Statistic02;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import views.PhanCong;
import views.QLChuyen;
import views.QLTime;
import views.QLVe;

/**
 *
 * @author Admin
 */
public class Statistic extends javax.swing.JFrame {

    /**
     * Creates new form Statistic
     */
    
    public static String infor;
    
       public void setInfor(String infor)
    {
        this.infor = infor;
        this.inforText.setText(infor);
        
    }
       
       
       
       private boolean isDigits(String s)
    {
        if(s== null)
        {
            return false;
        }
        
        if(s.charAt(s.length()-1) == 'f')
        {
            return false;
        }
        
        try{
            Float.parseFloat(s);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
        
    }
       
       
    private float calEmpSalary(int option, int month, int year)
    {
        
        Connection con = establishCon();
        Statement st =null;
        String query = "";     
        float salary=0;
        try {
            
            
            if(option==0)
            {
                query = "SELECT salary FROM EMPLOYEE WHERE YEAR(start_working_date)< "+year+" or (YEAR(start_working_date) = "+year+ "and month(start_working_date)<= "+month+")";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    salary += rs.getFloat("salary");                  
                }
                
            }
            else if(option==1)
            {
                query = "SELECT salary FROM EMPLOYEE WHERE YEAR(start_working_date) = "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                LocalDate now = LocalDate.now();
                
                if(year < now.getYear())
                {
                    while(rs.next())
                    {
                        salary += rs.getFloat("salary")*12;
                    }
                   
                }
                else if(year == now.getYear())
                {
                    while(rs.next())
                    {
                        salary += rs.getFloat("salary")*now.getMonthValue();
                    }
                    
                }
            }else if(option==2)
            {
                LocalDate now = LocalDate.now();
                query = "SELECT salary FROM EMPLOYEE WHERE YEAR(start_working_date) < "+now.getYear();
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    salary += rs.getFloat("salary")*12;
                }
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return salary;
    }
    
    
    private float calOpCost(int option, int month, int year)
    {
        Connection con = establishCon();
        Statement st =null;
        String query = "";     
        float opCost=0;
        try {
            
            
            if(option==0)
            {
                query = "SELECT gas_cost, fix_cost FROM OPERATION_COST WHERE MONTH(work_day)= "+month+" and YEAR(work_day)= "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    opCost += rs.getFloat("gas_cost") +rs.getFloat("fix_cost");                  
                }
                
            }
            else if(option==1)
            {
                query = "SELECT gas_cost, fix_cost FROM OPERATION_COST WHERE YEAR(work_day)= "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                LocalDate now = LocalDate.now();
                
               while(rs.next())
                {
                    opCost += rs.getFloat("gas_cost") +rs.getFloat("fix_cost");                  
                }
            }else if(option==2)
            {
                LocalDate now = LocalDate.now();
                query = "SELECT gas_cost, fix_cost FROM OPERATION_COST WHERE YEAR(work_day)< "+now.getYear();
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    opCost += rs.getFloat("gas_cost") + rs.getFloat("fix_cost");
                }
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return opCost;
    }
    
    
    private float calTurnover(int option, int month, int year)
    {
        Connection con = establishCon();
        Statement st =null;
        String query = "";     
        float turnover=0;
        try {
            
            
            if(option==0)
            {
                query = "exec turnoverByDay "+month+", "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    turnover += rs.getFloat("ticket_sold")*rs.getFloat("fare");                  
                }
                
            }
            else if(option==1)
            {
                query = "exec turnoverByMonth "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                //LocalDate now = LocalDate.now();
                
               while(rs.next())
                {
                    turnover += rs.getFloat("total_ticket_sold")*rs.getFloat("fare");                  
                }
            }else if(option==2)
            {
                //LocalDate now = LocalDate.now();
                query = "exec turnoverByYear";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                //LocalDate now = LocalDate.now();
                
               while(rs.next())
                {
                    turnover += rs.getFloat("total_ticket_sold")*rs.getFloat("fare");                  
                }
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return turnover;
    }
    
    private float calNetIncome(int option, int month, int year)
    {
     
            return calTurnover(option, month, year) - calEmpSalary(option, month, year) - calOpCost(option, month, year);
       
    }
    
    public Statistic() {
        initComponents();
        LocalDate now = LocalDate.now();
        displayST01(0, now.getMonthValue(), now.getYear() );
        displayST02(0, now.getMonthValue(), now.getYear() );
        ArrayList<String> buses = getBuses();
        ArrayList<String> tickets = getTickets();
        
        for(int i=0; i<buses.size(); i++){
            busCombo.addItem(buses.get(i));
        }
        
         for(int i=0; i<tickets.size(); i++){
            ticketCombo.addItem(tickets.get(i));
        }
         
        // deleteButton.setEnabled(false);
         updateButton.setEnabled(false);
         
         //deleteButton1.setEnabled(false);
         updateButton1.setEnabled(false);
         
         addButton1.setEnabled(false);
         
         
         
         empSalText.setText(Float.toString(calEmpSalary(0, now.getMonthValue(), now.getYear()))+ " vnd");
         opCostText.setText(Float.toString(calOpCost(0, now.getMonthValue(), now.getYear()))+" vnd");
         turnoverText.setText(Float.toString(calTurnover(0, now.getMonthValue(), now.getYear())) + " vnd");
         profitText.setText(Float.toString(calNetIncome(0, now.getMonthValue(), now.getYear())) +" vnd");
         
         stMonthCombo.setSelectedIndex(now.getMonthValue());
         stYearText.setText(Integer.toString(now.getYear()));
        
    }
    
    
     public ArrayList<String> getBuses()
    {
         ArrayList<String> buses = new ArrayList<>();
        Connection con = establishCon();
        Statement st =null;
        try {
            
            String query = "SELECT * FROM BUS";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                String bus =rs.getString("bus_id") +" - " +rs.getString("license_plate")+ " - Tuyến số:"+rs.getString("route_id");
                buses.add(bus); // sua sau khong de moi id duoc done
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
        return buses;
    }
     
     
     
     
     
     public ArrayList<String> getTickets()
    {
         ArrayList<String> tickets = new ArrayList<>();
        Connection con = establishCon();
        Statement st =null;
        try {
            
            String query = "SELECT * FROM TICKET";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                String tick =rs.getString("ticket_id") +" - " +rs.getString("ticket_type")+ " - Giá tiền: "+rs.getString("fare");
                tickets.add(tick); // sua sau khong de moi id duoc done
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
        return tickets;
    }
    
    
    
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
    
    
     public ArrayList<Statistic01> getST01List(int option, int month, int year){
        ArrayList<Statistic01> st01s = new ArrayList<>();
        Connection con = establishCon();
        Statement st =null;
        try {
            
            String query = "";
            if(option==0)
            {
                query = "exec turnoverByDay "+month+", "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Statistic01 st01 = new Statistic01(rs.getString("license_plate"), rs.getString("ticket_type"), rs.getInt("ticket_sold"), rs.getString("work_day"));
                    
                    st01s.add(st01);

                }
                
            }
            else if(option==1)
            {
                query = "exec turnoverByMonth "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Statistic01 st01 = new Statistic01(rs.getString("license_plate"), rs.getString("ticket_type"), rs.getInt("total_ticket_sold"), rs.getString("work_day"));
                    st01s.add(st01);

                }
            }
            else if(option==2)
            {
                query = "exec turnoverByYear";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Statistic01 st01 = new Statistic01(rs.getString("license_plate"), rs.getString("ticket_type"), rs.getInt("total_ticket_sold"), rs.getString("work_year"));
                    st01s.add(st01);

                }
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
        return st01s;
    
    }
     
     public ArrayList<Statistic02> getST02List(int option, int month, int year){
        ArrayList<Statistic02> st02s = new ArrayList<>();
        Connection con = establishCon();
        Statement st =null;
        try {
            
            String query = "";
            if(option==0)
            {
                query = "exec opCostByDay "+month+", "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Statistic02 st02 = new Statistic02(rs.getString("license_plate"), rs.getFloat("gas_cost"), rs.getFloat("fix_cost"), rs.getString("work_day"));
                    st02s.add(st02);

                }
                
            }
            else if(option==1)
            {
                query = "exec opCostByMonth "+year;
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Statistic02 st02 = new Statistic02(rs.getString("license_plate"), rs.getFloat("total_gas_cost"), rs.getFloat("total_fix_cost"), rs.getString("work_day"));
                    st02s.add(st02);

                }
            }
            else if(option==2)
            {
                query = "exec opCostByYear";
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next())
                {
                    Statistic02 st02 = new Statistic02(rs.getString("license_plate"), rs.getFloat("total_gas_cost"), rs.getFloat("total_fix_cost"), rs.getString("work_year"));
                    st02s.add(st02);

                }
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
        return st02s;
    
    }
     
          public void displayST01(int option, int month, int year)
    {
        ArrayList<Statistic01> st01s = getST01List(option, month, year);
        DefaultTableModel model = (DefaultTableModel)stTable01.getModel();
       
        Object [] rows = new Object[4];
        for(int i=0; i<st01s.size(); i++)
        {
            
            rows[0] = st01s.get(i).getLicensePlate();
            rows[1] = st01s.get(i).getTicketType();
          
            rows[2] = st01s.get(i).getTicketSold();
            rows[3] = st01s.get(i).getWorkDay();
           
            
            model.addRow(rows);
        }
    }
          
               public void displayST02(int option, int month, int year)
    {
        ArrayList<Statistic02> st02s = getST02List(option, month, year);
        DefaultTableModel model = (DefaultTableModel)stTable02.getModel();
       
        Object [] rows = new Object[4];
        for(int i=0; i<st02s.size(); i++)
        {
            
            rows[0] = st02s.get(i).getLicensePlate();
            rows[1] = st02s.get(i).getFix_cost();
          
            rows[2] = st02s.get(i).getGas_cost();
            rows[3] = st02s.get(i).getWork_day();
           
            
            model.addRow(rows);
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

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        ResetButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stTable02 = new javax.swing.JTable();
        stMonthCombo = new javax.swing.JComboBox<>();
        stYearText = new javax.swing.JTextField();
        stDayText = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        stTable01 = new javax.swing.JTable();
        groupByCombo = new javax.swing.JComboBox<>();
        busCombo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        addButton1 = new javax.swing.JButton();
        updateButton1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        ticketCombo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ticketSoldText = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        fixCostText = new javax.swing.JTextField();
        opCostText = new javax.swing.JTextField();
        empSalText = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        turnoverText = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        profitText = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        gasCostCombo = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setPreferredSize(new java.awt.Dimension(983, 720));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Thống kê theo:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Ngày (dd/mm/yyyy):");

        addButton.setText("Thêm");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Sửa");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        ResetButton.setText("Đặt lại");
        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });

        stTable02.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Xe bus", "Chi phí xăng", "Chi phí  sửa chữa", "Thời gian"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stTable02.getTableHeader().setReorderingAllowed(false);
        stTable02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stTable02MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(stTable02);

        stMonthCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn tháng", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Thống kê");

        stTable01.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Xe bus", "Loại vé", "Số vé bán", "Thời gian"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stTable01.getTableHeader().setReorderingAllowed(false);
        stTable01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stTable01MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(stTable01);

        groupByCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ngày", "Tháng", "Năm" }));
        groupByCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                groupByComboItemStateChanged(evt);
            }
        });

        busCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chọn xe bus--" }));
        busCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                busComboItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Xe bus:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Doanh thu vé");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Chi phí vận hành");

        addButton1.setText("Thêm");
        addButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButton1ActionPerformed(evt);
            }
        });

        updateButton1.setText("Sửa");
        updateButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("Xem thống kê");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ticketCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chọn loại vé--" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Loại vé:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Số lượng vé bán:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Chi phí xăng(vnd):");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Chi phí sửa chữa:");

        opCostText.setEditable(false);

        empSalText.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Tổng chi phí vận hành:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Lương nhân viên:");

        turnoverText.setEditable(false);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Doanh thu:");

        profitText.setEditable(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Lợi nhuận:");

        gasCostCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chi phí xăng--" }));

        jButton8.setText("Kiểm tra");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(stDayText, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(stMonthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(stYearText, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(busCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ticketCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ticketSoldText, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(groupByCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(281, 281, 281)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(53, 53, 53)
                                        .addComponent(jLabel17))
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(237, 237, 237)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(141, 141, 141)
                                .addComponent(jButton8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(358, 358, 358))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ResetButton)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(135, 135, 135))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel10))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(gasCostCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                                .addComponent(fixCostText, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(42, 42, 42)
                                        .addComponent(addButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(updateButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(233, 233, 233))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(112, 112, 112)
                                                .addComponent(jLabel12))
                                            .addComponent(turnoverText, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(182, 182, 182)
                                                .addComponent(empSalText, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(profitText, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(47, 47, 47)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(opCostText, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(groupByCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stDayText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(stMonthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stYearText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fixCostText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(gasCostCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel13)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ticketSoldText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(busCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ticketCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(turnoverText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(empSalText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(opCostText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profitText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

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
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton4.setText("Quản lý chuyến xe");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setText("Thống kê");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setText("Đăng xuất");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton10.setText("Quản lý tài khoản");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Quản lý vé");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        empmButton.setText("Quản lý nhân viên");
        empmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empmButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Quản lý xe bus");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Quản lý tuyến xe");
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
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addGap(50, 50, 50)
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
                .addContainerGap(101, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1449, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
         
        int day, month, year;
        int busId, ticketId;
        int ticketSold;
        
        
        
        
        if(stDayText.getText().equals(""))
        {
            
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày");
            return;
        }
        
        if(stMonthCombo.getSelectedIndex() == 0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng");
            return;
        }
        
        if(stYearText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm");
            return;
        }
        
        
        if(busCombo.getSelectedIndex()==0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe bus");
            return;
        }
        
        if(ticketCombo.getSelectedIndex()==0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng loại vé");
            return;
        }
        
        if(ticketSoldText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng vé bán");
            return;
        }
        
        
        
        if(!isDigits(stYearText.getText()))
        {
            JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
            return;
        }
        else if(isDigits(stYearText.getText()))
        {
            if(Integer.parseInt(stYearText.getText().trim()) < 2000 || Integer.parseInt(stYearText.getText().trim()) > LocalDate.now().getYear())
            {
                System.out.println("run");
                JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
                return;                
            }
             
        }
        
        YearMonth my = null;
        if(stMonthCombo.getSelectedIndex() !=0)
        {
            my = YearMonth.of(Integer.parseInt(stYearText.getText().trim()), stMonthCombo.getSelectedIndex());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng");
            return;
        }
        
        
        if(!isDigits(stDayText.getText().trim()))
        {
           JOptionPane.showMessageDialog(this, "Ngày chỉ được nhập số");
           return;
        }
        else if(isDigits(stDayText.getText().trim()))
        {
//            System.out.println("run");
            day =Integer.parseInt(stDayText.getText());
//            System.out.println(bDay);
         //   System.out.println(my.lengthOfMonth());
            if(day < 1 || day >my.lengthOfMonth())
            {
//                System.out.println("run2");
                JOptionPane.showMessageDialog(this, "Ngày hợp lệ trong tháng từ 1-"+Integer.toString(my.lengthOfMonth()));
                return;
            }
            
                
        }
        
        
        day = Integer.parseInt(stDayText.getText().trim());
        month = stMonthCombo.getSelectedIndex();
        year = Integer.parseInt(stYearText.getText().trim());
        
        busId = Integer.parseInt(busCombo.getSelectedItem().toString().split("-")[0].trim());
        ticketId = Integer.parseInt(ticketCombo.getSelectedItem().toString().split("-")[0].trim());
        
        
        
        
        
        
        
        
        
        
        
        
        if(!isDigits(ticketSoldText.getText().trim()))
        {
            JOptionPane.showMessageDialog(this, "Số lượng vé bán chỉ được nhập số");
            return;
        }
        
        
        
        
        ticketSold = Integer.parseInt(ticketSoldText.getText().trim());
        
        
        
        LocalDate date = LocalDate.of(year, month, day);
        
        
        if(!date.isBefore(LocalDate.now()))
        {
            JOptionPane.showMessageDialog(this, "Chỉ được thêm thống kê các ngày trong quá khứ");
            return;
        }
        
        
        Connection con = establishCon();
        Statement st =null;
        ResultSet rs;
        try {
            
            String query = "INSERT BUS_TICKET "                  
                    + "VALUES(" +busId+", "+ ticketId +", "+ ticketSold+", '"+ date+"')";
            
            String checkQuery = "SELECT * FROM BUS_TICKET WHERE bus_id = "+busId+" AND ticket_id= "+ticketId +"AND work_day='"+date+"'";
            
            st = con.createStatement();
            rs = st.executeQuery(checkQuery);
            
            if(rs.next())
            {
                JOptionPane.showMessageDialog(this, "Xe và loại vé đã chọn đã có thống kê doanh thu trong ngày: "+date);
                return;
            }
                   
                st = con.createStatement();
                st.executeUpdate(query);
                
                
               DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
               dtm.setRowCount(0);
               displayST01(0, month, year);
               
               
               String queryCheckSt02 = "SELECT * FROM OPERATION_COST WHERE bus_id = "+busId+" AND work_day= '"+date+"'";
               st = con.createStatement();
               rs = st.executeQuery(queryCheckSt02);
               
               if(!rs.next())
               {
                   addButton.setEnabled(false);
                   busCombo.setEnabled(false);
                   stDayText.setEnabled(false);
                   stMonthCombo.setEnabled(false);
                   stYearText.setEnabled(false);
                   addButton1.setEnabled(true);
                   ticketSoldText.setText("");
                   ticketCombo.setSelectedIndex(0);
                   JOptionPane.showMessageDialog(this, "Thêm thành công, vui lòng nhập vào bảng chi phí vận hành cho ngày: "+date);
               }
               else{
                   ticketSoldText.setText("");
                   ticketCombo.setSelectedIndex(0);
                   JOptionPane.showMessageDialog(this, "Thêm thành công");
               }
               
          
           
            empSalText.setText(Float.toString(calEmpSalary(0, month, year))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(0, month, year))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(0, month, year)) + " vnd");
            profitText.setText(Float.toString(calNetIncome(0, month, year)) +" vnd");
                
                
                
                
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
    }//GEN-LAST:event_addButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        int day, month, year;
        int busId, ticketId;
        int ticketSold;
        
        
        if(stDayText.getText().equals(""))
        {
            
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày");
            return;
        }
        
        if(stMonthCombo.getSelectedIndex() == 0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng");
            return;
        }
        
        if(stYearText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm");
            return;
        }
        
        
        
        if(busCombo.getSelectedIndex()==0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe bus");
            return;
        }
        
        if(ticketCombo.getSelectedIndex()==0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng loại vé");
            return;
        }
        
        if(ticketSoldText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng vé bán");
            return;
        }
        if(!isDigits(stYearText.getText()))
        {
            JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
            return;
        }
        else if(isDigits(stYearText.getText()))
        {
            if(Integer.parseInt(stYearText.getText().trim()) < 2000 || Integer.parseInt(stYearText.getText().trim()) > LocalDate.now().getYear())
            {
                System.out.println("run");
                JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
                return;                
            }
             
        }
        
        YearMonth my = null;
        if(stMonthCombo.getSelectedIndex() !=0)
        {
            my = YearMonth.of(Integer.parseInt(stYearText.getText().trim()), stMonthCombo.getSelectedIndex());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng");
            return;
        }
        
        
        if(!isDigits(stDayText.getText().trim()))
        {
           JOptionPane.showMessageDialog(this, "Ngày chỉ được nhập số");
           return;
        }
        else if(isDigits(stDayText.getText().trim()))
        {
//            System.out.println("run");
            day =Integer.parseInt(stDayText.getText());
//            System.out.println(bDay);
         //   System.out.println(my.lengthOfMonth());
            if(day < 1 || day >my.lengthOfMonth())
            {
//                System.out.println("run2");
                JOptionPane.showMessageDialog(this, "Ngày hợp lệ trong tháng từ 1-"+Integer.toString(my.lengthOfMonth()));
                return;
            }
            
                
        }
        
        
        day = Integer.parseInt(stDayText.getText().trim());
        month = stMonthCombo.getSelectedIndex();
        year = Integer.parseInt(stYearText.getText().trim());
        
        busId = Integer.parseInt(busCombo.getSelectedItem().toString().split("-")[0].trim());
        ticketId = Integer.parseInt(ticketCombo.getSelectedItem().toString().split("-")[0].trim());
        
        
        
        
        
        if(!isDigits(ticketSoldText.getText().trim()))
        {
            JOptionPane.showMessageDialog(this, "Số lượng vé bán chỉ được nhập số");
            return;
        }
        
        
        
        
        ticketSold = Integer.parseInt(ticketSoldText.getText().trim());
        
        
        
        LocalDate date = LocalDate.of(year, month, day);
        
        
        if(!date.isBefore(LocalDate.now()))
        {
            JOptionPane.showMessageDialog(this, "Chỉ được thống kê các ngày trong quá khứ");
            return;
        }
        
        
        Connection con = establishCon();
        Statement st =null;
        ResultSet rs;
        try {
            
            String query = "UPDATE BUS_TICKET "                  
                    + "SET ticket_sold="+ ticketSold+" WHERE bus_id= "+busId+"AND ticket_id= "+ticketId+"AND  work_day= '"+ date+"'";
            

                   
                st = con.createStatement();
                st.executeUpdate(query);
                
                
               DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
               dtm.setRowCount(0);
               displayST01(0, month, year);
               
               
               
               
            
                ticketSoldText.setText("");
                stDayText.setText("");
                ticketCombo.setSelectedIndex(0);
                busCombo.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Sửa thành công");
               
               
          
           
            empSalText.setText(Float.toString(calEmpSalary(0, month, year))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(0, month, year))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(0, month, year)) + " vnd");
            profitText.setText(Float.toString(calNetIncome(0, month, year)) +" vnd");
                
                stDayText.setEnabled(true);
        stMonthCombo.setEnabled(true);
        stYearText.setEnabled(true);
        busCombo.setEnabled(true);
        ticketCombo.setEnabled(true);
                updateButton.setEnabled(false);
                addButton.setEnabled(true);
                
        }catch(Exception e)
        {
           // e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Sửa không thành công, vui lòng kiểm tra lại đã tồn tại thống kê này chưa");
        }
        
        
    }//GEN-LAST:event_updateButtonActionPerformed

    private void stTable02MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stTable02MouseClicked
        if(groupByCombo.getSelectedIndex()==0)
        {
            updateButton1.setEnabled(true);
            addButton1.setEnabled(false);
        int selectedRow = stTable02.getSelectedRow();
         
        DefaultTableModel dfModel = (DefaultTableModel) stTable02.getModel();
        //routeIdText.setText(dfModel.getValueAt(selectedRow, 1).toString());
        String stDate = dfModel.getValueAt(selectedRow, 3).toString();
        String swDateDecompose[] = stDate.split("-");
        
        stDayText.setText(swDateDecompose[2]);
        stYearText.setText(swDateDecompose[0]);
       switch (swDateDecompose[1])
        {
            case "01":
                swDateDecompose[1] = "Tháng 1";
                break;
            case "02":
                swDateDecompose[1] = "Tháng 2";
                break;
            case "03":
                swDateDecompose[1] = "Tháng 3";
                break;
            case "04":
                swDateDecompose[1] = "Tháng 4";
                break;
            case "05":
                swDateDecompose[1] = "Tháng 5";
                break;
            case "06":
                swDateDecompose[1] = "Tháng 6";
                break;
            case "07":
                swDateDecompose[1] = "Tháng 7";
                break;
             case "08":
                swDateDecompose[1] = "Tháng 8";
                break;
            case "09":
                swDateDecompose[1] = "Tháng 9";
                break;
            case "10":
                swDateDecompose[1] = "Tháng 10";
                break;
            case "11":
                swDateDecompose[1] = "Tháng 11";
                break;
            case "12":
                swDateDecompose[1] = "Tháng 12";
                break;
            default: 
                swDateDecompose[1] = "Chọn tháng";
                       
        }
        stMonthCombo.setSelectedItem(swDateDecompose[1]);
        
        
        
        String busString = dfModel.getValueAt(selectedRow, 0).toString();
        System.out.println(busString);
        
        for(int i=0; i<busCombo.getItemCount(); i++)
        {
            
            String bus = busCombo.getItemAt(i).toString().split("-")[1].trim();
           // System.out.println(bus);
            if(bus.equals(busString) )
            {
                busCombo.setSelectedIndex(i);
                break;
            }
        }
        
        
        fixCostText.setText(dfModel.getValueAt(selectedRow, 2).toString());
        
        stDayText.setEnabled(false);
        stMonthCombo.setEnabled(false);
        stYearText.setEnabled(false);
        busCombo.setEnabled(false);
        
        stTable01.clearSelection();
        updateButton.setEnabled(false);
        ticketCombo.setSelectedIndex(0);
        ticketSoldText.setText("");
        addButton.setEnabled(false);
        
        }
    }//GEN-LAST:event_stTable02MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        PhanCong work = new PhanCong();
        work.setVisible(true);
        work.setLocationRelativeTo(null);
        // System.out.println(this.infor);
        work.setInfor(this.infor);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        QLTime time = new QLTime();
        time.setVisible(true);
        time.setLocationRelativeTo(null);
        // System.out.println(this.infor);
        time.setInfor(this.infor);
        this.dispose();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:

        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Login log = new Login();
        log.setVisible(true);
        log.setLocationRelativeTo(null);
        //System.out.println(this.infor);

        // System.out.println(bus.infor);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        AccountManagement acc = new AccountManagement();
        acc.setVisible(true);
        acc.setLocationRelativeTo(null);
        //System.out.println(this.infor);
        acc.setInfor(this.infor);
        // System.out.println(bus.infor);
        this.dispose();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        QLVe tick = new QLVe();
        tick.setVisible(true);
        tick.setLocationRelativeTo(null);
        // System.out.println(this.infor);
        tick.setInfor(this.infor);
        this.dispose();
    }//GEN-LAST:event_jButton11ActionPerformed

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        QLChuyen route = new QLChuyen();
        route.setVisible(true);
        route.setLocationRelativeTo(null);
        // System.out.println(this.infor);
        route.setInfor(this.infor);
        this.dispose();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void stTable01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stTable01MouseClicked
        // TODO add your handling code here:
        
        if(groupByCombo.getSelectedIndex()==0)
        {
            
            
            updateButton.setEnabled(true);
            addButton.setEnabled(false);
        int selectedRow = stTable01.getSelectedRow();
         
        DefaultTableModel dfModel = (DefaultTableModel) stTable01.getModel();
        //routeIdText.setText(dfModel.getValueAt(selectedRow, 1).toString());
        String stDate = dfModel.getValueAt(selectedRow, 3).toString();
        String swDateDecompose[] = stDate.split("-");
        
        stDayText.setText(swDateDecompose[2]);
        stYearText.setText(swDateDecompose[0]);
       switch (swDateDecompose[1])
        {
            case "01":
                swDateDecompose[1] = "Tháng 1";
                break;
            case "02":
                swDateDecompose[1] = "Tháng 2";
                break;
            case "03":
                swDateDecompose[1] = "Tháng 3";
                break;
            case "04":
                swDateDecompose[1] = "Tháng 4";
                break;
            case "05":
                swDateDecompose[1] = "Tháng 5";
                break;
            case "06":
                swDateDecompose[1] = "Tháng 6";
                break;
            case "07":
                swDateDecompose[1] = "Tháng 7";
                break;
             case "08":
                swDateDecompose[1] = "Tháng 8";
                break;
            case "09":
                swDateDecompose[1] = "Tháng 9";
                break;
            case "10":
                swDateDecompose[1] = "Tháng 10";
                break;
            case "11":
                swDateDecompose[1] = "Tháng 11";
                break;
            case "12":
                swDateDecompose[1] = "Tháng 12";
                break;
            default: 
                swDateDecompose[1] = "Chọn tháng";
                       
        }
        stMonthCombo.setSelectedItem(swDateDecompose[1]);
        
        
        
        String busString = dfModel.getValueAt(selectedRow, 0).toString();
        System.out.println(busString);
        
        for(int i=0; i<busCombo.getItemCount(); i++)
        {
            
            String bus = busCombo.getItemAt(i).toString().split("-")[1].trim();
           // System.out.println(bus);
            if(bus.equals(busString) )
            {
                busCombo.setSelectedIndex(i);
                break;
            }
        }
        
        String ticketTypeString = dfModel.getValueAt(selectedRow, 1).toString();
        
        for(int i=0; i<ticketCombo.getItemCount(); i++)
        {
            String ticket = ticketCombo.getItemAt(i).toString().split("-")[1].trim();
            //System.out.println(ticket);
            if(ticket.equals(ticketTypeString))
            {
                ticketCombo.setSelectedIndex(i);
                break;
            }
        }
        
        ticketSoldText.setText(dfModel.getValueAt(selectedRow, 2).toString());
        
        stDayText.setEnabled(false);
        stMonthCombo.setEnabled(false);
        stYearText.setEnabled(false);
        busCombo.setEnabled(false);
        ticketCombo.setEnabled(false);
        
        
        stTable02.clearSelection();
        updateButton1.setEnabled(false);
        fixCostText.setText("");
        
        }
        
        
        
    }//GEN-LAST:event_stTable01MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int index = groupByCombo.getSelectedIndex();
        int month, year;
        
        
        
        
        
        
        
        switch (stMonthCombo.getSelectedItem().toString())
        {
            case "Tháng 1":
                month = 1;
                break;
            case "Tháng 2":
                month = 2;
                break;
            case "Tháng 3":
                month = 3;
                break;
            case "Tháng 4":
                month = 4;
                break;
            case "Tháng 5":
                month = 5;
                break;
            case "Tháng 6":
                month = 6;
                break;
            case "Tháng 7":
                month = 7;
                break;
            case "Tháng 8":
                month = 8;
                break;
            case "Tháng 9":
                month = 9;
                break;
            case "Tháng 10":
                month = 10;
                break;
            case "Tháng 11":
                month = 11;
                break;
            case "Tháng 12":
                month = 12;
                break;
            default: 
                month = 0;
                       
        }
        
        
        
        
        //year = Integer.parseInt(stYearText.getText());
        
        
        
        
        if(index == 0)
        {
            
        if(stMonthCombo.getSelectedIndex() == 0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng");
            return;
        }
        
        if(stYearText.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm");
            return;
        }
        
        if(!isDigits(stYearText.getText()))
        {
            JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
            return;
        }
        else if(isDigits(stYearText.getText()))
        {
            if(Integer.parseInt(stYearText.getText().trim()) < 2000 || Integer.parseInt(stYearText.getText().trim()) > LocalDate.now().getYear())
            {
                System.out.println("run");
                JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
                return;                
            }
             
        }
        
        
        year = Integer.parseInt(stYearText.getText());
            
            DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
            dtm.setRowCount(0);
            DefaultTableModel dtm2 = (DefaultTableModel) stTable02.getModel();
            dtm2.setRowCount(0);
            displayST01(index, month, year);
            displayST02(index, month, year);
            System.out.println("run");
            empSalText.setText(Float.toString(calEmpSalary(index, month, year))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(index, month, year))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(index, month, year)) + " vnd");
            profitText.setText(Float.toString(calNetIncome(index, month, year)) +" vnd");
            
        }
        else if(index ==1)
        {
            
            if(stYearText.getText().trim().equals(""))
            {   
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm");
            return;
            }
            year = Integer.parseInt(stYearText.getText());
            DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
            dtm.setRowCount(0);
            DefaultTableModel dtm2 = (DefaultTableModel) stTable02.getModel();
            dtm2.setRowCount(0);
            displayST01(index, month, year);
            displayST02(index, month, year);
            empSalText.setText(Float.toString(calEmpSalary(index, month, year))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(index, month, year))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(index, month, year)) + " vnd");
            profitText.setText(Float.toString(calNetIncome(index, month, year)) +" vnd");
        }
        else if(index ==2)
        {
            year = LocalDate.now().getYear();
            DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
            dtm.setRowCount(0);
            DefaultTableModel dtm2 = (DefaultTableModel) stTable02.getModel();
            dtm2.setRowCount(0);
            displayST01(index, month, year);
            displayST02(index, month, year);
            empSalText.setText(Float.toString(calEmpSalary(index, month, year))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(index, month, year))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(index, month, year)) + " vnd");
            profitText.setText(Float.toString(calNetIncome(index, month, year)) +" vnd");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void groupByComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_groupByComboItemStateChanged
        // TODO add your handling code here:
        int index = groupByCombo.getSelectedIndex();
        
        
        
        
        
        if(index ==0)
        {
            DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
            dtm.setRowCount(0);
            DefaultTableModel dtm2 = (DefaultTableModel) stTable02.getModel();
            dtm2.setRowCount(0);
            LocalDate now = LocalDate.now();
            displayST01(index, now.getMonthValue(), now.getYear());
            displayST02(index, now.getMonthValue(), now.getYear());
            stDayText.setEnabled(true);
            stMonthCombo.setEnabled(true);
            stYearText.setEnabled(true);
            
            //LocalDate now = LocalDate.now();
            empSalText.setText(Float.toString(calEmpSalary(index, now.getMonthValue(), now.getYear()))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(index, now.getMonthValue(), now.getYear()))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(index, now.getMonthValue(), now.getYear())) + " vnd");
            profitText.setText(Float.toString(calNetIncome(index, now.getMonthValue(), now.getYear())) +" vnd");

            stMonthCombo.setSelectedIndex(now.getMonthValue());
            stYearText.setText(Integer.toString(now.getYear()));
            
            ticketSoldText.setText("");
                stDayText.setText("");
                ticketCombo.setSelectedIndex(0);
                busCombo.setSelectedIndex(0);
                fixCostText.setText("");
            
            
            addButton.setEnabled(true);
            addButton1.setEnabled(false);
            updateButton.setEnabled(false);
            updateButton1.setEnabled(false);
            busCombo.setEnabled(true);
            ticketCombo.setEnabled(true);
            
        }
        else if( index ==1)
        {
             DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
            dtm.setRowCount(0);
            DefaultTableModel dtm2 = (DefaultTableModel) stTable02.getModel();
            dtm2.setRowCount(0);
            LocalDate now = LocalDate.now();
            displayST01(index, now.getMonthValue(), now.getYear());
            displayST02(index, now.getMonthValue(), now.getYear());
            stDayText.setEnabled(false);
            stYearText.setEnabled(true);
            stMonthCombo.setEnabled(false);
            busCombo.setEnabled(false);
            ticketCombo.setEnabled(false);
            
            
            //LocalDate now = LocalDate.now();
         empSalText.setText(Float.toString(calEmpSalary(index, now.getMonthValue(), now.getYear()))+ " vnd");
         opCostText.setText(Float.toString(calOpCost(index, now.getMonthValue(), now.getYear()))+" vnd");
         turnoverText.setText(Float.toString(calTurnover(index, now.getMonthValue(), now.getYear())) + " vnd");
         profitText.setText(Float.toString(calNetIncome(index, now.getMonthValue(), now.getYear())) +" vnd");
         
         stMonthCombo.setSelectedIndex(0);
         stYearText.setText(Integer.toString(now.getYear()));
         
         
         
         ticketSoldText.setText("");
                stDayText.setText("");
                ticketCombo.setSelectedIndex(0);
                busCombo.setSelectedIndex(0);
                fixCostText.setText("");
            
            
            addButton.setEnabled(false);
            addButton1.setEnabled(false);
            updateButton.setEnabled(false);
            updateButton1.setEnabled(false);
        }
        else if(index ==2)
        {
            DefaultTableModel dtm = (DefaultTableModel) stTable01.getModel();
            dtm.setRowCount(0);
            DefaultTableModel dtm2 = (DefaultTableModel) stTable02.getModel();
            dtm2.setRowCount(0);
            LocalDate now = LocalDate.now();
            displayST01(index, now.getMonthValue(), now.getYear());
            displayST02(index, now.getMonthValue(), now.getYear());
            stMonthCombo.setEnabled(false);
            stDayText.setEnabled(false);
            stYearText.setEnabled(false);
            busCombo.setEnabled(false);
            ticketCombo.setEnabled(false);
            
            
           // LocalDate now = LocalDate.now();
            empSalText.setText(Float.toString(calEmpSalary(index, now.getMonthValue(), now.getYear()))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(index, now.getMonthValue(), now.getYear()))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(index, now.getMonthValue(), now.getYear())) + " vnd");
            profitText.setText(Float.toString(calNetIncome(index, now.getMonthValue(), now.getYear())) +" vnd");

            stMonthCombo.setSelectedIndex(0);
            stYearText.setText("");
            
            
            
            ticketSoldText.setText("");
                stDayText.setText("");
                ticketCombo.setSelectedIndex(0);
                busCombo.setSelectedIndex(0);
                fixCostText.setText("");
            
            
            addButton.setEnabled(false);
            addButton1.setEnabled(false);
            updateButton.setEnabled(false);
            updateButton1.setEnabled(false);
        }
    }//GEN-LAST:event_groupByComboItemStateChanged

    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
        stTable01.clearSelection();
        stTable02.clearSelection();
        
        addButton.setEnabled(true);
        addButton1.setEnabled(false);
        
        updateButton.setEnabled(false);
        updateButton1.setEnabled(false);
        
        stDayText.setText("");
//        stMonthCombo.setSelectedIndex(0);
//        stYearText.setText("");
         busCombo.setSelectedIndex(0);
         ticketCombo.setSelectedIndex(0);
         gasCostCombo.addItem("--Chi phí xăng--");
        fixCostText.setText("");
        
        
        stDayText.setEnabled(true);
        stMonthCombo.setEnabled(true);
        stYearText.setEnabled(true);
        busCombo.setEnabled(true);
        ticketCombo.setEnabled(true);
        
        
    }//GEN-LAST:event_ResetButtonActionPerformed

    private void updateButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButton1ActionPerformed
        // TODO add your handling code here:
        
        int day, month, year;
        int busId;
        float fixCost;
        
        if(fixCostText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập chi phí sửa chữa");
            return;
            
        }
        
        if(!isDigits(fixCostText.getText().trim()))
        {
            JOptionPane.showMessageDialog(this, "Chi phí sửa chữa chỉ được nhập số");
            return;
        }
        
        fixCost = Float.parseFloat(fixCostText.getText().trim());
        day = Integer.parseInt(stDayText.getText().trim());
        month = stMonthCombo.getSelectedIndex();
        year = Integer.parseInt(stYearText.getText().trim());
        
        busId = Integer.parseInt(busCombo.getSelectedItem().toString().split("-")[0].trim());
        
        LocalDate date = LocalDate.of(year, month, day);
        
        Connection con = establishCon();
        Statement st =null;
        ResultSet rs;
        try {
            
            String query = "UPDATE OPERATION_COST "
                    + "SET gas_cost= "+fixCost+" WHERE bus_id= "+busId+" AND work_day= '"+date+"'";
            

                   
                st = con.createStatement();
                st.executeUpdate(query);
                
                
               DefaultTableModel dtm = (DefaultTableModel) stTable02.getModel();
               dtm.setRowCount(0);
               displayST02(0, month, year);
               
               
               
               
            
                fixCostText.setText("");
                stDayText.setText("");
               
                busCombo.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Sửa thành công");
               
               
          
           
            empSalText.setText(Float.toString(calEmpSalary(0, month, year))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(0, month, year))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(0, month, year)) + " vnd");
            profitText.setText(Float.toString(calNetIncome(0, month, year)) +" vnd");
                
        stDayText.setEnabled(true);
        stMonthCombo.setEnabled(true);
        stYearText.setEnabled(true);
        busCombo.setEnabled(true);
        ticketCombo.setEnabled(true);
                updateButton1.setEnabled(false);
                addButton.setEnabled(true);
                
        }catch(Exception e)
        {
           // e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Sửa không thành công, vui lòng kiểm tra lại đã tồn tại thống kê này chưa");
        }
        
    }//GEN-LAST:event_updateButton1ActionPerformed

    private void addButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButton1ActionPerformed
        // TODO add your handling code here:
        int busId;
        float gasCost, fixCost;
        int day, month, year;
        
        
        if(stDayText.getText().equals(""))
        {
            
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày");
            return;
        }
        
        if(stMonthCombo.getSelectedIndex() == 0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng");
            return;
        }
        
        if(stYearText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm");
            return;
        }
        
        if(fixCostText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập chi phí sửa chữa");
            return;
        }
        
        if(!isDigits(stYearText.getText()))
        {
            JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
            return;
        }
        else if(isDigits(stYearText.getText()))
        {
            if(Integer.parseInt(stYearText.getText().trim()) < 2000 || Integer.parseInt(stYearText.getText().trim()) > LocalDate.now().getYear())
            {
                System.out.println("run");
                JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
                return;                
            }
             
        }
        
        YearMonth my = null;
        if(stMonthCombo.getSelectedIndex() !=0)
        {
            my = YearMonth.of(Integer.parseInt(stYearText.getText().trim()), stMonthCombo.getSelectedIndex());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng sinh");
            return;
        }
        
        
        if(!isDigits(stDayText.getText().trim()))
        {
           JOptionPane.showMessageDialog(this, "Ngày chỉ được nhập số");
           return;
        }
        else if(isDigits(stDayText.getText().trim()))
        {
//            System.out.println("run");
            day =Integer.parseInt(stDayText.getText());
//            System.out.println(bDay);
         //   System.out.println(my.lengthOfMonth());
            if(day < 1 || day >my.lengthOfMonth())
            {
//                System.out.println("run2");
                JOptionPane.showMessageDialog(this, "Ngày hợp lệ trong tháng từ 1-"+Integer.toString(my.lengthOfMonth()));
                return;
            }
            
                
        }
        
        
        if(!isDigits(fixCostText.getText().trim()))
        {
           JOptionPane.showMessageDialog(this, "Chi phí sửa chữa chỉ được nhập số");
           return;
        }
        
        
        
        busId = Integer.parseInt(busCombo.getSelectedItem().toString().split("-")[0].trim());
        gasCost = Float.parseFloat(gasCostCombo.getSelectedItem().toString().split("-")[1].trim());
        fixCost = Float.parseFloat(fixCostText.getText().trim());
        
        day = Integer.parseInt(stDayText.getText().trim());
        month = stMonthCombo.getSelectedIndex();
        year = Integer.parseInt(stYearText.getText().trim());
        
        LocalDate date = LocalDate.of(year, month, day);
        
        Connection con = establishCon();
        Statement st = null;
        try{
            String insertQuery = "INSERT OPERATION_COST VALUES("+busId +", "+gasCost+", "+fixCost+", '"+date+"')";
            st = con.createStatement();
            st.executeUpdate(insertQuery);
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            
            
            DefaultTableModel dtm = (DefaultTableModel) stTable02.getModel();
            dtm.setRowCount(0);
            
            displayST02(0, month, year);
            
            stDayText.setEnabled(true);
            stMonthCombo.setEnabled(true);
            stYearText.setEnabled(true);
            addButton.setEnabled(true);
            addButton1.setEnabled(false);
            busCombo.setEnabled(true);
            fixCostText.setText("");
            
            empSalText.setText(Float.toString(calEmpSalary(0, month, year))+ " vnd");
            opCostText.setText(Float.toString(calOpCost(0, month, year))+" vnd");
            turnoverText.setText(Float.toString(calTurnover(0, month, year)) + " vnd");
            profitText.setText(Float.toString(calNetIncome(0, month, year)) +" vnd");
                    
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_addButton1ActionPerformed

    private void busComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_busComboItemStateChanged
        // TODO add your handling code here
        
        gasCostCombo.removeAllItems();
        if(!(busCombo.getSelectedIndex() == 0))
        {
            int routeId = Integer.parseInt(busCombo.getSelectedItem().toString().split(":")[1]);
        
        float gasCost = 0;
        
        Connection con = establishCon();
        Statement st =null;
        try {
            
            String query = "SELECT gas_cost FROM ROUTE WHERE route_id = "+ routeId;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next())
            {
                gasCost = rs.getFloat("gas_cost");
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
              
              gasCostCombo.addItem("Tuyến số: "+routeId+" - "+gasCost);
        }
        else{
            gasCostCombo.addItem("--Chi phí xăng--");
        }
        
              
    }
        
        
        
//GEN-LAST:event_busComboItemStateChanged

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int day, month, year;
        int busId, ticketId = 0;
        
        
        if(stDayText.getText().equals(""))
        {
            
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày");
            return;
        }
        
        if(stMonthCombo.getSelectedIndex() == 0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng");
            return;
        }
        
        if(stYearText.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm");
            return;
        }
        
        
        if(!isDigits(stYearText.getText()))
        {
            JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
            return;
        }
        else if(isDigits(stYearText.getText()))
        {
            if(Integer.parseInt(stYearText.getText().trim()) < 2000 || Integer.parseInt(stYearText.getText().trim()) > LocalDate.now().getYear())
            {
                System.out.println("run");
                JOptionPane.showMessageDialog(this, "Năm chỉ được nhập \"số\" năm từ "+2000 +" đến " + Integer.toString(LocalDate.now().getYear()));
                return;                
            }
             
        }
        
        YearMonth my = null;
        if(stMonthCombo.getSelectedIndex() !=0)
        {
            my = YearMonth.of(Integer.parseInt(stYearText.getText().trim()), stMonthCombo.getSelectedIndex());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tháng sinh");
            return;
        }
        
        
        if(!isDigits(stDayText.getText().trim()))
        {
           JOptionPane.showMessageDialog(this, "Ngày chỉ được nhập số");
           return;
        }
        else if(isDigits(stDayText.getText().trim()))
        {
//            System.out.println("run");
            day =Integer.parseInt(stDayText.getText());
//            System.out.println(bDay);
         //   System.out.println(my.lengthOfMonth());
            if(day < 1 || day >my.lengthOfMonth())
            {
//                System.out.println("run2");
                JOptionPane.showMessageDialog(this, "Ngày hợp lệ trong tháng từ 1-"+Integer.toString(my.lengthOfMonth()));
                return;
            }
            
                
        }
        
        if(busCombo.getSelectedIndex()==0)
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn xe bus");
            return;
        }
        
        
        
        day = Integer.parseInt(stDayText.getText().trim());
        month = stMonthCombo.getSelectedIndex();
        year = Integer.parseInt(stYearText.getText().trim());
        
        busId = Integer.parseInt(busCombo.getSelectedItem().toString().split("-")[0].trim());
        
        if(!ticketCombo.getSelectedItem().toString().split("-")[0].trim().equals(""))
        {
            ticketId = Integer.parseInt(ticketCombo.getSelectedItem().toString().split("-")[0].trim());
        }
        
        
        
        
        Connection con = establishCon();
        Statement st = null;
        ResultSet rs;
        
        try{
            
            //System.out.println("run this111");
            
            String query = "SELECT * FROM BUS_TICKET WHERE bus_id = "+busId+" AND ticket_id= "+ticketId+" AND work_day= '"+LocalDate.of(year, month, day)+"'";
            String query2 = "SELECT * FROM OPERATION_COST WHERE bus_id= "+busId+" AND work_day= '"+LocalDate.of(year, month, day)+"'";
            
            if(!(ticketId==0))
            {
                st = con.createStatement();
                rs = st.executeQuery(query);
            
            if(!rs.next())
            {
                JOptionPane.showMessageDialog(this, "Xe và loại vé đã chọn chưa có thống kê doanh thu trong ngày: "+LocalDate.of(year, month, day) +" bạn có thể thêm");
                addButton.setEnabled(true);
                
            }else{
                JOptionPane.showMessageDialog(this, "Xe và loại vé đã chọn đã có thống kê doanh thu trong ngày: "+LocalDate.of(year, month, day) +" bạn không thể thêm");
                addButton.setEnabled(false);
            }
            }
            
            
            st = con.createStatement();
            rs = st.executeQuery(query2);
            
            if(!rs.next())
            {
                JOptionPane.showMessageDialog(this, "Chưa có thống kê chi phí vận hành của xe đã chọn trong ngày: "+ LocalDate.of(year, month, day) +" bạn có thể thêm");
                addButton1.setEnabled(true);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Ðã có thống kê chi phí vận hành của xe đã chọn trong ngày: "+ LocalDate.of(year, month, day) +" bạn không thể thêm");
                addButton1.setEnabled(false);
            }
            
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton8ActionPerformed

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
            java.util.logging.Logger.getLogger(Statistic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Statistic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Statistic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Statistic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Statistic().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ResetButton;
    private javax.swing.JButton addButton;
    private javax.swing.JButton addButton1;
    private javax.swing.JComboBox<String> busCombo;
    private javax.swing.JTextField empSalText;
    private javax.swing.JButton empmButton;
    private javax.swing.JTextField fixCostText;
    private javax.swing.JComboBox<String> gasCostCombo;
    private javax.swing.JComboBox<String> groupByCombo;
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
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField opCostText;
    private javax.swing.JTextField profitText;
    private javax.swing.JTextField stDayText;
    private javax.swing.JComboBox<String> stMonthCombo;
    private javax.swing.JTable stTable01;
    private javax.swing.JTable stTable02;
    private javax.swing.JTextField stYearText;
    private javax.swing.JComboBox<String> ticketCombo;
    private javax.swing.JTextField ticketSoldText;
    private javax.swing.JTextField turnoverText;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton updateButton1;
    // End of variables declaration//GEN-END:variables
}
