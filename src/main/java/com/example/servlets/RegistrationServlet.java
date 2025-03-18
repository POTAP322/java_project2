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


public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String fullName = request.getParameter("fullName");
        String employerFullName = request.getParameter("employerFullName");
        String email = request.getParameter("email");
        String companyName = request.getParameter("companyName");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertUserSQL = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, role);
                preparedStatement.executeUpdate();

                int userId = 0;
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    }
                }

                if (role.equals("EMPLOYEE")) {
                    String insertEmployeeSQL = "INSERT INTO employees (user_id, full_name, email) VALUES (?, ?, ?)";
                    try (PreparedStatement employeeStatement = connection.prepareStatement(insertEmployeeSQL)) {
                        employeeStatement.setInt(1, userId);
                        employeeStatement.setString(2, fullName);
                        employeeStatement.setString(3, email);
                        employeeStatement.executeUpdate();
                    }
                } else if (role.equals("EMPLOYER")) {
                    String insertEmployerSQL = "INSERT INTO employers (user_id, full_name, company_name) VALUES (?, ?, ?)";
                    try (PreparedStatement employerStatement = connection.prepareStatement(insertEmployerSQL)) {
                        employerStatement.setInt(1, userId);
                        employerStatement.setString(2, employerFullName);
                        employerStatement.setString(3, companyName);
                        employerStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Registration failed");
            return;
        }

        response.sendRedirect("registration_success.jsp");
    }
}