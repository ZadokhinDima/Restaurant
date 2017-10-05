package model.service;

import model.entities.Check;
import model.entities.Order;
import model.entities.User;

import java.util.List;

public interface CheckService {
    List<Check> getChecks(User client);

    void payCheck(int check);

    Check acceptOrder(Order order, User admin);

    void declineOrder(int orderId);
}
