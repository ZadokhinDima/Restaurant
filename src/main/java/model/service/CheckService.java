package model.service;

import model.entities.Check;
import model.entities.User;

import java.util.List;

public interface CheckService {
    List<Check> getChecks(User client);

    void payCheck(int check);

    Check acceptOrder(int orderId, User admin);

    void declineOrder(int orderId);
}
