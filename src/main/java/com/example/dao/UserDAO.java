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
                        user.setId(userId);
                    }
                }

                if ("BUYER".equals(user.getRole())) {
                    insertBuyer(connection, userId, user.getBuyerFullName(), user.getEmail());
                } else if ("SELLER".equals(user.getRole())) {
                    insertSeller(connection, userId, user.getSellerFullName(), user.getCompanyName());
                }
            }
        }
    }

    private void insertBuyer(Connection connection, int userId, String fullName, String email) throws SQLException {
        String insertBuyerSQL = "INSERT INTO buyers (user_id, full_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement buyerStatement = connection.prepareStatement(insertBuyerSQL)) {
            buyerStatement.setInt(1, userId);
            buyerStatement.setString(2, fullName);
            buyerStatement.setString(3, email);
            buyerStatement.executeUpdate();
        }
    }

    private void insertSeller(Connection connection, int userId, String fullName, String companyName) throws SQLException {
        String insertSellerSQL = "INSERT INTO sellers (user_id, full_name, company_name) VALUES (?, ?, ?)";
        try (PreparedStatement sellerStatement = connection.prepareStatement(insertSellerSQL)) {
            sellerStatement.setInt(1, userId);
            sellerStatement.setString(2, fullName);
            sellerStatement.setString(3, companyName);
            sellerStatement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT u.id, u.username, u.password, u.role, " +
                    "b.full_name AS buyer_full_name, b.email, " +
                    "s.full_name AS seller_full_name, s.company_name " +
                    "FROM users u " +
                    "LEFT JOIN buyers b ON u.id = b.user_id " +
                    "LEFT JOIN sellers s ON u.id = s.user_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setRole(resultSet.getString("role"));
                        user.setBuyerFullName(resultSet.getString("buyer_full_name"));
                        user.setEmail(resultSet.getString("email"));
                        user.setSellerFullName(resultSet.getString("seller_full_name"));
                        user.setCompanyName(resultSet.getString("company_name"));
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }
}
