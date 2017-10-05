package model.service;


import model.entities.*;

import java.util.List;


public interface OrderService {

    List<Order> ordersOfUser(User user);

    List<Meal> getOrderMeals(int orderId);

    int createOrder(User client, List<Meal> meals);

    int getSummaryPrice(Order order);

    boolean checkClientRightsOnOrder(int orderId, User client);

    Order getFullInfoAboutOrder(int orderId);

    List<Order> getActiveOrders();
}
