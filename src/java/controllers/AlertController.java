/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AlertDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Alert;
import models.User;

/**
 *
 * @author Hao
 */
@WebServlet(name = "AlertController", urlPatterns = {"/AlertController"})
public class AlertController extends HttpServlet {

    private static final String ALERT_PAGE = "/alerts.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "alert_list";
        }

        try {
            HttpSession session = request.getSession(false);
            User u = (session == null) ? null : (User) session.getAttribute("USER");

            if (u == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            AlertDAO dao = new AlertDAO();

            switch (action) {
                case "alert_ack": {
                    int alertId = parseIntSafe(request.getParameter("alertId"), 0);
                    boolean ok = (alertId > 0) && dao.ack(alertId);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=alert_list&ack=" + (ok ? "1" : "0"));
                    return;
                }

                case "alert_done": {
                    int alertId = parseIntSafe(request.getParameter("alertId"), 0);
                    boolean ok = (alertId > 0) && dao.done(alertId);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=alert_list&done=" + (ok ? "1" : "0"));
                    return;
                }

                case "alert_list":
                default: {
                    List<Alert> list;

                    // ADMIN/MANAGER xem all; AGENT xem của mình
                    if ("ADMIN".equalsIgnoreCase(u.getRole()) || "MANAGER".equalsIgnoreCase(u.getRole())) {
                        list = dao.getAll();
                    } else {
                        list = dao.getByAgentId(u.getUserId());
                    }

                    request.setAttribute("alertsList", list);
                    request.getRequestDispatcher(ALERT_PAGE).forward(request, response);
                    return;
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
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
