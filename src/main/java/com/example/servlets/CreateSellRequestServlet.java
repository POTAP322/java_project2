package com.example.servlets;

import com.example.dao.SellRequestDAO;
import com.example.dao.SellerDAO;
import com.example.models.SellRequest;
import com.example.models.User;
import com.example.service.SellRequestService;
import com.example.service.SellerService;

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
    private final SellRequestService sellRequestService = new SellRequestService(new SellRequestDAO());
    private final SellerService sellerService = new SellerService(new SellerDAO());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null || !"SELLER".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещён");
            return;
        }

        try {
            int sellerId = sellerService.getSellerIdByUserId(user.getId());

            SellRequest sellRequest = new SellRequest();
            sellRequest.setSellerId(sellerId);
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