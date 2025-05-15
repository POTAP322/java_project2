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
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserDAO());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            List<User> users = userService.getAllUsers();

            boolean loginSuccess = false;

            for (User user : users) {
                if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("home");
                    return;
                }
            }

            // Если дошли сюда — значит, логин не найден
            request.setAttribute("error", "Неверный логин или пароль");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace(); // Это будет в консоли сервера
            request.setAttribute("error", "Ошибка сервера: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
