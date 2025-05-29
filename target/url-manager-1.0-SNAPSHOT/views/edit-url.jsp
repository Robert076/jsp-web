<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit URL</title>
</head>
<body>
    <h2>Edit URL</h2>
    
    <c:if test="${not empty error}">
        <div>${error}</div>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/urls/edit/${url.id}" method="post">
        <input type="hidden" name="title" value="${url.title}">
        <div>
            <label for="url">URL:</label>
            <input type="text" id="url" name="url" value="${url.url}" required>
        </div>
        
        <div>
            <label for="description">Description:</label>
            <input type="text" id="description" name="description" value="${url.description}" required>
        </div>
        
        <button type="submit">Update URL</button>
    </form>
    
    <p><a href="${pageContext.request.contextPath}/urls">Back to URLs</a></p>
</body>
</html> 