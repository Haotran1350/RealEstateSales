<%-- 
    Document   : home
    Created on : Feb 6, 2026, 10:40:44 AM
    Author     : Hao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h2>Home</h2>

    <c:if test="${not empty sessionScope.ERROR}">
        <p style="color:red;">${sessionScope.ERROR}</p>
        <c:remove var="ERROR" scope="session"/>
    </c:if>

    <c:choose>
        <c:when test="${empty sessionScope.USER}">
            <p>Bạn chưa đăng nhập.</p>
            <a href="${pageContext.request.contextPath}/login.jsp">Go to Login</a>
        </c:when>

        <c:otherwise>
            <p>
                Xin chào,
                <b>${sessionScope.USER.fullName}</b>
                (role: <b>${sessionScope.USER.role}</b>)
            </p>

            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/MainController?action=lead_list">Leads (View/Create)</a>
                </li>
               
                <li><a href="${pageContext.request.contextPath}/MainController?action=property_list">Properties</a></li>
                <li><a href="${pageContext.request.contextPath}/MainController?action=listing_list">Listings</a></li>
                <li><a href="${pageContext.request.contextPath}/MainController?action=alert_list">Alerts</a></li>
                <li>
                    <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
                </li>
                
            </ul>
        </c:otherwise>
    </c:choose>

</body>
</html>
