package model.service.impl;


import model.dao.*;
import model.dao.impl.mysql.DbManager;
import model.dao.impl.mysql.MySQLFactory;
import model.entities.*;
import model.service.OrderService;

import org.apache.log4j.Logger;

import java.sql.Connection;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService{

    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    public List<Order> ordersOfUser(User user){
        Connection connection = DbManager.getConnection();
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        factory.setConnection(connection);
        OrderDAO dao = factory.getOrderDAO();
        List<Order> result = dao.getForUser(user);
        DbManager.putConnection(connection);
        return result;
    }

    public List<Meal> getOrderMeals(int orderId){
        Connection connection = DbManager.getConnection();
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        factory.setConnection(connection);
        OrderDAO orderDAO = factory.getOrderDAO();
        Optional<Order> order = orderDAO.getForId(orderId);
        if(order.isPresent()){
            MealsDAO mealsDAO = factory.getMealsDAO();
            List<Meal> result =  mealsDAO.getFromOrder(order.get());
            loadCategories(result, factory);
            DbManager.putConnection(connection);
            return result;
        }
        else{
            LOGGER.error("Attempt to get non-existent order");
            DbManager.putConnection(connection);
            throw new RuntimeException();
        }
    }

    private void loadCategories(List<Meal> meals, FactoryDAO factory){
        CategoryDAO categoryDAO = factory.getCategoryDAO();
        for(Meal meal : meals){
            if(meal.getCategory() == null){
                meal.setCategory(categoryDAO.getForId(meal.getCategoryId()).get());
            }
        }
    }

    public int createOrder(User client, List<Meal> meals){
        Order toCreate =  new OrderBuilder().setClient(client)
                .setOrdered(new Timestamp(new Date().getTime()))
                .setMeals(meals)
                .createOrder();

        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        factory.setConnection(connection);
        OrderDAO dao = factory.getOrderDAO();
        dao.insert(toCreate);
        DbManager.putConnection(connection);
        return toCreate.getId();
    }

    @Override
    public int getSummaryPrice(Order order){
        if(order.getMeals() == null){
            order.setMeals(getOrderMeals(order.getId()));
        }
        int result = 0;
        for(Meal meal : order.getMeals()){
            result += meal.getPrice() * meal.getAmount();
        }
        return result;
    }

    @Override
    public boolean checkClientRightsOnOrder(int orderId, User client) {
        Connection connection = DbManager.getConnection();
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        factory.setConnection(connection);
        OrderDAO orderDAO = factory.getOrderDAO();
        Order order = orderDAO.getForId(orderId).get();
        DbManager.putConnection(connection);
        return order.getClientId() == client.getId();
    }

    @Override
    public Order getFullInfoAboutOrder(int orderId) {
        Connection connection = DbManager.getConnection();
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        factory.setConnection(connection);
        OrderDAO orderDAO = factory.getOrderDAO();
        Order result = orderDAO.getForId(orderId).get();
        UserDAO userDAO = factory.getUserDAO();
        User client = userDAO.getForId(result.getClientId()).get();
        result.setClient(client);
        result.setMeals(getOrderMeals(result.getId()));
        DbManager.putConnection(connection);
        return result;
    }

    @Override
    public List<Order> getActiveOrders() {
        Connection connection = DbManager.getConnection();
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        factory.setConnection(connection);
        OrderDAO orderDAO = factory.getOrderDAO();

        List<Order> result = orderDAO.getAll().stream()
                .filter(order -> order.getAccepted()==0)
                .collect(Collectors.toList());

        DbManager.putConnection(connection);
        return result;
    }
}
