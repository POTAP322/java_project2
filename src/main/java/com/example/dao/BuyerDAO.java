package com.example.dao;
import com.example.models.User;

import java.sql.*;
public class BuyerDAO {
    public User getBuyerByUserId(int buyerId) throws SQLException {
        String sql = "SELECT b.full_name, b.email, u.username " +
                "FROM buyers b " +
                "JOIN users u ON b.user_id = u.id " +
                "WHERE b.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User buyer = new User();
                    buyer.setBuyerFullName(rs.getString("full_name"));
                    buyer.setEmail(rs.getString("email"));
                    buyer.setUsername(rs.getString("username")); // опционально
                    return buyer;
                } else {
                    throw new SQLException("Покупатель с ID " + buyerId + " не найден");
                }
            }
        }
    }
}
