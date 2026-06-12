<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<%@ page import="com.ems.dao.EmployeeDAO" %>
<%@ page import="com.ems.model.User" %>
<%@ page import="com.ems.model.Employee" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    User currentUser = (User) session.getAttribute("user");
    EmployeeDAO dao = new EmployeeDAO();
    Employee employee = dao.getEmployeeByUserId(currentUser.getUserId());
    request.setAttribute("employee", employee);
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>My Profile — EMS</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
  <span class="brand">&#128188; EMS</span>
  <nav>
    <a href="${pageContext.request.contextPath}/payslip">My Payslip</a>
    <a href="${pageContext.request.contextPath}/logout">Logout (${sessionScope.user.username})</a>
  </nav>
</div>

<div class="page">
  <h2 class="page-title">My Profile</h2>

  <c:choose>
    <c:when test="${empty employee}">
      <div class="alert alert-info">No employee profile found for your account. Contact HR.</div>
    </c:when>
    <c:otherwise>
      <div class="card">
        <div style="display:flex;align-items:center;gap:16px;margin-bottom:24px">
          <div style="width:64px;height:64px;border-radius:50%;background:var(--primary);
                      color:#fff;display:flex;align-items:center;justify-content:center;
                      font-size:24px;font-weight:700">
            ${employee.firstName[0]}${employee.lastName[0]}
          </div>
          <div>
            <h3 style="font-size:20px">${employee.fullName}</h3>
            <span class="badge badge-employee">${employee.designation}</span>
          </div>
        </div>

        <div class="detail-grid">
          <div class="detail-item"><label>Employee ID</label><span>#${employee.empId}</span></div>
          <div class="detail-item"><label>Email</label><span>${employee.email}</span></div>
          <div class="detail-item"><label>Phone</label><span>${employee.phone}</span></div>
          <div class="detail-item"><label>Department</label><span>${employee.deptName}</span></div>
          <div class="detail-item"><label>Designation</label><span>${employee.designation}</span></div>
          <div class="detail-item">
            <label>Salary</label>
            <span>&#8377; <fmt:formatNumber value="${employee.salary}" pattern="#,##0.00"/></span>
          </div>
          <div class="detail-item"><label>Hire Date</label><span>${employee.hireDate}</span></div>
          <div class="detail-item"><label>Address</label><span>${employee.address}</span></div>
        </div>

        <div class="form-actions" style="margin-top:24px">
          <a href="${pageContext.request.contextPath}/payslip" class="btn btn-primary">
            &#128196; View My Payslip
          </a>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
