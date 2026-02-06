<%-- 
    Document   : interactions
    Created on : Feb 6, 2026, 12:24:17 PM
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

        <h2>Interactions for leadId = ${leadId}</h2>

        <p>
            <a href="${pageContext.request.contextPath}/MainController?action=lead_list">Back to Leads</a>
        </p>

        <c:if test="${not empty param.created}">
            <c:choose>
                <c:when test="${param.created == '1'}">
                    <p style="color:green;">Add interaction OK</p>
                </c:when>
                <c:otherwise>
                    <p style="color:red;">Add interaction FAILED</p>
                </c:otherwise>
            </c:choose>
        </c:if>

        <c:if test="${not empty requestScope.error}">
            <p style="color:red;">${requestScope.error}</p>
        </c:if>

        <!-- Form Add Interaction -->
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="interaction_create"/>
            <input type="hidden" name="leadId" value="${leadId}"/>

            Channel:
            <select name="channel">
                <option value="CALL">CALL</option>
                <option value="SMS">SMS</option>
                <option value="EMAIL">EMAIL</option>
                <option value="MEET">MEET</option>
                <option value="OTHER">OTHER</option>
            </select>
            <br/><br/>

            Content: <br/>
            <textarea name="content" rows="4" cols="60" required></textarea>
            <br/><br/>

            Outcome: <input type="text" name="outcome" />
            <br/><br/>

            Next action at:
            <input type="datetime-local" name="nextActionAt" />
            <br/><br/>

            <button type="submit">Add Interaction</button>
        </form>

        <hr/>

        <c:if test="${empty interactionList}">
            <p style="color:red;">NO DATA TO VIEW</p>
        </c:if>

        <c:if test="${not empty interactionList}">
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th>interaction_id</th>
                        <th>agent_id</th>
                        <th>channel</th>
                        <th>content</th>
                        <th>ts</th>
                        <th>outcome</th>
                        <th>next_action_at</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" items="${interactionList}">
                        <tr>
                            <td>${i.interactionId}</td>
                            <td>${i.agentId}</td>
                            <td>${i.channel}</td>
                            <td>${i.content}</td>
                            <td>${i.ts}</td>
                            <td>${i.outcome}</td>
                            <td>${i.nextActionAt}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

    </body>
</html>
