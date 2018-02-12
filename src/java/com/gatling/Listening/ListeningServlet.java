/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gatling.Listening;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "ListeningServlet", urlPatterns = {"/ListeningServlet"})
public class ListeningServlet extends HttpServlet {
    
    private String message;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
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
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListeningServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListeningServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>" + message + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        setResponseAccess(response);
        String ac = request.getParameter("account");
        String pw = request.getParameter("password");
        System.out.printf("account:"+ac+"     password:"+pw);
        processRequest(request, response);
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
        processRequest(request, response);
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

}
