package model.service;


import model.entities.Meal;
import model.entities.MealCategory;


import java.util.List;

public interface MenuService {
    List<Meal> getMealsForCategory(int category);

    Meal getMealWithAmount(int mealId, int amount);

    void addMealToList(int mealId, int amount, List<Meal> meals);

    void removeMealFromList(int mealId, int amount, List<Meal> meals);

    List<MealCategory> getAllCategories();

    MealCategory getForId(int category);
}
