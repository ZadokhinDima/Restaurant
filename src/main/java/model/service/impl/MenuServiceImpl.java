package model.service.impl;

import model.dao.CategoryDAO;
import model.dao.ConnectionDAO;
import model.dao.FactoryDAO;
import model.dao.MealsDAO;
import model.dao.impl.mysql.MySQLFactory;
import model.entities.Meal;
import model.entities.MealCategory;
import model.service.MenuService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class MenuServiceImpl implements MenuService {


    private FactoryDAO factory;

    private MenuServiceImpl(){
        factory = FactoryDAO.getInstance();
    }

    private static class Holder{
        private static MenuServiceImpl INSTANCE = new MenuServiceImpl();
    }

    public static MenuServiceImpl getInstance(){
        return Holder.INSTANCE;
    }


    public Meal getMealWithAmount(int mealId, int amount) {
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        MealsDAO mealsDAO = factory.getMealsDAO(connectionDAO);
        Meal meal = mealsDAO.getForId(mealId).get();
        meal.setAmount(amount);
        connectionDAO.close();
        return meal;
    }

    public void addMealToList(int mealId, int amount, List<Meal> meals) {
        Meal toAdd = getMealWithAmount(mealId, amount);
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        CategoryDAO categoryDAO = factory.getCategoryDAO(connectionDAO);
        if (meals.contains(toAdd)) {
            for (Meal meal : meals) {
                if (meal.equals(toAdd)) {
                    meal.setAmount(meal.getAmount() + toAdd.getAmount());
                }
            }
        } else {
            toAdd.setCategory(categoryDAO.getForId(toAdd.getCategoryId()).get());
            meals.add(toAdd);
        }
        connectionDAO.close();
    }

    public void removeMealFromList(int mealId, int amount, List<Meal> meals) {
        Meal toRemove = getMealWithAmount(mealId, amount);
        Meal toBeTotallyRemoved = null;
        for (Meal meal : meals) {
            if (meal.equals(toRemove)) {
                int rest = meal.getAmount() - toRemove.getAmount();
                if (rest <= 0) {
                    toBeTotallyRemoved = meal;
                }
                meal.setAmount(rest);
            }
        }
        //if no one meal rest
        if(toBeTotallyRemoved != null){
            meals.remove(toBeTotallyRemoved);
        }
    }

    @Override
    public List<Meal> getMealsForCategory(int categoryId) {
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        MealsDAO mealsDAO = factory.getMealsDAO(connectionDAO);
        CategoryDAO categoryDAO = factory.getCategoryDAO(connectionDAO);
        List<Meal> result = mealsDAO.getAllForCategory(categoryDAO.getForId(categoryId).get());
        connectionDAO.close();
        return result;
    }

    @Override
    public List<MealCategory> getAllCategories() {
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        CategoryDAO categoryDAO = factory.getCategoryDAO(connectionDAO);
        List<MealCategory> result = categoryDAO.getAll();
        connectionDAO.close();
        return result;
    }

    @Override
    public MealCategory getForId(int category) {
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        CategoryDAO categoryDAO = factory.getCategoryDAO(connectionDAO);
        MealCategory result = categoryDAO.getForId(category).get();
        connectionDAO.close();
        return result;
    }
}
