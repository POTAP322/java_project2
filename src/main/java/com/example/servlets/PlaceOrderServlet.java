package com.example.servlets;

import com.example.dao.BuyerDAO;
import com.example.dao.PurchaseOrderDAO;
import com.example.service.BuyerService;
import com.example.models.PurchaseOrder;
import com.example.service.PurchaseOrderService;
import com.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {
    private final PurchaseOrderService purchaseOrderService = new PurchaseOrderService(new PurchaseOrderDAO());
    private final BuyerService buyerService = new BuyerService(new BuyerDAO());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");


        if (user == null || !"BUYER".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Только покупатель может оформлять заказы");
            return;
        }

        int sellRequestId = Integer.parseInt(request.getParameter("request_id"));

        try {
            int buyerId = buyerService.getBuyerByUserId(user.getId());

            // Проверяем, не заказывал ли уже пользователь эту заявку
            if (purchaseOrderService.isAlreadyOrdered(buyerId, sellRequestId)) {
                response.sendRedirect("home?warning=already_ordered");
                return;
            }

            // Создаем новый заказ
            PurchaseOrder order = new PurchaseOrder();
            order.setBuyerId(buyerId);
            order.setSellRequestId(sellRequestId);
            order.setStatus("PENDING");
            purchaseOrderService.createOrder(order);

            response.sendRedirect("home");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("home?error=order_failed");
        }
    }
}
