package com.example.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")
public class UserListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> employeeList = new ArrayList<>();
        List<User> employerList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT u.id, u.username, u.role, e.full_name, e.email, em.full_name AS employer_full_name, em.company_name " +
                    "FROM users u " +
                    "LEFT JOIN employees e ON u.id = e.user_id " +
                    "LEFT JOIN employers em ON u.id = em.user_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setRole(resultSet.getString("role"));
                        user.setFullName(resultSet.getString("full_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setEmployerFullName(resultSet.getString("employer_full_name"));
                        user.setCompanyName(resultSet.getString("company_name"));

                        if ("EMPLOYEE".equals(user.getRole())) {
                            employeeList.add(user);
                        } else if ("EMPLOYER".equals(user.getRole())) {
                            employerList.add(user);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve users");
            return;
        }

        request.setAttribute("employeeList", employeeList);
        request.setAttribute("employerList", employerList);
        request.getRequestDispatcher("/userList.jsp").forward(request, response);
    }
}