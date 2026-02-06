/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author Hao
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

   private static final String LOGIN_PAGE = "/login.jsp";
   private static final String ADMIN_PAGE = "/admin.jsp";
   private static final String MANAGER_PAGE = "/manager.jsp";
   private static final String AGENT_PAGE = "/agent.jsp";
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Nếu chưa submit form (mở trang login) -> forward luôn
        if (username == null || password == null) {
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            User user = dao.checkLogin(username, password);

            if (user == null) {
                request.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("USER", user);

            String role = user.getRole();
            String ctx = request.getContextPath();

            if ("ADMIN".equalsIgnoreCase(role)) {
                response.sendRedirect(ctx + ADMIN_PAGE);
            } else if ("MANAGER".equalsIgnoreCase(role)) {
                response.sendRedirect(ctx + MANAGER_PAGE);
            } else {
                response.sendRedirect(ctx + AGENT_PAGE);
            }

        } catch (Exception e) {
            throw new ServletException(e);
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
