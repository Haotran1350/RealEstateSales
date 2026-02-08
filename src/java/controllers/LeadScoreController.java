/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.AlertDAO;
import dal.LeadScoreDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.LeadScore;
import models.User;

/**
 *
 * @author Hao
 */
@WebServlet(name = "LeadScoreController", urlPatterns = {"/LeadScoreController"})
public class LeadScoreController extends HttpServlet {

    private static final String PAGE = "/lead_scores.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "leadscore_list";
        }

        HttpSession session = request.getSession(false);
        User u = (session == null) ? null : (User) session.getAttribute("USER");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int leadId = parseIntSafe(request.getParameter("leadId"), 0);
        if (leadId <= 0) {
            response.sendRedirect(request.getContextPath() + "/MainController?action=lead_list");
            return;
        }

        try {
            LeadScoreDAO dao = new LeadScoreDAO();

            switch (action) {
                case "leadscore_generate": {
                    int horizon = parseIntSafe(request.getParameter("horizonDays"), 7);
                    if (horizon != 7 && horizon != 14 && horizon != 30) {
                        horizon = 7;
                    }

                    // demo: random probability 0..1 (4 digits)
                    BigDecimal p = BigDecimal.valueOf(new Random().nextDouble())
                            .setScale(4, RoundingMode.HALF_UP);

                    String bucket;
                    if (p.compareTo(new BigDecimal("0.70")) >= 0) {
                        bucket = "HOT";
                    } else if (p.compareTo(new BigDecimal("0.40")) >= 0) {
                        bucket = "WARM";
                    } else {
                        bucket = "COLD";
                    }

                    LeadScore s = new LeadScore();
                    s.setLeadId(leadId);
                    s.setHorizonDays(horizon);
                    s.setProbability(p);
                    s.setBucket(bucket);
                    s.setExplanationText("Demo score: dựa trên hành vi + ngân sách (giả lập).");
                    String mv = dao.getLatestModelVersion("LEAD_SCORING"); // dùng hàm trong LeadScoreDAO
                    if (mv == null) {
                        String ctx = request.getContextPath();
                        response.sendRedirect(ctx + "/MainController?action=leadscore_list&leadId=" + leadId + "&gen=nomodel");
                        return;
                    }
                    s.setModelVersion(mv);

                    boolean ok = dao.create(s);

                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=leadscore_list&leadId=" + leadId
                            + "&gen=" + (ok ? "1" : "0"));
                    return;
                }

                case "nba_generate": {
                    // Lấy score mới nhất: lấy list top 1 từ dao.getByLeadId
                    List<LeadScore> scores = dao.getByLeadId(leadId);
                    if (scores.isEmpty()) {
                        response.sendRedirect(request.getContextPath()
                                + "/MainController?action=leadscore_list&leadId=" + leadId + "&nba=0");
                        return;
                    }
                    LeadScore latest = scores.get(0);

                    Integer assignedAgentId = dao.getAssignedAgentId(leadId);
                    int agentId = (assignedAgentId != null) ? assignedAgentId : u.getUserId();

                    String sev, msg;
                    switch (latest.getBucket()) {
                        case "HOT":
                            sev = "HIGH";
                            msg = "NEXT BEST ACTION: Gọi lại trong 24h + chốt lịch xem nhà.";
                            break;
                        case "WARM":
                            sev = "MED";
                            msg = "NEXT BEST ACTION: Gửi 3 listing phù hợp + hỏi thêm nhu cầu.";
                            break;
                        default:
                            sev = "LOW";
                            msg = "NEXT BEST ACTION: Follow-up sau 7 ngày (khách còn đang cân nhắc).";
                            break;
                    }

                    boolean ok = new AlertDAO().createNBA(agentId, leadId, sev, msg);

                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=leadscore_list&leadId=" + leadId
                            + "&nba=" + (ok ? "1" : "0"));
                    return;
                }

                case "leadscore_list":
                default: {
                    List<LeadScore> list = dao.getByLeadId(leadId);
                    request.setAttribute("leadId", leadId);
                    request.setAttribute("scoresList", list);
                    request.getRequestDispatcher(PAGE).forward(request, response);
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
