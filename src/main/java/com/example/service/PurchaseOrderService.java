package com.example.service;

import com.example.dao.PurchaseOrderDAO;
import com.example.models.PurchaseOrder;

import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderService {
    private final PurchaseOrderDAO purchaseOrderDAO;

    public PurchaseOrderService(PurchaseOrderDAO purchaseOrderDAO) {
        this.purchaseOrderDAO = purchaseOrderDAO;
    }

    public void createOrder(PurchaseOrder order) throws SQLException {
        purchaseOrderDAO.createOrder(order);
    }

    public List<PurchaseOrder> getOrdersBySeller(int sellerId) throws SQLException {
        return purchaseOrderDAO.getOrdersBySeller(sellerId);
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        purchaseOrderDAO.updateOrderStatus(orderId, status);
    }
}