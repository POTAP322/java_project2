package com.example.service;

import com.example.dao.SellRequestDAO;
import com.example.models.SellRequest;

import java.sql.SQLException;
import java.util.List;

public class SellRequestService {
    private final SellRequestDAO sellRequestDAO;

    public SellRequestService(SellRequestDAO sellRequestDAO) {
        this.sellRequestDAO = sellRequestDAO;
    }

    public void createSellRequest(SellRequest request) throws SQLException {
        sellRequestDAO.createSellRequest(request);
    }

    public List<SellRequest> getAllRequests() throws SQLException {
        return sellRequestDAO.getAllRequests();
    }
}