package com.example.dao;
import com.example.models.User;

import java.sql.*;
public class BuyerDAO {
    public int getBuyerIdByUserId(int userId) throws SQLException {
        String sql = "SELECT id FROM buyers WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Это buyers.id
                } else {
                    throw new SQLException("Покупатель не найден для пользователя с ID: " + userId);
                }
            }
        }
    }
}
