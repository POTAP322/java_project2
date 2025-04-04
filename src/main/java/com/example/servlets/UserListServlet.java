package com.example.servlets;

import com.example.dao.UserDAO;
import com.example.models.User;

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
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> employeeList = new ArrayList<>();
        List<User> employerList = new ArrayList<>();

        try {
            List<User> users = userDAO.getAllUsers();
            for (User user : users) {
                if ("EMPLOYEE".equals(user.getRole())) {
                    employeeList.add(user);
                } else if ("EMPLOYER".equals(user.getRole())) {
                    employerList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve users");
            return;
        }

        request.setAttribute("employeeList", employeeList);
        request.setAttribute("employerList", employerList);
        request.getRequestDispatcher("/userList.jsp").forward(request, response);
    }
}
