package com.example.servlets;

import com.example.dao.PurchaseOrderDAO;
import com.example.service.PurchaseOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet {
    private final PurchaseOrderService purchaseOrderService = new PurchaseOrderService(new PurchaseOrderDAO());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int orderId = Integer.parseInt(request.getParameter("order_id"));
        String action = request.getParameter("action"); // CONFIRMED or REJECTED

        try {
            purchaseOrderService.updateOrderStatus(orderId, action);
            response.sendRedirect("seller-menu");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Ошибка при обработке заказа");
        }
    }
}
