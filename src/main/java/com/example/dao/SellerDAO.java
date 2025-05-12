package com.example.dao;
import java.sql.*;

public class SellerDAO {
    public int getSellerIdByUserId(int userId) throws SQLException {
        String sql = "SELECT id FROM sellers WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Это sellers.id
                } else {
                    throw new SQLException("Продавец не найден для пользователя с ID: " + userId);
                }
            }
        }
    }
}
