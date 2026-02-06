<%-- 
    Document   : properties
    Created on : Feb 6, 2026, 1:26:27 PM
    Author     : Hao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Properties</title>
    </head>
    <body>

        <h2>Properties</h2>

        <p>
            <a href="${pageContext.request.contextPath}/MainController?action=lead_list">Leads</a> |
            <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
        </p>

        <c:if test="${not empty param.created}">
            <p style="color:${param.created=='1'?'green':'red'};">
                Create: ${param.created=='1'?'OK':'FAILED'}
            </p>
        </c:if>
        <c:if test="${not empty param.updated}">
            <p style="color:${param.updated=='1'?'green':'red'};">
                Update: ${param.updated=='1'?'OK':'FAILED'}
            </p>
        </c:if>
        <c:if test="${not empty param.deleted}">
            <p style="color:${param.deleted=='1'?'green':'red'};">
                Delete: ${param.deleted=='1'?'OK':'FAILED'}
            </p>
        </c:if>

        <c:if test="${not empty requestScope.error}">
            <p style="color:red;">${requestScope.error}</p>
        </c:if>

        <h3>Create Property</h3>
        <form action="${pageContext.request.contextPath}/MainController" method="post">
            <input type="hidden" name="action" value="property_create"/>

            Type*: <input type="text" name="type" placeholder="APARTMENT/HOUSE/LAND" required/> <br/><br/>
            City*: <input type="text" name="city" required/> <br/><br/>
            District: <input type="text" name="district"/> <br/><br/>
            Ward: <input type="text" name="ward"/> <br/><br/>
            Address line: <input type="text" name="addressLine" style="width:420px;"/> <br/><br/>

            Area m2*: <input type="text" name="areaM2" placeholder="vd: 55.5" required/> <br/><br/>
            Bedrooms: <input type="text" name="bedrooms" placeholder="vd: 2"/> <br/><br/>
            Bathrooms: <input type="text" name="bathrooms" placeholder="vd: 1"/> <br/><br/>

            Legal status: <input type="text" name="legalStatus" placeholder="RED_BOOK/PINK_BOOK"/> <br/><br/>
            Year built: <input type="text" name="yearBuilt" placeholder="vd: 2018"/> <br/><br/>
            Features json: <input type="text" name="featuresJson" style="width:420px;" placeholder='{"balcony":true}'/> <br/><br/>

            <button type="submit">Create</button>
        </form>

        <hr/>

        <c:if test="${empty propertiesList}">
            <p style="color:red;">NO DATA TO VIEW</p>
        </c:if>

        <c:if test="${not empty propertiesList}">
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th>property_id</th>
                        <th>type</th>
                        <th>city</th>
                        <th>district</th>
                        <th>ward</th>
                        <th>address_line</th>
                        <th>area_m2</th>
                        <th>bedrooms</th>
                        <th>bathrooms</th>
                        <th>legal_status</th>
                        <th>year_built</th>
                        <th>features_json</th>
                        <th>created_at</th>
                        <th>updated_at</th>
                        <th>update</th>
                        <th>delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${propertiesList}">
                        <tr>
                    <form action="${pageContext.request.contextPath}/MainController" method="post">
                        <input type="hidden" name="action" value="property_update"/>
                        <input type="hidden" name="propertyId" value="${p.propertyId}"/>

                        <td>${p.propertyId}</td>
                        <td><input type="text" name="type" value="${p.type}" style="width:110px;"/></td>
                        <td><input type="text" name="city" value="${p.city}" style="width:120px;"/></td>
                        <td><input type="text" name="district" value="${p.district}" style="width:120px;"/></td>
                        <td><input type="text" name="ward" value="${p.ward}" style="width:120px;"/></td>
                        <td><input type="text" name="addressLine" value="${p.addressLine}" style="width:220px;"/></td>
                        <td><input type="text" name="areaM2" value="${p.areaM2}" style="width:80px;"/></td>
                        <td><input type="text" name="bedrooms" value="${p.bedrooms}" style="width:55px;"/></td>
                        <td><input type="text" name="bathrooms" value="${p.bathrooms}" style="width:55px;"/></td>
                        <td><input type="text" name="legalStatus" value="${p.legalStatus}" style="width:110px;"/></td>
                        <td><input type="text" name="yearBuilt" value="${p.yearBuilt}" style="width:70px;"/></td>
                        <td><input type="text" name="featuresJson" value="${p.featuresJson}" style="width:220px;"/></td>

                        <td>${p.createdAt}</td>
                        <td>${p.updatedAt}</td>

                        <td><button type="submit">Update</button></td>
                    </form>

                    <td>
                        <form action="${pageContext.request.contextPath}/MainController" method="post"
                              onsubmit="return confirm('Delete property_id=${p.propertyId}?');">
                            <input type="hidden" name="action" value="property_delete"/>
                            <input type="hidden" name="propertyId" value="${p.propertyId}"/>
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

</body>
</html>
