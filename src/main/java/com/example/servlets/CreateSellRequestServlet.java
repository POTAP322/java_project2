package com.example.servlets;

import com.example.dao.SellRequestDAO;
import com.example.dao.SellerDAO;
import com.example.models.SellRequest;
import com.example.models.User;
import com.example.service.SellRequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;

@WebServlet("/create-sell-request")
public class CreateSellRequestServlet extends HttpServlet {
    private SellRequestService sellRequestService;
    private SellerDAO sellerDAO;

    @Override
    public void init() throws ServletException {
        this.sellRequestService = new SellRequestService(new SellRequestDAO());
        this.sellerDAO = new SellerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"SELLER".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещён");
            return;
        }

        try {
            // Получаем seller_id из таблицы sellers по user.id
            int sellerId = sellerDAO.getSellerIdByUserId(user.getId());

            SellRequest sellRequest = new SellRequest();
            sellRequest.setSellerId(sellerId); // ← Используем sellers.id
            sellRequest.setName(request.getParameter("name"));
            sellRequest.setDescription(request.getParameter("description"));
            sellRequest.setPrice(Integer.parseInt(request.getParameter("price")));

            sellRequestService.createSellRequest(sellRequest);
            response.sendRedirect("seller-menu");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Ошибка при создании заявки: " + e.getMessage());
            request.getRequestDispatcher("/sellerMenu.jsp").forward(request, response);
        }
    }
}