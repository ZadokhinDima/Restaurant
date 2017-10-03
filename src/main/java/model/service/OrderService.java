package model.service;


import model.entities.*;

import java.util.List;
import java.util.Optional;


public interface OrderService {

    List<Order> ordersOfUser(User user);

    List<Meal> getOrderMeals(int orderId);

    boolean createOrder(User client, List<Meal> meals);

    int getSummaryPrice(Order order);

    boolean checkClientRightsOnOrder(int orderId, User client);
}
