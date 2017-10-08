package model.service.impl;

import model.dao.CategoryDAO;
import model.dao.FactoryDAO;
import model.dao.MealsDAO;
import model.dao.impl.mysql.DbManager;
import model.dao.impl.mysql.MySQLFactory;
import model.entities.Meal;
import model.entities.MealCategory;
import model.service.MenuService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = Logger.getLogger(MenuServiceImpl.class);

    private MenuServiceImpl(){

    }

    private static class Holder{
        private static MenuServiceImpl INSTANCE = new MenuServiceImpl();
    }

    public static MenuServiceImpl getInstance(){
        return Holder.INSTANCE;
    }


    public Meal getMealWithAmount(int mealId, int amount) {
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        factory.setConnection(connection);
        MealsDAO mealsDAO = factory.getMealsDAO();
        Meal meal = mealsDAO.getForId(mealId).get();
        meal.setAmount(amount);
        DbManager.putConnection(connection);
        return meal;
    }

    public void addMealToList(int mealId, int amount, List<Meal> meals) {
        Meal toAdd = getMealWithAmount(mealId, amount);
        Connection connection = DbManager.getConnection();
        MySQLFactory factory = (MySQLFactory)FactoryDAO.getInstance();
        factory.setConnection(connection);
        CategoryDAO categoryDAO = factory.getCategoryDAO();
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
        DbManager.putConnection(connection);
    }

    public void removeMealFromList(int mealId, int amount, List<Meal> meals) {
        Meal toRemove = getMealWithAmount(mealId, amount);
        Meal toBeTotallyRemoved = null;
        for (Meal meal : meals) {
            if (meal.equals(toRemove)) {
                int rest = meal.getAmount() - toRemove.getAmount();
                if (rest == 0) {
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
    public List<Meal> getMealsForCategory(int category) {
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        factory.setConnection(connection);
        MealsDAO mealsDAO = factory.getMealsDAO();
        CategoryDAO categoryDAO = factory.getCategoryDAO();
        List<Meal> result = mealsDAO.getAllForCategory(categoryDAO.getForId(category).get());
        DbManager.putConnection(connection);
        return result;
    }

    @Override
    public List<MealCategory> getAllCategories() {
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        factory.setConnection(connection);
        CategoryDAO categoryDAO = factory.getCategoryDAO();
        List<MealCategory> result = categoryDAO.getAll();
        DbManager.putConnection(connection);
        return result;
    }

    @Override
    public MealCategory getForId(int category) {
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        factory.setConnection(connection);
        CategoryDAO categoryDAO = factory.getCategoryDAO();
        MealCategory result = categoryDAO.getForId(category).get();
        DbManager.putConnection(connection);
        return result;
    }
}
