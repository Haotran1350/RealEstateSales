<%-- 
    Document   : lead_scores
    Created on : Feb 8, 2026, 10:15:20 AM
    Author     : Hao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lead Scores</title>
    </head>
    <body>

        <h2>Lead Scores - leadId = ${leadId}</h2>

        <p>
            <a href="${pageContext.request.contextPath}/MainController?action=lead_list">Back to Leads</a> |
            <a href="${pageContext.request.contextPath}/MainController?action=alert_list">Alerts</a> |
            <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
        </p>

        <c:if test="${not empty param.gen}">
            <p style="color:${param.gen=='1'?'green':'red'};">Generate score: ${param.gen=='1'?'OK':'FAILED'}</p>
        </c:if>
        <c:if test="${not empty param.nba}">
            <p style="color:${param.nba=='1'?'green':'red'};">Generate NBA alert: ${param.nba=='1'?'OK':'FAILED'}</p>
        </c:if>

        <h3>Generate score</h3>
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="leadscore_generate"/>
            <input type="hidden" name="leadId" value="${leadId}"/>

            Horizon:
            <select name="horizonDays">
                <option value="7">7 days</option>
                <option value="14">14 days</option>
                <option value="30">30 days</option>
            </select>

            <button type="submit">Generate</button>
        </form>

        <form action="${pageContext.request.contextPath}/MainController" method="post" style="margin-top:10px;">
            <input type="hidden" name="action" value="nba_generate"/>
            <input type="hidden" name="leadId" value="${leadId}"/>
            <button type="submit">Generate Next Best Action (Alert)</button>
        </form>

        <hr/>

        <c:if test="${empty scoresList}">
            <p style="color:red;">NO SCORES</p>
        </c:if>

        <c:if test="${not empty scoresList}">
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th>score_id</th>
                        <th>horizon_days</th>
                        <th>probability</th>
                        <th>bucket</th>
                        <th>explanation</th>
                        <th>model_version</th>
                        <th>created_at</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${scoresList}">
                        <tr>
                            <td>${s.scoreId}</td>
                            <td>${s.horizonDays}</td>
                            <td>${s.probability}</td>
                            <td>${s.bucket}</td>
                            <td>${s.explanationText}</td>
                            <td>${s.modelVersion}</td>
                            <td>${s.createdAt}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

    </body>
</html>