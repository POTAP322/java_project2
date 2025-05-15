package com.example.servlets;

import com.example.service.UserService;
import com.example.models.User;
import com.example.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserDAO());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Чтение данных из формы
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        // Заполняем поля в зависимости от роли
        if ("BUYER".equals(role)) {
            user.setBuyerFullName(request.getParameter("buyerFullName"));
            user.setEmail(request.getParameter("email"));
        } else if ("SELLER".equals(role)) {
            user.setSellerFullName(request.getParameter("sellerFullName"));
            user.setCompanyName(request.getParameter("companyName"));
        }

        try {
            // Регистрация пользователя
            userService.registerUser(user);

            // Сохранение пользователя в сессии
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Перенаправление на главную страницу
            response.sendRedirect("home");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка регистрации: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
