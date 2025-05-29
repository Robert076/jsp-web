<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>My URLs</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom mb-4">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/urls">URL Manager</a>
        <div class="d-flex">
            <a href="${pageContext.request.contextPath}/urls/popular" class="btn btn-outline-primary me-2">Popular URLs</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger">Logout</a>
        </div>
    </div>
</nav>
<div class="container">
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${empty isPopular}">
        <div class="card mb-4">
            <div class="card-header">Add New URL</div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/urls" id="addUrlForm">
                    <div class="mb-3">
                        <label for="url" class="form-label">URL</label>
                        <input type="url" class="form-control" id="url" name="url" required>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">Title</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description"></textarea>
                    </div>
                    <button type="submit" class="btn btn-success">Add URL</button>
                </form>
            </div>
        </div>
    </c:if>
    <div class="card">
        <div class="card-header">
            <c:choose>
                <c:when test="${isPopular}">Top Popular URLs</c:when>
                <c:otherwise>Your URLs</c:otherwise>
            </c:choose>
        </div>
        <div class="card-body p-0">
            <table class="table mb-0">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>URL</th>
                    <th>Description</th>
                    <th>Visits</th>
                    <c:if test="${empty isPopular}"><th>Actions</th></c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="url" items="${urls}">
                    <tr>
                        <td>${url.title}</td>
                        <td><a href="${url.url}" target="_blank">${url.url}</a></td>
                        <td>${url.description}</td>
                        <td>${url.visitCount}</td>
                        <c:if test="${empty isPopular}">
                            <td>
                                <a href="${pageContext.request.contextPath}/urls/edit/${url.id}" class="btn btn-sm btn-primary">Edit</a>
                                <form method="post" action="${pageContext.request.contextPath}/urls/delete/${url.id}" style="display:inline;" onsubmit="return confirmDelete();">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    function confirmDelete() {
        return confirm('Are you sure you want to delete this URL?');
    }
    document.getElementById('addUrlForm')?.addEventListener('submit', function(e) {
        const url = document.getElementById('url').value.trim();
        const title = document.getElementById('title').value.trim();
        if (!url || !title) {
            e.preventDefault();
            alert('URL and Title are required.');
        }
    });
</script>
</body>
</html> 