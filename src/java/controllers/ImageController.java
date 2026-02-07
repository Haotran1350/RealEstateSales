/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.ListingImageDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import models.ListingImage;
import models.User;

/**
 *
 * @author Hao
 */
@WebServlet(name = "ImageController", urlPatterns = {"/ImageController"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 10L * 1024 * 1024, // 10MB
        maxRequestSize = 20L * 1024 * 1024 // 20MB
)
public class ImageController extends HttpServlet {

    private static final String PAGE = "/images.jsp";

   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "image_list";

        // login check
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
            ListingImageDAO dao = new ListingImageDAO();

            switch (action) {
                case "image_upload": {
                    int sortOrder = parseIntSafe(request.getParameter("sortOrder"), 0);

                    Part filePart = request.getPart("file"); // name="file"
                    if (filePart == null || filePart.getSize() == 0) {
                        response.sendRedirect(request.getContextPath()
                                + "/ImageController?action=image_list&listingId=" + listingId + "&up=0");
                        return;
                    }

                    String submitted = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String ext = getExt(submitted);

                    if (!isAllowedImage(ext)) {
                        response.sendRedirect(request.getContextPath()
                                + "/ImageController?action=image_list&listingId=" + listingId + "&type=0");
                        return;
                    }

                    // lưu vào deploy folder: /images/listings  (web/images/listings)
                    String uploadDirPath = getServletContext().getRealPath("/images/listings");
                    if (uploadDirPath == null) {
                        response.sendRedirect(request.getContextPath()
                                + "/ImageController?action=image_list&listingId=" + listingId + "&up=0");
                        return;
                    }

                    File uploadDir = new File(uploadDirPath);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    String savedName = "l" + listingId + "_" + System.currentTimeMillis() + ext;
                    File savedFile = new File(uploadDir, savedName);

                    filePart.write(savedFile.getAbsolutePath());

                    String urlOrPath = "/images/listings/" + savedName;

                    boolean ok = dao.create(listingId, urlOrPath, sortOrder);

                    response.sendRedirect(request.getContextPath()
                            + "/ImageController?action=image_list&listingId=" + listingId + "&up=" + (ok ? "1" : "0"));
                    return;
                }

                case "image_delete": {
                    int imageId = parseIntSafe(request.getParameter("imageId"), 0);
                    if (imageId <= 0) {
                        response.sendRedirect(request.getContextPath()
                                + "/ImageController?action=image_list&listingId=" + listingId + "&del=0");
                        return;
                    }

                    // lấy path từ DB (đúng DAO của mày)
                    String urlOrPath = dao.getPathByImageId(imageId);

                    boolean ok = dao.delete(imageId);

                    // xóa file best-effort
                    if (ok && urlOrPath != null && !urlOrPath.trim().isEmpty()) {
                        String real = getServletContext().getRealPath(urlOrPath);
                        if (real != null) {
                            File f = new File(real);
                            if (f.exists()) { try { f.delete(); } catch (Exception ignore) {} }
                        }
                    }

                    response.sendRedirect(request.getContextPath()
                            + "/ImageController?action=image_list&listingId=" + listingId + "&del=" + (ok ? "1" : "0"));
                    return;
                }

                case "image_list":
                default: {
                    List<ListingImage> list = dao.getByListingId(listingId);
                    request.setAttribute("listingId", listingId);
                    request.setAttribute("imagesList", list);
                    request.getRequestDispatcher(PAGE).forward(request, response);
                    return;
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private int parseIntSafe(String s, int def) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }

    private String getExt(String name) {
        if (name == null) return "";
        int dot = name.lastIndexOf('.');
        if (dot < 0) return "";
        return name.substring(dot).toLowerCase();
    }

    private boolean isAllowedImage(String ext) {
        return ".jpg".equals(ext) || ".jpeg".equals(ext) || ".png".equals(ext)
                || ".gif".equals(ext) || ".webp".equals(ext);
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
