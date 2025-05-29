<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card">
                <div class="card-header text-center">
                    <h4>Register</h4>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/register" id="registerForm">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username" required minlength="3">
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required minlength="6">
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirm Password</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required minlength="6">
                        </div>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <button type="submit" class="btn btn-primary w-100">Register</button>
                    </form>
                    <div class="mt-3 text-center">
                        <a href="${pageContext.request.contextPath}/login">Already have an account? Login</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        const username = document.getElementById('username').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        if (username.length < 3) {
            e.preventDefault();
            alert('Username must be at least 3 characters.');
        } else if (!email.match(/^\S+@\S+\.\S+$/)) {
            e.preventDefault();
            alert('Please enter a valid email address.');
        } else if (password.length < 6) {
            e.preventDefault();
            alert('Password must be at least 6 characters.');
        } else if (password !== confirmPassword) {
            e.preventDefault();
            alert('Passwords do not match.');
        }
    });
</script>
</body>
</html> 