<%-- 
    Document   : agent
    Created on : Feb 6, 2026, 10:16:29 AM
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

        <h2>AGENT</h2>
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

            <!-- Tí mày làm Interaction theo Lead -->
            <li><a href="${pageContext.request.contextPath}/MainController?action=interaction_today">My Follow-ups Today</a></li>

            <!-- Listing/Property tùy đề -->
            <li><a href="${pageContext.request.contextPath}/MainController?action=listing_list">My Listings</a></li>

            <li><a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a></li>
        </ul>

    </body>
</html>
