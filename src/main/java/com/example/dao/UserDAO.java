package com.example.dao;

import com.example.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public void registerUser(User user) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertUserSQL = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRole());
                preparedStatement.executeUpdate();

                int userId = 0;
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    }
                }

                if ("EMPLOYEE".equals(user.getRole())) {
                    insertEmployee(connection, userId, user.getFullName(), user.getEmail());
                } else if ("EMPLOYER".equals(user.getRole())) {
                    insertEmployer(connection, userId, user.getEmployerFullName(), user.getCompanyName());
                }
            }
        }
    }

    private void insertEmployee(Connection connection, int userId, String fullName, String email) throws SQLException {
        String insertEmployeeSQL = "INSERT INTO employees (user_id, full_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement employeeStatement = connection.prepareStatement(insertEmployeeSQL)) {
            employeeStatement.setInt(1, userId);
            employeeStatement.setString(2, fullName);
            employeeStatement.setString(3, email);
            employeeStatement.executeUpdate();
        }
    }

    private void insertEmployer(Connection connection, int userId, String fullName, String companyName) throws SQLException {
        String insertEmployerSQL = "INSERT INTO employers (user_id, full_name, company_name) VALUES (?, ?, ?)";
        try (PreparedStatement employerStatement = connection.prepareStatement(insertEmployerSQL)) {
            employerStatement.setInt(1, userId);
            employerStatement.setString(2, fullName);
            employerStatement.setString(3, companyName);
            employerStatement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
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
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }
}
