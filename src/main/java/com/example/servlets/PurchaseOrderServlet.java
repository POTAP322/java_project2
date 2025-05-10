package com.example.servlets;

import com.example.models.PurchaseOrder;
import com.example.service.PurchaseOrderService;
import com.example.dao.PurchaseOrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/place-order")
public class PurchaseOrderServlet extends HttpServlet {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderServlet() {
        this(new PurchaseOrderService(new PurchaseOrderDAO()));
    }

    // Для тестирования
    public PurchaseOrderServlet(PurchaseOrderService service) {
        this.purchaseOrderService = service;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int buyerId = Integer.parseInt(request.getParameter("buyer_id"));
            int requestId = Integer.parseInt(request.getParameter("request_id"));

            PurchaseOrder order = new PurchaseOrder();
            order.setBuyerId(buyerId);
            order.setSellRequestId(requestId);

            purchaseOrderService.createOrder(order);
            response.sendRedirect("orders.jsp");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при оформлении заказа");
        }
    }
}