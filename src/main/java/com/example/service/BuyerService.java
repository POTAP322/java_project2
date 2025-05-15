package com.example.service;

import com.example.dao.BuyerDAO;
import com.example.models.User;

import java.sql.SQLException;

public class BuyerService {
    private final BuyerDAO buyerDAO;

    public BuyerService(BuyerDAO buyerDAO) {
        this.buyerDAO = buyerDAO;
    }

    /**
     * Получает ФИО и Email покупателя по его buyers.id
     */
    public int getBuyerByUserId(int buyerId) throws SQLException {
        return buyerDAO.getBuyerIdByUserId(buyerId);
    }
}
