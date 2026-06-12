<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%
    if (session.getAttribute("user") == null ||
        !((com.ems.model.User)session.getAttribute("user")).isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/login"); return;
    }
    boolean isEdit = (request.getAttribute("employee") != null);
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><%= isEdit ? "Edit" : "Add" %> Employee — EMS</title>
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
  <h2 class="page-title"><%= isEdit ? "Edit Employee" : "Add New Employee" %></h2>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <div class="card">
    <form method="post" action="${pageContext.request.contextPath}/employee">
      <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
      <c:if test="${not empty employee}">
        <input type="hidden" name="empId" value="${employee.empId}">
      </c:if>

      <div class="form-grid">
        <div class="form-group">
          <label>First Name *</label>
          <input name="firstName" type="text" required
                 value="${employee.firstName}" placeholder="John">
        </div>
        <div class="form-group">
          <label>Last Name *</label>
          <input name="lastName" type="text" required
                 value="${employee.lastName}" placeholder="Doe">
        </div>
        <div class="form-group">
          <label>Email *</label>
          <input name="email" type="email" required
                 value="${employee.email}" placeholder="john.doe@company.com">
        </div>
        <div class="form-group">
          <label>Phone</label>
          <input name="phone" type="tel"
                 value="${employee.phone}" placeholder="9876543210">
        </div>
        <div class="form-group">
          <label>Department *</label>
          <select name="deptId" required>
            <option value="">-- Select Department --</option>
            <c:forEach var="dept" items="${departments}">
              <option value="${dept.key}"
                <c:if test="${employee.deptId == dept.key}">selected</c:if>
              >${dept.value}</option>
            </c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label>Designation *</label>
          <input name="designation" type="text" required
                 value="${employee.designation}" placeholder="Software Engineer">
        </div>
        <div class="form-group">
          <label>Salary (&#8377;) *</label>
          <input name="salary" type="number" step="0.01" min="0" required
                 value="${employee.salary}" placeholder="50000">
        </div>
        <div class="form-group">
          <label>Hire Date</label>
          <input name="hireDate" type="date" value="${employee.hireDate}">
        </div>
        <div class="form-group" style="grid-column:1/-1">
          <label>Address</label>
          <textarea name="address" rows="3"
                    placeholder="Full address…">${employee.address}</textarea>
        </div>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn btn-primary">
          <%= isEdit ? "&#10003; Save Changes" : "+ Add Employee" %>
        </button>
        <a href="${pageContext.request.contextPath}/employee" class="btn btn-outline">Cancel</a>
      </div>
    </form>
  </div>
</div>
</body>
</html>
