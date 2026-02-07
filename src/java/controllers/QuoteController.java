/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import dal.QuoteDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.QuoteData;

/**
 *
 * @author Hao
 */
@WebServlet(name = "QuoteController", urlPatterns = {"/QuoteController"})
public class QuoteController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "quote_pdf";
        }

        if (!"quote_pdf".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/home.jsp");
            return;
        }

        int listingId = parseIntSafe(request.getParameter("listingId"), 0);
        if (listingId <= 0) {
            response.sendRedirect(request.getContextPath() + "/MainController?action=listing_list");
            return;
        }

        try {
            QuoteData q = new QuoteDAO().getByListingId(listingId);
            if (q == null) {
                response.sendRedirect(request.getContextPath() + "/MainController?action=listing_list&pdf=0");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=quote_" + listingId + ".pdf");

            Document doc = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font h = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font n = new Font(Font.HELVETICA, 11);

            doc.add(new Paragraph("RealEstateCare - Quotation / Consultation", title));
            doc.add(new Paragraph("Listing ID: " + q.getListingId() + " | Status: " + safe(q.getListingStatus()), n));
            doc.add(new Paragraph(" ", n));

            // PROPERTY
            doc.add(new Paragraph("Property information", h));
            PdfPTable t1 = new PdfPTable(2);
            t1.setWidthPercentage(100);
            t1.addCell(cell("Property ID", true));
            t1.addCell(cell(String.valueOf(q.getPropertyId()), false));
            t1.addCell(cell("Type", true));
            t1.addCell(cell(safe(q.getType()), false));
            t1.addCell(cell("Address", true));
            t1.addCell(cell(buildAddress(q), false));
            t1.addCell(cell("Area (m2)", true));
            t1.addCell(cell(fmtBD(q.getAreaM2()), false));
            t1.addCell(cell("Bedrooms", true));
            t1.addCell(cell(fmtInt(q.getBedrooms()), false));
            t1.addCell(cell("Bathrooms", true));
            t1.addCell(cell(fmtInt(q.getBathrooms()), false));
            t1.addCell(cell("Legal", true));
            t1.addCell(cell(safe(q.getLegalStatus()), false));
            t1.addCell(cell("Year built", true));
            t1.addCell(cell(fmtInt(q.getYearBuilt()), false));
            doc.add(t1);

            doc.add(new Paragraph(" ", n));

            // LISTING
            doc.add(new Paragraph("Listing information", h));
            PdfPTable t2 = new PdfPTable(2);
            t2.setWidthPercentage(100);
            t2.addCell(cell("Seller", true));
            t2.addCell(cell(safe(q.getSellerName()), false));
            t2.addCell(cell("Seller phone", true));
            t2.addCell(cell(safe(q.getSellerPhone()), false));
            t2.addCell(cell("List price", true));
            t2.addCell(cell(fmtMoney(q.getListPrice()), false));
            doc.add(t2);

            doc.add(new Paragraph(" ", n));

            // VALUATION
            doc.add(new Paragraph("AI valuation (latest)", h));
            if (q.getpMed() == null && q.getpLow() == null && q.getpHigh() == null) {
                doc.add(new Paragraph("No valuation found for this listing.", n));
            } else {
                PdfPTable t3 = new PdfPTable(2);
                t3.setWidthPercentage(100);
                t3.addCell(cell("pLow", true));
                t3.addCell(cell(fmtMoney(q.getpLow()), false));
                t3.addCell(cell("pMed", true));
                t3.addCell(cell(fmtMoney(q.getpMed()), false));
                t3.addCell(cell("pHigh", true));
                t3.addCell(cell(fmtMoney(q.getpHigh()), false));
                t3.addCell(cell("Explanation", true));
                t3.addCell(cell(safe(q.getExplanationText()), false));
                doc.add(t3);
            }

            doc.add(new Paragraph(" ", n));

            // AGENT
            doc.add(new Paragraph("Agent information", h));
            doc.add(new Paragraph(
                    "Agent: " + safe(q.getAgentName())
                    + " | Phone: " + safe(q.getAgentPhone())
                    + " | Email: " + safe(q.getAgentEmail()), n));

            doc.close();

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private static int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    private static String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "-" : s.trim();
    }

    private static String fmtInt(Integer x) {
        return x == null ? "-" : String.valueOf(x);
    }

    private static String fmtBD(BigDecimal x) {
        return x == null ? "-" : x.toPlainString();
    }

    private static String fmtMoney(BigDecimal x) {
        if (x == null) {
            return "-";
        }
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format(x) + " VND";
    }

    private static String buildAddress(QuoteData q) {
        String a = safe(q.getAddressLine());
        String w = safe(q.getWard());
        String d = safe(q.getDistrict());
        String c = safe(q.getCity());
        return a + ", " + w + ", " + d + ", " + c;
    }

    private static PdfPCell cell(String text, boolean header) {
    Font f = header
            ? new Font(Font.HELVETICA, 11, Font.BOLD)
            : new Font(Font.HELVETICA, 11);

    PdfPCell c = new PdfPCell(new Phrase(text == null ? "-" : text, f));
    c.setPadding(6f);
    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
    if (header) c.setHorizontalAlignment(Element.ALIGN_LEFT);
    return c;
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
