/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.PropertyDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Property;

/**
 *
 * @author Hao
 */
@WebServlet(name = "PropertyController", urlPatterns = {"/PropertyController"})
public class PropertyController extends HttpServlet {

    private static final String PAGE = "/properties.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "property_list";

        try {
            PropertyDAO dao = new PropertyDAO();

            switch (action) {

                case "property_create": {
                    Property x = readFromRequest(request);
                    if (x == null) {
                        request.setAttribute("error", "Thiếu dữ liệu hoặc area_m2 không hợp lệ");
                        List<Property> list = dao.getAll();
                        request.setAttribute("propertiesList", list);
                        request.getRequestDispatcher(PAGE).forward(request, response);
                        return;
                    }

                    boolean ok = dao.create(x);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=property_list&created=" + (ok ? "1" : "0"));
                    return;
                }

                case "property_update": {
                    int id = parseIntSafe(request.getParameter("propertyId"), 0);
                    Property x = readFromRequest(request);
                    if (id <= 0 || x == null) {
                        response.sendRedirect(request.getContextPath()
                                + "/MainController?action=property_list&updated=0");
                        return;
                    }
                    x.setPropertyId(id);

                    boolean ok = dao.update(x);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=property_list&updated=" + (ok ? "1" : "0"));
                    return;
                }

                case "property_delete": {
                    int id = parseIntSafe(request.getParameter("propertyId"), 0);
                    boolean ok = (id > 0) && dao.delete(id);
                    response.sendRedirect(request.getContextPath()
                            + "/MainController?action=property_list&deleted=" + (ok ? "1" : "0"));
                    return;
                }

                case "property_list":
                default: {
                    List<Property> list = dao.getAll();
                    request.setAttribute("propertiesList", list);
                    request.getRequestDispatcher(PAGE).forward(request, response);
                    return;
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private Property readFromRequest(HttpServletRequest request) {
        String type = trimOrNull(request.getParameter("type"));
        String city = trimOrNull(request.getParameter("city"));
        String district = trimOrNull(request.getParameter("district"));
        String ward = trimOrNull(request.getParameter("ward"));
        String addressLine = trimOrNull(request.getParameter("addressLine"));
        String legalStatus = trimOrNull(request.getParameter("legalStatus"));
        String featuresJson = trimOrNull(request.getParameter("featuresJson"));

        BigDecimal areaM2 = parseBD(request.getParameter("areaM2")); // NOT NULL
        Integer bedrooms = parseIntOrNull(request.getParameter("bedrooms"));
        Integer bathrooms = parseIntOrNull(request.getParameter("bathrooms"));
        Integer yearBuilt = parseIntOrNull(request.getParameter("yearBuilt"));

        if (type == null || city == null || areaM2 == null) return null; // required

        Property x = new Property();
        x.setType(type);
        x.setCity(city);
        x.setDistrict(district);
        x.setWard(ward);
        x.setAddressLine(addressLine);
        x.setAreaM2(areaM2);
        x.setBedrooms(bedrooms);
        x.setBathrooms(bathrooms);
        x.setLegalStatus(legalStatus);
        x.setYearBuilt(yearBuilt);
        x.setFeaturesJson(featuresJson);
        return x;
    }

    private String trimOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    private Integer parseIntOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try { return Integer.valueOf(s); } catch (Exception e) { return null; }
    }

    private BigDecimal parseBD(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        try { return new BigDecimal(s); } catch (Exception e) { return null; }
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
