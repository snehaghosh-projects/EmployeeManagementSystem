<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<%
    if (session.getAttribute("user") == null ||
        !((com.ems.model.User)session.getAttribute("user")).isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login"); return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${employee.fullName} — EMS</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar">
  <span class="brand">&#128188; EMS Admin</span>
  <nav>
    <a href="${pageContext.request.contextPath}/employee">&#8592; Back to List</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </nav>
</div>

<div class="page">
  <h2 class="page-title">Employee Details</h2>
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
      <a href="${pageContext.request.contextPath}/employee?action=edit&id=${employee.empId}"
         class="btn btn-warning">Edit</a>
      <a href="${pageContext.request.contextPath}/payslip?id=${employee.empId}"
         class="btn btn-outline">View Payslip</a>

      <!-- Send payslip email -->
      <form method="post" action="${pageContext.request.contextPath}/payslip" style="display:inline">
        <input type="hidden" name="empId" value="${employee.empId}">
        <button type="submit" class="btn btn-primary">&#9993; Send Payslip Email</button>
      </form>

      <a href="${pageContext.request.contextPath}/employee?action=delete&id=${employee.empId}"
         class="btn btn-danger"
         onclick="return confirm('Delete ${employee.fullName}?')">Delete</a>
    </div>
  </div>
</div>
</body>
</html>
