/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hao
 */
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "/login.jsp";
    private static final String HOME_PAGE = "/home.jsp";

    private static final String LOGIN_CONTROLLER = "/LoginController";
    private static final String LOGOUT_CONTROLLER = "/LogoutController";
    private static final String LEAD_CONTROLLER = "/LeadController";
    private static final String INTERACTION_CONTROLLER = "/InteractionController";
    private static final String PROPERTY_CONTROLLER = "/PropertyController";
    private static final String LISTING_CONTROLLER = "/ListingController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String url;

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "login":
                url = LOGIN_CONTROLLER;
                break;

            case "logout":
                url = LOGOUT_CONTROLLER;
                break;

            case "lead_list":

            case "lead_create":
                url = LEAD_CONTROLLER;
                break;

            case "interaction_list":

            case "interaction_create":
                url = INTERACTION_CONTROLLER;
                break;

            case "property_list":

            case "property_create":

            case "property_update":

            case "property_delete":
                url = PROPERTY_CONTROLLER;
                break;

            case "listing_list":
                
            case "listing_create":
                
            case "listing_update_status":
                
            case "listing_delete":
                url = LISTING_CONTROLLER; 
                break;

            default:
                url = HOME_PAGE;
        }

        request.getRequestDispatcher(url).forward(request, response);
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
