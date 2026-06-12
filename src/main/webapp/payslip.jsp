<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt"  prefix="fmt" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login"); return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Payslip — ${emp.fullName}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="navbar" style="print:none">
  <span class="brand">&#128188; EMS</span>
  <nav>
    <a href="javascript:window.print()">&#128438; Print</a>
    <a href="javascript:history.back()">&#8592; Back</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </nav>
</div>

<div class="page">
  <div class="payslip">
    <div class="payslip-header">
      <h2>&#128188; Employee Management System</h2>
      <p style="color:var(--muted)">Payslip for ${month} ${year}</p>
      <hr style="margin:16px 0;border-color:var(--border)">
    </div>

    <!-- Employee info -->
    <div class="detail-grid" style="margin-bottom:20px">
      <div class="detail-item"><label>Employee Name</label><span>${emp.fullName}</span></div>
      <div class="detail-item"><label>Employee ID</label><span>#${emp.empId}</span></div>
      <div class="detail-item"><label>Designation</label><span>${emp.designation}</span></div>
      <div class="detail-item"><label>Department</label><span>${emp.deptName}</span></div>
      <div class="detail-item"><label>Pay Period</label><span>${month} ${year}</span></div>
      <div class="detail-item"><label>Hire Date</label><span>${emp.hireDate}</span></div>
    </div>

    <!-- Earnings & Deductions -->
    <table class="payslip-table">
      <thead>
        <tr>
          <th>Earnings</th><th style="text-align:right">Amount (&#8377;)</th>
          <th>Deductions</th><th style="text-align:right">Amount (&#8377;)</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Basic Salary</td>
          <td style="text-align:right"><fmt:formatNumber value="${emp.salary}" pattern="#,##0.00"/></td>
          <td>Provident Fund (12%)</td>
          <td style="text-align:right"><fmt:formatNumber value="${pf}" pattern="#,##0.00"/></td>
        </tr>
        <tr>
          <td>HRA (20%)</td>
          <td style="text-align:right"><fmt:formatNumber value="${hra}" pattern="#,##0.00"/></td>
          <td>Income Tax (10%)</td>
          <td style="text-align:right"><fmt:formatNumber value="${tax}" pattern="#,##0.00"/></td>
        </tr>
        <tr>
          <td>Dearness Allowance (10%)</td>
          <td style="text-align:right"><fmt:formatNumber value="${da}" pattern="#,##0.00"/></td>
          <td></td><td></td>
        </tr>
        <tr>
          <td>Travel Allowance</td>
          <td style="text-align:right"><fmt:formatNumber value="${ta}" pattern="#,##0.00"/></td>
          <td></td><td></td>
        </tr>
        <tr class="payslip-total">
          <td><strong>Total Earnings</strong></td>
          <td style="text-align:right"><strong><fmt:formatNumber value="${totalEarn}" pattern="#,##0.00"/></strong></td>
          <td><strong>Total Deductions</strong></td>
          <td style="text-align:right"><strong><fmt:formatNumber value="${totalDed}" pattern="#,##0.00"/></strong></td>
        </tr>
      </tbody>
    </table>

    <!-- Net pay -->
    <div style="text-align:right;margin-top:16px;padding:16px;
                background:#eff6ff;border-radius:var(--radius);border:1px solid #bfdbfe">
      <span style="font-size:18px;font-weight:700;color:var(--primary)">
        Net Pay: &#8377; <fmt:formatNumber value="${netPay}" pattern="#,##0.00"/>
      </span>
    </div>

    <p style="margin-top:20px;font-size:12px;color:var(--muted);text-align:center">
      This is a computer-generated payslip and does not require a signature.
    </p>
  </div>
</div>
</body>
</html>
