package com.example.servlets;

import com.example.service.PurchaseOrderService;
import com.example.dao.PurchaseOrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/manage-orders")
public class ManageOrdersServlet extends HttpServlet {
    private final PurchaseOrderService purchaseOrderService;

    public ManageOrdersServlet() {
        this(new PurchaseOrderService(new PurchaseOrderDAO()));
    }

    // Для тестирования
    public ManageOrdersServlet(PurchaseOrderService service) {
        this.purchaseOrderService = service;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("order_id"));
            String action = request.getParameter("action"); // confirm or reject

            String status = "REJECTED";
            if ("confirm".equals(action)) {
                status = "CONFIRMED";
            }

            purchaseOrderService.updateOrderStatus(orderId, status);
            response.sendRedirect("manage_orders.jsp");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при обработке заказа");
        }
    }
}