package com.example.servlets;

import com.example.dao.PurchaseOrderDAO;
import com.example.dao.SellRequestDAO;
import com.example.dao.SellerDAO;
import com.example.models.PurchaseOrder;
import com.example.models.SellRequest;
import com.example.service.PurchaseOrderService;
import com.example.service.SellRequestService;
import com.example.service.SellerService;
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

@WebServlet("/seller-menu")
public class SellerMenuServlet extends HttpServlet {
    private SellRequestService sellRequestService;
    private PurchaseOrderService purchaseOrderService;
    private SellerService sellerService;

    @Override
    public void init() {
        SellRequestDAO requestDAO = new SellRequestDAO();
        PurchaseOrderDAO orderDAO = new PurchaseOrderDAO();
        SellerDAO sellerDAO = new SellerDAO();

        this.sellRequestService = new SellRequestService(requestDAO);
        this.purchaseOrderService = new PurchaseOrderService(orderDAO);
        this.sellerService = new SellerService(sellerDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"SELLER".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещён");
            return;
        }

        try {
            // Получаем seller_id из sellers по user.id
            int sellerId = sellerService.getSellerIdByUserId(user.getId());

            // Заявки этого продавца
            List<SellRequest> myRequests = sellRequestService.getSellRequestsBySeller(sellerId);
            request.setAttribute("myRequests", myRequests);

            // Отклики на заявки этого продавца
            List<PurchaseOrder> allOrders = purchaseOrderService.getOrdersBySeller(sellerId);
            request.setAttribute("allOrders", allOrders);

            request.getRequestDispatcher("/sellerMenu.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Не удалось загрузить данные");
        }
    }
}
