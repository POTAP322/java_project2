package com.example.servlets;

import com.example.service.UserService;
import com.example.models.User;
import com.example.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        UserDAO userDAO = new UserDAO();
        userService = new UserService(userDAO);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        if ("EMPLOYEE".equals(role)) {
            user.setBuyerFullName(request.getParameter("buyerFullName"));
            user.setEmail(request.getParameter("email"));
        } else if ("EMPLOYER".equals(role)) {
            user.setSellerFullName(request.getParameter("sellerFullName"));
            user.setCompanyName(request.getParameter("companyName"));
        }

        try {
            userService.registerUser(user);
            response.sendRedirect("registration_success.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка регистрации: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}