package com.example.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.models.PurchaseOrder;
public class PurchaseOrderDAO {
    public void createOrder(PurchaseOrder order) throws SQLException {
        String sql = "INSERT INTO purchase_orders (buyer_id, sell_request_id, status, created_at) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getBuyerId());
            ps.setInt(2, order.getSellRequestId());
            ps.setString(3, "PENDING");
            ps.executeUpdate();
        }
    }

    public List<PurchaseOrder> getOrdersBySeller(int sellerId) throws SQLException {
        List<PurchaseOrder> orders = new ArrayList<>();
        String sql = "SELECT po.id, po.buyer_id, po.sell_request_id, po.status " +
                "FROM purchase_orders po " +
                "JOIN sell_requests sr ON po.sell_request_id = sr.id " +
                "WHERE sr.seller_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PurchaseOrder o = new PurchaseOrder();
                    o.setId(rs.getInt("id"));
                    o.setBuyerId(rs.getInt("buyer_id"));
                    o.setSellRequestId(rs.getInt("sell_request_id"));
                    o.setStatus(rs.getString("status"));
                    orders.add(o);
                }
            }
        }
        return orders;
    }
    /**
     * Получает все заказы, сделанные определённым покупателем
     */
    public List<PurchaseOrder> getOrdersByBuyer(int buyerId) throws SQLException {
        List<PurchaseOrder> orders = new ArrayList<>();
        String sql = "SELECT id, buyer_id, sell_request_id, status, created_at " +
                "FROM purchase_orders " +
                "WHERE buyer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, buyerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PurchaseOrder order = new PurchaseOrder();
                    order.setId(rs.getInt("id"));
                    order.setBuyerId(rs.getInt("buyer_id"));
                    order.setSellRequestId(rs.getInt("sell_request_id"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at")); // если есть поле createdAt
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE purchase_orders SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }
    public boolean hasBuyerAlreadyOrdered(int buyerId, int sellRequestId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM purchase_orders WHERE buyer_id = ? AND sell_request_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, buyerId);
            ps.setInt(2, sellRequestId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // true, если есть запись
                }
            }
        }
        return false;
    }
}
