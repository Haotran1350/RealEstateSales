<%-- 
    Document   : alerts
    Created on : Feb 6, 2026, 2:01:31 PM
    Author     : Hao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Alerts</title>
</head>
<body>

<h2>Alerts</h2>

<p>
  <a href="${pageContext.request.contextPath}/home.jsp">Home</a> |
  <a href="${pageContext.request.contextPath}/MainController?action=lead_list">Leads</a> |
  <a href="${pageContext.request.contextPath}/MainController?action=listing_list">Listings</a> |
  <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
</p>

<c:if test="${not empty param.ack}">
  <p style="color:${param.ack=='1'?'green':'red'};">ACK: ${param.ack=='1'?'OK':'FAILED'}</p>
</c:if>
<c:if test="${not empty param.done}">
  <p style="color:${param.done=='1'?'green':'red'};">DONE: ${param.done=='1'?'OK':'FAILED'}</p>
</c:if>

<c:if test="${empty alertsList}">
  <p style="color:red;">NO DATA TO VIEW</p>
</c:if>

<c:if test="${not empty alertsList}">
<table border="1" cellpadding="6" cellspacing="0">
  <thead>
    <tr>
      <th>alert_id</th>
      <th>agent</th>
      <th>lead_id</th>
      <th>listing_id</th>
      <th>type</th>
      <th>severity</th>
      <th>message</th>
      <th>status</th>
      <th>created_at</th>
      <th>resolved_at</th>
      <th>actions</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="a" items="${alertsList}">
      <tr>
        <td>${a.alertId}</td>
        <td>${a.agentId} - ${a.agentName}</td>
        <td>${a.leadId}</td>
        <td>${a.listingId}</td>
        <td>${a.type}</td>
        <td>${a.severity}</td>
        <td style="max-width:420px;">${a.message}</td>
        <td><b>${a.status}</b></td>
        <td>${a.createdAt}</td>
        <td>${a.resolvedAt}</td>
        <td>
          <c:if test="${a.status == 'OPEN'}">
            <a href="${pageContext.request.contextPath}/MainController?action=alert_ack&alertId=${a.alertId}">ACK</a>
            |
          </c:if>

          <c:if test="${a.status != 'DONE'}">
            <a href="${pageContext.request.contextPath}/MainController?action=alert_done&alertId=${a.alertId}">DONE</a>
          </c:if>

          <c:if test="${a.status == 'DONE'}">
            -
          </c:if>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
</c:if>

</body>
</html>
