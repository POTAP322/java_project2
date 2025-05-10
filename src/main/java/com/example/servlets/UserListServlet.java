package com.example.servlets;

import com.example.service.UserService;
import com.example.models.User;
import com.example.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")
public class UserListServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        UserDAO userDAO = new UserDAO();
        userService = new UserService(userDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> buyerList = new ArrayList<>();
        List<User> sellerList = new ArrayList<>();
        List<User> adminList = new ArrayList<>();

        try {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                switch (user.getRole()) {
                    case "EMPLOYEE":
                        buyerList.add(user);
                        break;
                    case "EMPLOYER":
                        sellerList.add(user);
                        break;
                    case "ADMIN":
                        adminList.add(user);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Не удалось загрузить список пользователей");
            return;
        }

        request.setAttribute("buyerList", buyerList);
        request.setAttribute("sellerList", sellerList);
        request.setAttribute("adminList", adminList);
        request.getRequestDispatcher("/userList.jsp").forward(request, response);
    }
}