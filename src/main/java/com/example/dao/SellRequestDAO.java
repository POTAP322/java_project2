package com.example.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.models.SellRequest;
public class SellRequestDAO {
    public void createSellRequest(SellRequest request) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO sell_requests (seller_id, name, description, price, created_at) VALUES (?, ?, ?, ?, NOW())";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, request.getSellerId());
                ps.setString(2, request.getName());
                ps.setString(3, request.getDescription());
                ps.setInt(4, request.getPrice());
                ps.executeUpdate();
            }
        }
    }

    public List<SellRequest> getAllRequests() throws SQLException {
        List<SellRequest> requests = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM sell_requests")) {
            while (rs.next()) {
                SellRequest r = new SellRequest();
                r.setId(rs.getInt("id"));
                r.setSellerId(rs.getInt("seller_id"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                r.setPrice(rs.getInt("price"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                requests.add(r);
            }
        }
        return requests;
    }
    /**
     * Получает все заявки на продажу, созданные конкретным продавцом
     */
    public List<SellRequest> getSellRequestsBySeller(int sellerId) throws SQLException {
        List<SellRequest> requests = new ArrayList<>();
        String sql = "SELECT id, seller_id, name, description, price, created_at FROM sell_requests WHERE seller_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sellerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SellRequest request = new SellRequest();
                    request.setId(rs.getInt("id"));
                    request.setSellerId(rs.getInt("seller_id"));
                    request.setName(rs.getString("name"));
                    request.setDescription(rs.getString("description"));
                    request.setPrice(rs.getInt("price"));
                    request.setCreatedAt(rs.getTimestamp("created_at"));
                    requests.add(request);
                }
            }
        }
        return requests;
    }
}
