package com.example.servlets;

import com.example.models.SellRequest;
import com.example.service.SellRequestService;
import com.example.dao.SellRequestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/create-request")
public class SellRequestServlet extends HttpServlet {
    private final SellRequestService sellRequestService;

    public SellRequestServlet() {
        this(new SellRequestService(new SellRequestDAO()));
    }

    // Для тестирования
    public SellRequestServlet(SellRequestService service) {
        this.sellRequestService = service;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int sellerId = Integer.parseInt(request.getParameter("seller_id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int price = Integer.parseInt(request.getParameter("price"));

            SellRequest sellRequest = new SellRequest();
            sellRequest.setSellerId(sellerId);
            sellRequest.setName(name);
            sellRequest.setDescription(description);
            sellRequest.setPrice(price);

            sellRequestService.createSellRequest(sellRequest);
            response.sendRedirect("requests.jsp");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при создании заявки");
        }
    }
}