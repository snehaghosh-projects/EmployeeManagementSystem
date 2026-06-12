<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login — Employee Management System</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-wrap">
  <div class="login-card">
    <h1>&#128188; EMS Login</h1>
    <p class="sub">Employee Management System</p>

    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger">${error}</div>
    <% } %>
    <% if ("timeout".equals(request.getParameter("msg"))) { %>
      <div class="alert alert-info">Session expired. Please log in again.</div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login">
      <div class="form-group" style="margin-bottom:14px">
        <label for="username">Username</label>
        <input id="username" name="username" type="text"
               placeholder="e.g. admin or john.doe" required autofocus>
      </div>
      <div class="form-group" style="margin-bottom:20px">
        <label for="password">Password</label>
        <input id="password" name="password" type="password"
               placeholder="Enter password" required>
      </div>
      <button type="submit" class="btn btn-primary" style="width:100%;justify-content:center">
        Sign In
      </button>
    </form>

    <p style="margin-top:20px;font-size:12px;color:var(--muted);text-align:center">
      Demo: <strong>admin / admin123</strong> &nbsp;|&nbsp; <strong>john.doe / emp123</strong>
    </p>
  </div>
</div>
</body>
</html>
