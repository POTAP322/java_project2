package com.example.servlets;

import com.example.dao.SellRequestDAO;
import com.example.models.SellRequest;
import com.example.service.SellRequestService;
import com.example.service.ServiceManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final SellRequestService sellRequestService = ServiceManager.getInstance().getSellRequestService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<SellRequest> requests = sellRequestService.getAllRequests();
            request.setAttribute("requests", requests);
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Не удалось загрузить заявки");
        }
    }
}
