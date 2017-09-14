package model.dao;

import model.entities.Meal;
import model.entities.Order;

import java.util.List;

public interface OrderDAO extends GenericDAO<Order>{
	public List<Meal> loadOrderMeals(Order order);
}
