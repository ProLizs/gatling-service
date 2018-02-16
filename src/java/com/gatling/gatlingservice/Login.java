/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gatling.gatlingservice;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gatling.db.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.gatling.basedata.menuData;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.json.*;
import com.gatling.basedata.Token;
import com.google.gson.*;


/**
 *
 * @author Administrator
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
    private String message;
    DBManager dbmgr;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        dbmgr = new DBManager();
        message = "Hello World , Nect To Meet You: " + System.currentTimeMillis();
        System.out.println("servlet初始化……");
        super.init();
    }

    private void setResponseAccess(HttpServletResponse response) {
        // 允许该域发起跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");//*允许任何域
        // 允许的外域请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        // 在999999秒内，不需要再发送预检验请求，可以缓存该结果
        response.setHeader("Access-Control-Max-Age", "9");
        // 允许跨域请求包含某请求头,x-requested-with请求头为异步请求
        response.setHeader("Access-Control-Allow-Headers",
                "x-requested-with");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param resultData http responseData
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String resultData)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            ResultSet ret = dbmgr.Execute("SELECT * FROM `menu_L1` a LEFT JOIN `menu_L2` b ON a.menu_id =  b.menu_id");
            ResultSet ret = dbmgr.Execute("SELECT * FROM `menu_L1`");
            ret.last();
            int rsCounut = ret.getRow();
            ret.beforeFirst();
            
            String str = null;
            int index = 1;
            str = "{\"token\":"+resultData + ",";
            str += "\"menu\":[";
            while(ret.next()){
                str += "{";
                str += "\"menuid\":" + "\""+ ret.getString("menu_id")+"\",";
                str += "\"menu_name\":" + "\""+ ret.getString("menu_name") +"\"";
                str += "}";
                if(index < rsCounut){
                    str += ",";
                    index++;
                }
            }
            str += "]}";

            System.out.print(str);
            
            out.println(str);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.setResponseAccess(response);
        String ac = request.getParameter("account");
        String pw = request.getParameter("password");
        System.out.printf("account:"+ac+"     password:"+pw);
        ResultSet ret = dbmgr.Execute("SELECT * FROM db_account WHERE account='" + ac + "' AND 'password'='"+pw+"'");
        try {
            String status = ret.getMetaData().getColumnLabel(3);
            if(status != null){
                processRequest(request,response,resultToken());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response,"");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
    * 生成令牌
    * 
    * @Param resp
    * @return
    */
   public synchronized static String resultToken() {
           // 生成令牌
           String apikey = System.currentTimeMillis() + "";
           return apikey;
   }
}
