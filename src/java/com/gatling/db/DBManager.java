/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gatling.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DBManager {
    protected Connection conn ;
    protected Statement stmt;
//    public void DBManager(String connect ,String _un,String _pw) throws SQLException{
//        conn = (Connection) DriverManager.getConnection(connect,_un,_pw);
//    }
    public ResultSet Execute(String cmd){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gatling?zeroDateTimeBehavior=convertToNull","root","1234");
            if(conn != null){
                stmt = conn.createStatement();
                return stmt.executeQuery(cmd);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
