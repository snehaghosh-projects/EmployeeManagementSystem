package com.ems.servlet;

import com.ems.dao.EmployeeDAO;
import com.ems.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

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
        req.setAttribute("employee", empDAO.getEmployeeByUserId(user.getUserId()));
        req.getRequestDispatcher("/employee/profile.jsp").forward(req, resp);
    }
}
