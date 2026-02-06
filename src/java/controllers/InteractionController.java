/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.InteractionDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static javax.servlet.jsp.PageContext.PAGE;
import models.Interaction;
import models.User;

/**
 *
 * @author Hao
 */
@WebServlet(name = "InteractionController", urlPatterns = {"/InteractionController"})
public class InteractionController extends HttpServlet {

    private static final String INTERACTION_PAGE = "/interactions.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "interaction_list";

        try {
            switch (action) {
                case "interaction_create": {
                    int leadId = parseIntSafe(request.getParameter("leadId"), 0);
                    if (leadId <= 0) {
                        response.sendRedirect(request.getContextPath() + "/MainController?action=lead_list");
                        return;
                    }

                    HttpSession session = request.getSession(false);
                    User u = (session == null) ? null : (User) session.getAttribute("USER");
                    if (u == null) {
                        response.sendRedirect(request.getContextPath() + "/login.jsp");
                        return;
                    }

                    String channel = request.getParameter("channel");
                    String content = request.getParameter("content");
                    String outcome = request.getParameter("outcome");
                    String nextActionAtStr = request.getParameter("nextActionAt"); // yyyy-MM-ddTHH:mm (input datetime-local)

                    if (channel == null || channel.trim().isEmpty()) channel = "CALL";
                    if (content == null || content.trim().isEmpty()) {
                        // load list lại + báo lỗi
                        request.setAttribute("error", "Content không được để trống");
                        List<Interaction> list = new InteractionDAO().getByLeadId(leadId);
                        request.setAttribute("interactionList", list);
                        request.setAttribute("leadId", leadId);
                        request.getRequestDispatcher(PAGE).forward(request, response);
                        return;
                    }

                    Interaction x = new Interaction();
                    x.setLeadId(leadId);
                    x.setAgentId(u.getUserId());
                    x.setChannel(channel);
                    x.setContent(content);
                    x.setOutcome(outcome);

                    // để ts = null cho DB tự set SYSDATETIME()
                    x.setTs(null);

                    Timestamp next = parseDatetimeLocal(nextActionAtStr);
                    x.setNextActionAt(next);

                    boolean ok = new InteractionDAO().create(x);

                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=interaction_list&leadId=" + leadId
                            + "&created=" + (ok ? "1" : "0"));
                    return;
                }

                case "interaction_list":
                default: {
                    int leadId = parseIntSafe(request.getParameter("leadId"), 0);
                    if (leadId <= 0) {
                        response.sendRedirect(request.getContextPath() + "/MainController?action=lead_list");
                        return;
                    }

                    List<Interaction> list = new InteractionDAO().getByLeadId(leadId);
                    request.setAttribute("interactionList", list);
                    request.setAttribute("leadId", leadId);

                    request.getRequestDispatcher(INTERACTION_PAGE).forward(request, response);
                    return;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    private Timestamp parseDatetimeLocal(String s) {
        // input datetime-local: "2026-02-06T21:30"
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try {
            return Timestamp.valueOf(s.replace("T", " ") + ":00");
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
