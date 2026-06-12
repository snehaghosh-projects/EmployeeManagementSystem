<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    com.ems.model.User u = (com.ems.model.User) session.getAttribute("user");
    if (!u.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/profile");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Admin Dashboard — EMS</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- Navbar -->
<div class="navbar">
  <span class="brand">&#128188; EMS Admin</span>
  <nav>
    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp">Dashboard</a>
    <a href="${pageContext.request.contextPath}/employee?action=add">+ Add Employee</a>
    <a href="${pageContext.request.contextPath}/logout">Logout (${sessionScope.user.username})</a>
  </nav>
</div>

<div class="page">
  <h2 class="page-title">Dashboard</h2>

  <!-- Alerts -->
  <c:if test="${param.msg == 'added'}">
    <div class="alert alert-success">&#10003; Employee added successfully and welcome email sent.</div>
  </c:if>
  <c:if test="${param.msg == 'updated'}">
    <div class="alert alert-success">&#10003; Employee record updated.</div>
  </c:if>
  <c:if test="${param.msg == 'deleted'}">
    <div class="alert alert-danger">Employee deleted.</div>
  </c:if>
  <c:if test="${param.msg == 'payslip_sent'}">
    <div class="alert alert-success">&#9993; Payslip email sent.</div>
  </c:if>

  <!-- Stat cards -->
  <div class="stats">
    <div class="stat-card">
      <div class="stat-val">${totalCount}</div>
      <div class="stat-label">Total Employees</div>
    </div>
    <div class="stat-card">
      <div class="stat-val">${totalPages}</div>
      <div class="stat-label">Pages</div>
    </div>
    <div class="stat-card">
      <div class="stat-val">${pageSize}</div>
      <div class="stat-label">Per Page</div>
    </div>
  </div>

  <!-- Toolbar -->
  <div class="toolbar">
    <form method="get" action="${pageContext.request.contextPath}/employee" class="search-box">
      <input name="search" type="text" placeholder="Search name, dept, email…"
             value="${param.search}" style="width:240px">
      <button type="submit" class="btn btn-primary btn-sm">Search</button>
    </form>
    <a href="${pageContext.request.contextPath}/employee" class="btn btn-outline btn-sm">Clear</a>
    <a href="${pageContext.request.contextPath}/employee?action=add" class="btn btn-success btn-sm">+ Add Employee</a>
  </div>

  <!-- Table -->
  <div class="card" style="padding:0">
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th><a href="?sort=name&dir=<c:choose><c:when test='${sort=="name" && dir=="asc"}'>desc</c:when><c:otherwise>asc</c:otherwise></c:choose>&page=${currentPage}">Name &#8597;</a></th>
            <th>Email</th>
            <th><a href="?sort=department&dir=<c:choose><c:when test='${sort=="department" && dir=="asc"}'>desc</c:when><c:otherwise>asc</c:otherwise></c:choose>&page=${currentPage}">Department &#8597;</a></th>
            <th><a href="?sort=designation&dir=<c:choose><c:when test='${sort=="designation" && dir=="asc"}'>desc</c:when><c:otherwise>asc</c:otherwise></c:choose>&page=${currentPage}">Designation &#8597;</a></th>
            <th><a href="?sort=salary&dir=<c:choose><c:when test='${sort=="salary" && dir=="asc"}'>desc</c:when><c:otherwise>asc</c:otherwise></c:choose>&page=${currentPage}">Salary &#8597;</a></th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${empty employees}">
              <tr><td colspan="7" style="text-align:center;color:var(--muted);padding:32px">No employees found.</td></tr>
            </c:when>
            <c:otherwise>
              <c:forEach var="emp" items="${employees}" varStatus="i">
              <tr>
                <td>${(currentPage-1)*pageSize + i.index + 1}</td>
                <td><strong>${emp.fullName}</strong></td>
                <td>${emp.email}</td>
                <td>${emp.deptName}</td>
                <td>${emp.designation}</td>
                <td>&#8377; <fmt:formatNumber value="${emp.salary}" pattern="#,##0.00"/></td>
                <td style="white-space:nowrap">
                  <a href="${pageContext.request.contextPath}/employee?action=view&id=${emp.empId}" class="btn btn-outline btn-sm">View</a>
                  <a href="${pageContext.request.contextPath}/employee?action=edit&id=${emp.empId}" class="btn btn-warning btn-sm">Edit</a>
                  <a href="${pageContext.request.contextPath}/payslip?id=${emp.empId}" class="btn btn-outline btn-sm">Payslip</a>
                  <a href="${pageContext.request.contextPath}/employee?action=delete&id=${emp.empId}"
                     class="btn btn-danger btn-sm"
                     onclick="return confirm('Delete ${emp.fullName}?')">Delete</a>
                </td>
              </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Pagination -->
  <c:if test="${totalPages > 1}">
    <div class="pagination">
      <c:if test="${currentPage > 1}">
        <a href="?page=${currentPage-1}&sort=${sort}&dir=${dir}">&laquo; Prev</a>
      </c:if>
      <c:forEach begin="1" end="${totalPages}" var="pg">
        <c:choose>
          <c:when test="${pg == currentPage}"><span class="active">${pg}</span></c:when>
          <c:otherwise><a href="?page=${pg}&sort=${sort}&dir=${dir}">${pg}</a></c:otherwise>
        </c:choose>
      </c:forEach>
      <c:if test="${currentPage < totalPages}">
        <a href="?page=${currentPage+1}&sort=${sort}&dir=${dir}">Next &raquo;</a>
      </c:if>
    </div>
  </c:if>
</div>
</body>
</html>
