/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author Hao
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static final String LOGIN_PAGE = "/login.jsp";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();              // /ProjectName/admin.jsp
        String ctx = req.getContextPath();             // /ProjectName
        String path = uri.substring(ctx.length());     // /admin.jsp

        // 1) Cho qua tài nguyên tĩnh + trang/login controller
        if (isPublic(path, req)) {
            chain.doFilter(request, response);
            return;
        }

        // 2) Check login
        HttpSession session = req.getSession(false);
        User user = (session == null) ? null : (User) session.getAttribute("USER");

        if (user == null) {
            resp.sendRedirect(ctx + LOGIN_PAGE);
            return;
        }

        // 3) Check role theo trang
        String role = user.getRole(); // "ADMIN"/"AGENT"/"MANAGER"

        if (path.equals("/admin.jsp") && !equalsRole(role, "ADMIN")) {
            session.setAttribute("ERROR", "Bạn không có quyền truy cập trang ADMIN");
            resp.sendRedirect(ctx + "/home.jsp");
            return;
        }

        if (path.equals("/agent.jsp") && !equalsRole(role, "AGENT")) {
            session.setAttribute("ERROR", "Bạn không có quyền truy cập trang AGENT");
            resp.sendRedirect(ctx + "/home.jsp");
            return;
        }

        if (path.equals("/manager.jsp") && !equalsRole(role, "MANAGER")) {
            session.setAttribute("ERROR", "Bạn không có quyền truy cập trang MANAGER");
            resp.sendRedirect(ctx + "/home.jsp");
            return;
        }

        // 4) Nếu ok thì cho đi tiếp
        chain.doFilter(request, response);
    }

    private boolean equalsRole(String role, String expected) {
        return role != null && role.equalsIgnoreCase(expected);
    }

    private boolean isPublic(String path, HttpServletRequest req) {
        // Cho phép file tĩnh
        if (path.startsWith("/assets/") || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
            return true;
        }

        // Cho phép trang login
        if (path.equals("/login.jsp")) return true;

        // Cho phép trực tiếp vào LoginController / LogoutController
        if (path.equals("/LoginController") || path.equals("/LogoutController")) return true;

        // Cho phép MainController gọi action login/logout (không cần session)
        if (path.equals("/MainController")) {
            String action = req.getParameter("action");
            return "login".equals(action) || "logout".equals(action);
        }

        return false;
    }
}