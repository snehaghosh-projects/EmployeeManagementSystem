<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Error — EMS</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="login-wrap">
  <div class="login-card" style="text-align:center">
    <h1 style="font-size:48px;color:var(--danger)">&#9888;</h1>
    <h2>Something went wrong</h2>
    <p class="sub" style="margin-top:8px">
      <%= request.getAttribute("javax.servlet.error.message") != null
          ? request.getAttribute("javax.servlet.error.message")
          : "An unexpected error occurred." %>
    </p>
    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary" style="margin-top:20px">
      Go to Login
    </a>
  </div>
</div>
</body>
</html>
