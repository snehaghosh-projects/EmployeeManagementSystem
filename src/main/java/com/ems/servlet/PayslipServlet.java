package com.ems.servlet;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;
import com.ems.model.User;
import com.ems.util.EmailUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@WebServlet("/payslip")
public class PayslipServlet extends HttpServlet {

    private final EmployeeDAO empDAO = new EmployeeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Admin can view any employee's payslip; employee sees only their own
        int empId;
        if (user.isAdmin() && req.getParameter("id") != null) {
            empId = Integer.parseInt(req.getParameter("id"));
        } else {
            Employee self = empDAO.getEmployeeByUserId(user.getUserId());
            if (self == null) { resp.sendRedirect(req.getContextPath() + "/profile"); return; }
            empId = self.getEmpId();
        }

        Employee emp = empDAO.getEmployeeById(empId);
        if (emp == null) { resp.sendError(404, "Employee not found"); return; }

        // ── Payroll calculation ───────────────────────────────
        BigDecimal gross     = emp.getSalary();
        BigDecimal hra       = gross.multiply(new BigDecimal("0.20")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal da        = gross.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal ta        = new BigDecimal("1500.00");
        BigDecimal pf        = gross.multiply(new BigDecimal("0.12")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax       = gross.multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalEarn = gross.add(hra).add(da).add(ta);
        BigDecimal totalDed  = pf.add(tax);
        BigDecimal netPay    = totalEarn.subtract(totalDed);

        LocalDate now = LocalDate.now();

        req.setAttribute("emp",       emp);
        req.setAttribute("hra",       hra);
        req.setAttribute("da",        da);
        req.setAttribute("ta",        ta);
        req.setAttribute("pf",        pf);
        req.setAttribute("tax",       tax);
        req.setAttribute("totalEarn", totalEarn);
        req.setAttribute("totalDed",  totalDed);
        req.setAttribute("netPay",    netPay);
        req.setAttribute("month",     now.getMonth().toString());
        req.setAttribute("year",      now.getYear());

        req.getRequestDispatcher("/payslip.jsp").forward(req, resp);
    }

    /** Admin triggers e-mail delivery of the payslip. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !((User) session.getAttribute("user")).isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login"); return;
        }

        int empId = Integer.parseInt(req.getParameter("empId"));
        Employee emp = empDAO.getEmployeeById(empId);
        if (emp == null) { resp.sendError(404); return; }

        String body = "<html><body style='font-family:Arial'>"
            + "<h2>Payslip Notification</h2>"
            + "<p>Dear " + emp.getFullName() + ",</p>"
            + "<p>Your payslip for " + LocalDate.now().getMonth() + " "
            + LocalDate.now().getYear() + " is now available. "
            + "Please log in to the Employee Management Portal to download it.</p>"
            + "</body></html>";

        EmailUtil.sendEmail(emp.getEmail(), "EMS - Payslip Available", body);
        resp.sendRedirect(req.getContextPath() + "/employee?msg=payslip_sent");
    }
}
