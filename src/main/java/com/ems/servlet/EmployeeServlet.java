package com.ems.servlet;

import com.ems.dao.*;
import com.ems.model.*;
import com.ems.util.EmailUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    private final EmployeeDAO   empDAO  = new EmployeeDAO();
    private final DepartmentDAO deptDAO = new DepartmentDAO();
    private final UserDAO       userDAO = new UserDAO();

    // ── LIST / READ ───────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        requireAdmin(req, resp);

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                req.setAttribute("departments", deptDAO.getAllDepartments());
                req.getRequestDispatcher("/admin/employee-form.jsp").forward(req, resp);
                break;
            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("employee", empDAO.getEmployeeById(id));
                req.setAttribute("departments", deptDAO.getAllDepartments());
                req.getRequestDispatcher("/admin/employee-form.jsp").forward(req, resp);
                break;
            case "delete":
                id = Integer.parseInt(req.getParameter("id"));
                empDAO.deleteEmployee(id);
                resp.sendRedirect(req.getContextPath() + "/employee?msg=deleted");
                break;
            case "view":
                id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("employee", empDAO.getEmployeeById(id));
                req.getRequestDispatcher("/admin/employee-view.jsp").forward(req, resp);
                break;
            default:
                // list with pagination + sorting
                int page     = parseIntOr(req.getParameter("page"), 1);
                int pageSize = parseIntOr(req.getParameter("size"), 10);
                String sort  = req.getParameter("sort");
                String dir   = req.getParameter("dir");
                String search= req.getParameter("search");

                List<Employee> employees = (search != null && !search.trim().isEmpty())
                    ? empDAO.searchEmployees(search)
                    : empDAO.getAllEmployees(page, pageSize, sort, dir);

                int total = empDAO.getTotalCount();
                int totalPages = (int) Math.ceil((double) total / pageSize);

                req.setAttribute("employees",  employees);
                req.setAttribute("currentPage",page);
                req.setAttribute("totalPages", totalPages);
                req.setAttribute("totalCount", total);
                req.setAttribute("pageSize",   pageSize);
                req.setAttribute("sort",       sort);
                req.setAttribute("dir",        dir);
                req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
                break;
        }
    }

    // ── CREATE / UPDATE ───────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        requireAdmin(req, resp);

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            // 1. Create a user account first
            String username = req.getParameter("email").split("@")[0];
            String tempPass = "Pass@" + System.currentTimeMillis() % 10000;
            int userId = userDAO.createUser(username, tempPass, "EMPLOYEE");

            Employee emp = buildFromRequest(req);
            emp.setUserId(userId);

            if (empDAO.addEmployee(emp)) {
                // 2. Send welcome e-mail
                EmailUtil.sendEmail(
                    emp.getEmail(),
                    "Welcome to EMS - Your Account Details",
                    EmailUtil.buildWelcomeEmail(emp.getFullName(), username, tempPass)
                );
                resp.sendRedirect(req.getContextPath() + "/employee?msg=added");
            } else {
                req.setAttribute("error", "Failed to add employee.");
                req.setAttribute("departments", deptDAO.getAllDepartments());
                req.getRequestDispatcher("/admin/employee-form.jsp").forward(req, resp);
            }

        } else if ("update".equals(action)) {
            Employee emp = buildFromRequest(req);
            emp.setEmpId(Integer.parseInt(req.getParameter("empId")));

            if (empDAO.updateEmployee(emp)) {
                // Send update notification
                String details = "<p>Your designation / department or salary may have "
                               + "been updated. Please log in to view the latest details.</p>";
                EmailUtil.sendEmail(
                    emp.getEmail(),
                    "EMS - Your Profile Has Been Updated",
                    EmailUtil.buildUpdateEmail(emp.getFullName(), details)
                );
                resp.sendRedirect(req.getContextPath() + "/employee?msg=updated");
            } else {
                req.setAttribute("error", "Failed to update employee.");
                req.setAttribute("employee",    emp);
                req.setAttribute("departments", deptDAO.getAllDepartments());
                req.getRequestDispatcher("/admin/employee-form.jsp").forward(req, resp);
            }
        }
    }

    // ── HELPERS ───────────────────────────────────────────────
    private Employee buildFromRequest(HttpServletRequest req) {
        Employee emp = new Employee();
        emp.setFirstName(req.getParameter("firstName"));
        emp.setLastName(req.getParameter("lastName"));
        emp.setEmail(req.getParameter("email"));
        emp.setPhone(req.getParameter("phone"));
        emp.setDeptId(Integer.parseInt(req.getParameter("deptId")));
        emp.setDesignation(req.getParameter("designation"));
        emp.setSalary(new BigDecimal(req.getParameter("salary")));
        String hd = req.getParameter("hireDate");
        if (hd != null && !hd.isBlank()) emp.setHireDate(Date.valueOf(hd));
        emp.setAddress(req.getParameter("address"));
        return emp;
    }

    private void requireAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("user") == null
            || !((User) s.getAttribute("user")).isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    private int parseIntOr(String val, int def) {
        try { return Integer.parseInt(val); } catch (Exception e) { return def; }
    }
}
