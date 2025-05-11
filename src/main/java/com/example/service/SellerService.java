package com.example.service;

import com.example.dao.SellerDAO;
import java.sql.SQLException;

public class SellerService {
    private final SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    /**
     * Получает ID продавца из таблицы sellers по user.id
     */
    public int getSellerIdByUserId(int userId) throws SQLException {
        return sellerDAO.getSellerIdByUserId(userId);
    }
}