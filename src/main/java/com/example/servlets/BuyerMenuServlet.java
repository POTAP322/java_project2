package com.example.servlets;

import com.example.dao.BuyerDAO;
import com.example.dao.PurchaseOrderDAO;
import com.example.dao.SellRequestDAO;
import com.example.models.PurchaseOrder;
import com.example.models.SellRequest;
import com.example.models.User;
import com.example.service.BuyerService;
import com.example.service.PurchaseOrderService;
import com.example.service.SellRequestService;

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
    private final PurchaseOrderService purchaseOrderService = new PurchaseOrderService(new PurchaseOrderDAO());
    private final SellRequestService sellRequestService = new SellRequestService(new SellRequestDAO());
    private final BuyerService buyerService = new BuyerService(new BuyerDAO());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            int buyerId = buyerService.getBuyerByUserId(user.getId());
            List<PurchaseOrder> orders = purchaseOrderService.getOrdersByBuyer(buyerId);
            List<SellRequest> allRequests = sellRequestService.getAllRequests();

            // Передаём в JSP оба списка
            request.setAttribute("orders", orders);
            request.setAttribute("allRequests", allRequests);

            request.getRequestDispatcher("/buyerMenu.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Не удалось загрузить данные");
        }
    }
}
