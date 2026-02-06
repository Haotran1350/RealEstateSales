/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.LeadDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Lead;
import models.User;

/**
 *
 * @author Hao
 */
@WebServlet(name = "LeadController", urlPatterns = {"/LeadController"})
public class LeadController extends HttpServlet {

    private static final String LEADS_PAGE = "/leads.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "lead_list";

        try {
            switch (action) {
                case "lead_create": {
                    HttpSession session = request.getSession(false);
                    User u = (session == null) ? null : (User) session.getAttribute("USER");

                    String fullName = request.getParameter("fullName");
                    String phone = request.getParameter("phone");
                    String email = request.getParameter("email");
                    String purpose = request.getParameter("purpose"); // BUY/RENT
                    String budgetMinStr = request.getParameter("budgetMin");
                    String budgetMaxStr = request.getParameter("budgetMax");
                    String locationPrefJson = request.getParameter("locationPrefJson");

                    // Validate tối thiểu
                    if (fullName == null || fullName.trim().isEmpty()) {
                        request.setAttribute("error", "Full name không được để trống");
                        List<Lead> leads = new LeadDAO().getAll();
                        request.setAttribute("leadsList", leads);
                        request.getRequestDispatcher(LEADS_PAGE).forward(request, response);
                        return;
                    }

                    BigDecimal budgetMin = parseBD(budgetMinStr);
                    BigDecimal budgetMax = parseBD(budgetMaxStr);

                    Lead x = new Lead();
                    x.setFullName(fullName.trim());
                    x.setPhone(phone);
                    x.setEmail(email);
                    x.setPurpose((purpose == null || purpose.isEmpty()) ? "BUY" : purpose);
                    x.setBudgetMin(budgetMin);
                    x.setBudgetMax(budgetMax);
                    x.setLocationPrefJson(locationPrefJson);
                    x.setStatus("NEW");

                    // Nếu user là AGENT thì auto assign lead cho agent luôn
                    if (u != null && u.getUserId() > 0 && "AGENT".equalsIgnoreCase(u.getRole())) {
                        x.setAssignedAgentId(u.getUserId());
                    }

                    boolean ok = new LeadDAO().create(x);

                    String ctx = request.getContextPath();
                    response.sendRedirect(ctx + "/MainController?action=lead_list&created=" + (ok ? "1" : "0"));
                    return;
                }

                case "lead_list":
                default: {
                    List<Lead> leads = new LeadDAO().getAll();
                    request.setAttribute("leadsList", leads);
                    request.getRequestDispatcher(LEADS_PAGE).forward(request, response);
                    return;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private BigDecimal parseBD(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try {
            return new BigDecimal(s);
        } catch (Exception e) {
            return null;
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
