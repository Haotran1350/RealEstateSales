/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ListingDAO;
import dal.PropertyDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Listing;
import models.Property;
import models.User;

/**
 *
 * @author Hao
 */
@WebServlet(name = "ListingController", urlPatterns = {"/ListingController"})
public class ListingController extends HttpServlet {

    private static final String PAGE = "/listings.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "listing_list";
        }

        try {
            ListingDAO dao = new ListingDAO();

            switch (action) {

                case "listing_create": {
                    HttpSession session = request.getSession(false);
                    User u = (session == null) ? null : (User) session.getAttribute("USER");

                    Integer propertyId = parseIntOrNull(request.getParameter("propertyId"));
                    String sellerName = trimOrNull(request.getParameter("sellerName"));
                    String sellerPhone = trimOrNull(request.getParameter("sellerPhone"));
                    BigDecimal listPrice = parseBD(request.getParameter("listPrice"));
                    String status = trimOrNull(request.getParameter("status"));

                    if (propertyId == null || propertyId <= 0 || listPrice == null) {
                        request.setAttribute("error", "propertyId và listPrice là bắt buộc (listPrice > 0).");
                        loadPageData(request, dao);
                        request.getRequestDispatcher(PAGE).forward(request, response);
                        return;
                    }

                    Listing x = new Listing();
                    x.setPropertyId(propertyId);
                    x.setSellerName(sellerName);
                    x.setSellerPhone(sellerPhone);
                    x.setListPrice(listPrice);
                    x.setStatus((status == null) ? "DRAFT" : status);

                    // auto gán agent_id nếu user đăng nhập là AGENT
                    if (u != null && u.getUserId() > 0 && "AGENT".equalsIgnoreCase(u.getRole())) {
                        x.setAgentId(u.getUserId());
                    }

                    boolean ok = dao.create(x);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=listing_list&created=" + (ok ? "1" : "0"));
                    return;
                }

                case "listing_update_status": {
                    int listingId = parseIntSafe(request.getParameter("listingId"), 0);
                    String newStatus = trimOrNull(request.getParameter("status"));

                    if (listingId <= 0 || newStatus == null) {
                        response.sendRedirect(request.getContextPath()
                                + "/MainController?action=listing_list&updated=0");
                        return;
                    }

                    boolean ok = dao.updateStatus(listingId, newStatus);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=listing_list&updated=" + (ok ? "1" : "0"));
                    return;
                }

                case "listing_delete": {
                    int listingId = parseIntSafe(request.getParameter("listingId"), 0);
                    boolean ok = (listingId > 0) && dao.delete(listingId);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=listing_list&deleted=" + (ok ? "1" : "0"));
                    return;
                }

                case "listing_list":
                default: {
                    loadPageData(request, dao);
                    request.getRequestDispatcher(PAGE).forward(request, response);
                    return;
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void loadPageData(HttpServletRequest request, ListingDAO dao) throws Exception {
        List<Listing> list = dao.getAll();
        request.setAttribute("listingsList", list);

        // dropdown properties
        List<Property> props = new PropertyDAO().getAll();
        request.setAttribute("propertiesList", props);
    }

    private String trimOrNull(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    private Integer parseIntOrNull(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        if (s.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal parseBD(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        if (s.isEmpty()) {
            return null;
        }
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
