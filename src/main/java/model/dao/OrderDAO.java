package model.dao;


import model.entities.Order;
import model.entities.User;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order>{
    List<Order> getForUser(User user);
}
