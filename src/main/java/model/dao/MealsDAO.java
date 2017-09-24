package model.dao;

import model.entities.Meal;
import model.entities.Order;

import java.util.List;

public interface MealsDAO extends GenericDAO<Meal> {
	List<Meal> getFromOrder(Order order);
}
