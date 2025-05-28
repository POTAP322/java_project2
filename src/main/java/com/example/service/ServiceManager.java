package com.example.service;

import com.example.dao.*;

public class ServiceManager {
    private static ServiceManager instance;

    private final BuyerService buyerService;
    private final PurchaseOrderService purchaseOrderService;
    private final SellRequestService sellRequestService;
    private final SellerService sellerService;
    private final UserService userService;

    private ServiceManager() {
        BuyerDAO buyerDAO = new BuyerDAO();
        PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();
        SellRequestDAO sellRequestDAO = new SellRequestDAO();
        SellerDAO sellerDAO = new SellerDAO();
        UserDAO userDAO = new UserDAO();

        this.buyerService = new BuyerService(buyerDAO);
        this.purchaseOrderService = new PurchaseOrderService(purchaseOrderDAO);
        this.sellRequestService = new SellRequestService(sellRequestDAO);
        this.sellerService = new SellerService(sellerDAO);
        this.userService = new UserService(userDAO);
    }

    public static synchronized ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public BuyerService getBuyerService() {
        return buyerService;
    }

    public PurchaseOrderService getPurchaseOrderService() {
        return purchaseOrderService;
    }

    public SellRequestService getSellRequestService() {
        return sellRequestService;
    }

    public SellerService getSellerService() {
        return sellerService;
    }

    public UserService getUserService() {
        return userService;
    }
}
