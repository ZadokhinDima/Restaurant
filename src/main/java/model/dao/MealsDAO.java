package model.dao;

import model.entities.Meal;
import model.entities.MealCategory;
import model.entities.Order;

import java.util.List;


public interface MealsDAO extends GenericDAO<Meal> {
	List<Meal> getFromOrder(Order order);

	List<Meal> getAllForCategory(MealCategory category);
}
