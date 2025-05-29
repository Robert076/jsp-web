<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit URL</title>
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
    <div class="card">
        <div class="card-header">Edit URL</div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/urls/edit/${url.id}" id="editUrlForm">
                <div class="mb-3">
                    <label for="url" class="form-label">URL</label>
                    <input type="url" class="form-control" id="url" name="url" value="${url.url}" required>
                </div>
                <div class="mb-3">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" class="form-control" id="title" name="title" value="${url.title}" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description">${url.description}</textarea>
                </div>
                <button type="submit" class="btn btn-primary">Save Changes</button>
                <a href="${pageContext.request.contextPath}/urls" class="btn btn-secondary ms-2" onclick="return confirmCancel();">Cancel</a>
            </form>
        </div>
    </div>
</div>
<script>
    function confirmCancel() {
        return confirm('Are you sure you want to cancel editing? Unsaved changes will be lost.');
    }
    document.getElementById('editUrlForm').addEventListener('submit', function(e) {
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