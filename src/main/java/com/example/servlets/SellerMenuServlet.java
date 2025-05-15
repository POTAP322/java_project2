package com.example.servlets;

import com.example.dao.SellerDAO;
import com.example.models.PurchaseOrder;
import com.example.models.SellRequest;
import com.example.service.PurchaseOrderService;
import com.example.service.SellRequestService;
import com.example.models.User;
import com.example.dao.PurchaseOrderDAO;
import com.example.dao.SellRequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/seller-menu")
public class SellerMenuServlet extends HttpServlet {
    private final SellRequestService sellRequestService = new SellRequestService(new SellRequestDAO());
    private final PurchaseOrderService purchaseOrderService = new PurchaseOrderService(new PurchaseOrderDAO());
    private final SellerDAO sellerDAO = new SellerDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"SELLER".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещён");
            return;
        }

        try {
            // Получаем sellers.id по users.id
            int sellerId = sellerDAO.getSellerIdByUserId(user.getId());

            // Заявки продавца
            List<SellRequest> myRequests = sellRequestService.getSellRequestsBySeller(sellerId);
            request.setAttribute("myRequests", myRequests);

            // Отклики на заявки
            List<PurchaseOrder> allOrders = purchaseOrderService.getOrdersBySeller(sellerId);
            request.setAttribute("allOrders", allOrders);

            request.getRequestDispatcher("/sellerMenu.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Не удалось загрузить данные");
        }
    }
}
