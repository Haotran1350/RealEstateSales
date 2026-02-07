<%-- 
    Document   : listing
    Created on : Feb 6, 2026, 1:32:44 PM
    Author     : Hao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Listings</title>
    </head>
    <body>

        <h2>Listings</h2>

        <p>
            <a href="${pageContext.request.contextPath}/MainController?action=property_list">Properties</a> |
            <a href="${pageContext.request.contextPath}/MainController?action=lead_list">Leads</a> |
            <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
        </p>

        <c:if test="${not empty requestScope.error}">
            <p style="color:red;">${requestScope.error}</p>
        </c:if>

        <c:if test="${not empty param.created}">
            <p style="color:${param.created=='1'?'green':'red'};">Create: ${param.created=='1'?'OK':'FAILED'}</p>
        </c:if>
        <c:if test="${not empty param.updated}">
            <p style="color:${param.updated=='1'?'green':'red'};">Update: ${param.updated=='1'?'OK':'FAILED'}</p>
        </c:if>
        <c:if test="${not empty param.deleted}">
            <p style="color:${param.deleted=='1'?'green':'red'};">Delete: ${param.deleted=='1'?'OK':'FAILED'}</p>
        </c:if>

        <h3>Create Listing</h3>
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="listing_create"/>

            Property*:
            <select name="propertyId" required>
                <option value="">-- choose property --</option>
                <c:forEach var="p" items="${propertiesList}">
                    <option value="${p.propertyId}">
                        #${p.propertyId} - ${p.type} - ${p.city} ${p.district} ${p.ward} - ${p.addressLine}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>

            Seller name: <input type="text" name="sellerName"/> <br/><br/>
            Seller phone: <input type="text" name="sellerPhone"/> <br/><br/>

            List price* (DECIMAL): <input type="text" name="listPrice" placeholder="vd: 2800000000" required/> <br/><br/>

            Status:
            <select name="status">
                <option value="DRAFT">DRAFT</option>
                <option value="ACTIVE">ACTIVE</option>
                <option value="SOLD">SOLD</option>
                <option value="RENTED">RENTED</option>
            </select>
            <br/><br/>

            <button type="submit">Create</button>
        </form>

        <hr/>

        <c:if test="${empty listingsList}">
            <p style="color:red;">NO DATA TO VIEW</p>
        </c:if>

        <c:if test="${not empty listingsList}">
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th>listing_id</th>
                        <th>property</th>
                        <th>seller</th>
                        <th>agent_id</th>
                        <th>list_price</th>
                        <th>status</th>
                        <th>created_at</th>
                        <th>updated_at</th>
                        <th>closed_at</th>
                        <th>update status</th>
                        <th>delete</th>
                        <th>images</th>
                        <th>pdf</th>
                        <th>valuation</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="l" items="${listingsList}">
                        <tr>
                            <td>${l.listingId}</td>
                            <td>
                                #${l.propertyId} - ${l.propertyType}<br/>
                                ${l.city} ${l.district} ${l.ward}<br/>
                                ${l.addressLine}
                            </td>
                            <td>${l.sellerName}<br/>${l.sellerPhone}</td>
                            <td>${l.agentId}</td>
                            <td>${l.listPrice}</td>
                            <td>${l.status}</td>
                            <td>${l.createdAt}</td>
                            <td>${l.updatedAt}</td>
                            <td>${l.closedAt}</td>

                            <td>
                                <form action="${pageContext.request.contextPath}/MainController" method="post">
                                    <input type="hidden" name="action" value="listing_update_status"/>
                                    <input type="hidden" name="listingId" value="${l.listingId}"/>
                                    <select name="status">
                                        <option value="DRAFT"  ${l.status=='DRAFT'?'selected':''}>DRAFT</option>
                                        <option value="ACTIVE" ${l.status=='ACTIVE'?'selected':''}>ACTIVE</option>
                                        <option value="SOLD"   ${l.status=='SOLD'?'selected':''}>SOLD</option>
                                        <option value="RENTED" ${l.status=='RENTED'?'selected':''}>RENTED</option>
                                    </select>
                                    <button type="submit">Save</button>
                                </form>
                            </td>

                            <td>
                                <form action="${pageContext.request.contextPath}/MainController" method="post"
                                      onsubmit="return confirm('Delete listing_id=${l.listingId}?');">
                                    <input type="hidden" name="action" value="listing_delete"/>
                                    <input type="hidden" name="listingId" value="${l.listingId}"/>
                                    <button type="submit">Delete</button>
                                </form>
                            </td>

                            <td>
                                <a href="${pageContext.request.contextPath}/MainController?action=image_list&listingId=${l.listingId}">
                                    Manage Images
                                </a>
                            </td>

                            <td>
                                <a href="${pageContext.request.contextPath}/MainController?action=quote_pdf&listingId=${l.listingId}" target="_blank">
                                    PDF Quote
                                </a>
                            </td>
                            
                            <td>
                                <a href="${pageContext.request.contextPath}/MainController?action=valuation_generate&listingId=${l.listingId}"
                                   onclick="return confirm('Generate valuation for listing ${l.listingId}?');">
                                    Generate Valuation
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

    </body>
</html>