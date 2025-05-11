package com.example.servlets;

import com.example.dao.PurchaseOrderDAO;
import com.example.models.PurchaseOrder;
import com.example.service.PurchaseOrderService;
import com.example.service.UserService;
import com.example.models.User;
import com.example.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet("/buyer-menu")
public class BuyerMenuServlet extends HttpServlet {
    private PurchaseOrderService purchaseOrderService;

    @Override
    public void init() {
        this.purchaseOrderService = new PurchaseOrderService(new PurchaseOrderDAO());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            List<PurchaseOrder> orders = purchaseOrderService.getOrdersByBuyer(user.getId());
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/buyerMenu.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Не удалось загрузить ваши заказы");
        }
    }
}
