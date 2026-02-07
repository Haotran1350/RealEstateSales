<%-- 
    Document   : images
    Created on : Feb 8, 2026, 12:46:58 AM
    Author     : Hao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listing Images</title>
</head>
<body>

<h2>Listing Images</h2>

<p>
    <a href="${pageContext.request.contextPath}/MainController?action=listing_list">Back to Listings</a> |
    <a href="${pageContext.request.contextPath}/MainController?action=logout">Logout</a>
</p>

<c:if test="${not empty param.up}">
    <p style="color:${param.up=='1'?'green':'red'};">Upload: ${param.up=='1'?'OK':'FAILED'}</p>
</c:if>
<c:if test="${not empty param.del}">
    <p style="color:${param.del=='1'?'green':'red'};">Delete: ${param.del=='1'?'OK':'FAILED'}</p>
</c:if>
<c:if test="${param.type=='0'}">
    <p style="color:red;">Chỉ cho phép png/jpg/jpeg/gif/webp</p>
</c:if>

<h3>Upload new image</h3>

<!-- FIX: POST thẳng ImageController -->
<form action="${pageContext.request.contextPath}/ImageController" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="image_upload"/>
    <input type="hidden" name="listingId" value="${listingId}"/>

    Sort order: <input type="text" name="sortOrder" value="0"/> <br/><br/>

    <!-- name="file" phải khớp getPart("file") -->
    File: <input type="file" name="file" accept="image/*" required/> <br/><br/>

    <button type="submit">Upload</button>
</form>

<hr/>

<c:if test="${empty imagesList}">
    <p style="color:red;">NO IMAGES</p>
</c:if>

<c:if test="${not empty imagesList}">
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
        <tr>
            <th>image_id</th>
            <th>preview</th>
            <th>url_or_path</th>
            <th>sort_order</th>
            <th>created_at</th>
            <th>delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="img" items="${imagesList}">
            <tr>
                <td>${img.imageId}</td>
                <td>
                    <img src="${pageContext.request.contextPath}${img.urlOrPath}" width="120" />
                </td>
                <td>${img.urlOrPath}</td>
                <td>${img.sortOrder}</td>
                <td>${img.createdAt}</td>
                <td>
                   
                    <form action="${pageContext.request.contextPath}/ImageController" method="post"
                          onsubmit="return confirm('Delete image_id=${img.imageId}?');">
                        <input type="hidden" name="action" value="image_delete"/>
                        <input type="hidden" name="listingId" value="${listingId}"/>
                        <input type="hidden" name="imageId" value="${img.imageId}"/>
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
