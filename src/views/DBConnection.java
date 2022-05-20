/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import com.microsoft.sqlserver.jdbc.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author TranKhang
 */
public class DBConnection {
    public static Connection getConnection(){
        Connection connection= null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url= "jdbc:sqlserver://localhost:1433;"
            + "databaseName=BusManagement;"
            + "encrypt=true;trustServerCertificate=true";
            String user= "sa";
            String password="viet1234";
            connection =DriverManager.getConnection(url, user, password);
        }catch(SQLException ex){
            ex.printStackTrace();
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
}

    public static void closeConnection(Connection con){
        if(con!=null){
            try{
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
//    public static void main(String[] args){
//        System.out.println(getConnection());
//    }
            
            }