/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ValuationDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import models.Valuation;

/**
 *
 * @author Hao
 */
@WebServlet(name = "ValuationController", urlPatterns = {"/ValuationController"})
public class ValuationController extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "valuation_generate";

        // login check (giống style mày)
        HttpSession session = request.getSession(false);
        User u = (session == null) ? null : (User) session.getAttribute("USER");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int listingId = parseIntSafe(request.getParameter("listingId"), 0);
        if (listingId <= 0) {
            response.sendRedirect(request.getContextPath() + "/MainController?action=listing_list");
            return;
        }

        try {
            ValuationDAO dao = new ValuationDAO();

            if ("valuation_generate".equals(action)) {
                Valuation v = dao.generateDemoValuation(listingId);
                boolean ok = (v != null) && dao.createForListing(v);

                response.sendRedirect(request.getContextPath()
                        + "/MainController?action=listing_list&val=" + (ok ? "1" : "0"));
                return;
            }

            // fallback
            response.sendRedirect(request.getContextPath() + "/MainController?action=listing_list");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
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
