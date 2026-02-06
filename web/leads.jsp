<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Leads</title>
    </head>
    <body>

        <h2>Leads</h2>

        <p>
            <a href="${pageContext.request.contextPath}/MainController?action=home">Home</a> |
            <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
        </p>

        <c:if test="${not empty param.created}">
            <c:choose>
                <c:when test="${param.created == '1'}">
                    <p style="color:green;">Create lead OK</p>
                </c:when>
                <c:otherwise>
                    <p style="color:red;">Create lead FAILED</p>
                </c:otherwise>
            </c:choose>
        </c:if>

        <c:if test="${not empty requestScope.error}">
            <p style="color:red;">${requestScope.error}</p>
        </c:if>

        <!-- Form Create Lead -->
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="lead_create"/>

            Full name: <input type="text" name="fullName" required /> <br/><br/>
            Phone: <input type="text" name="phone" required /> <br/><br/>
            Email: <input type="text" name="email" /> <br/><br/>

            Purpose:
            <select name="purpose">
                <option value="BUY">BUY</option>
                <option value="RENT">RENT</option>
            </select>
            <br/><br/>

            Budget min: <input type="text" name="budgetMin" placeholder="vd: 2000000000" /> <br/><br/>
            Budget max: <input type="text" name="budgetMax" placeholder="vd: 3500000000" /> <br/><br/>

            Location pref json:
            <input type="text" name="locationPrefJson" style="width:450px;"
                   placeholder='vd: {"city":"Hồ Chí Minh","district":["Thủ Đức"]}' />
            <br/><br/>

            <button type="submit">Create</button>
        </form>

        <hr/>

        <c:if test="${empty leadsList}">
            <p style="color:red;">NO DATA TO VIEW</p>
        </c:if>

        <c:if test="${not empty leadsList}">
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th>lead_id</th>
                        <th>full_name</th>
                        <th>phone</th>
                        <th>email</th>
                        <th>budget_min</th>
                        <th>budget_max</th>
                        <th>purpose</th>
                        <th>status</th>
                        <th>assigned_agent_id</th>
                        <th>actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="l" items="${leadsList}">
                        <tr>
                            <td>${l.leadId}</td>
                            <td>${l.fullName}</td>
                            <td>${l.phone}</td>
                            <td>${l.email}</td>
                            <td>${l.budgetMin}</td>
                            <td>${l.budgetMax}</td>
                            <td>${l.purpose}</td>
                            <td>${l.status}</td>
                            <td>${l.assignedAgentId}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/MainController?action=interaction_list&leadId=${l.leadId}">
                                    View Interactions
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

    </body>
</html>
