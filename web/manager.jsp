<%-- 
    Document   : manager
    Created on : Feb 6, 2026, 10:14:56 AM
    Author     : Hao
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <c:if test="${empty sessionScope.USER}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <h2>MANAGER</h2>
        <p>
            Hello <b>${sessionScope.USER.fullName}</b> |
            Role: <b>${sessionScope.USER.role}</b>
        </p>

        <c:if test="${not empty sessionScope.ERROR}">
            <p style="color:red;">${sessionScope.ERROR}</p>
            <c:remove var="ERROR" scope="session"/>
        </c:if>

        <ul>
            <li><a href="${pageContext.request.contextPath}/MainController?action=lead_list">Leads (View/Create)</a></li>
            <li><a href="${pageContext.request.contextPath}/MainController?action=listing_list">Listings</a></li>
            <li><a href="${pageContext.request.contextPath}/MainController?action=valuation_report">Valuation Report</a></li>

            <li><a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a></li>
        </ul>

    </body>
</html>
