package model.service.impl;


import model.dao.*;
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

    private FactoryDAO factory;
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);

    private OrderServiceImpl(){
        factory = FactoryDAO.getInstance();
    }

    private static class Holder{
        private static OrderServiceImpl INSTANCE = new OrderServiceImpl();
    }

    public static OrderServiceImpl getInstance(){
        return Holder.INSTANCE;
    }



    public List<Order> ordersOfUser(User user){
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        OrderDAO dao = factory.getOrderDAO(connectionDAO);
        List<Order> result = dao.getForUser(user);
        connectionDAO.close();
        return result;
    }

    public List<Meal> getOrderMeals(int orderId){
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
        Optional<Order> order = orderDAO.getForId(orderId);
        if(order.isPresent()){
            MealsDAO mealsDAO = factory.getMealsDAO(connectionDAO);
            List<Meal> result =  mealsDAO.getFromOrder(order.get());
            loadCategories(result, connectionDAO);
            connectionDAO.close();
            return result;
        }
        else{
            LOGGER.error("Attempt to get non-existent order");
            connectionDAO.close();
            throw new RuntimeException();
        }
    }

    private void loadCategories(List<Meal> meals, ConnectionDAO connectionDAO){
        CategoryDAO categoryDAO = factory.getCategoryDAO(connectionDAO);
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


        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
        orderDAO.insert(toCreate);
        connectionDAO.close();
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
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
        Order order = orderDAO.getForId(orderId).get();
        connectionDAO.close();
        return order.getClientId() == client.getId();
    }

    @Override
    public Order getFullInfoAboutOrder(int orderId) {
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
        Order result = orderDAO.getForId(orderId).get();
        UserDAO userDAO = factory.getUserDAO(connectionDAO);
        User client = userDAO.getForId(result.getClientId()).get();
        result.setClient(client);
        result.setMeals(getOrderMeals(result.getId()));
        connectionDAO.close();
        return result;
    }

    @Override
    public List<Order> getActiveOrders() {
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);

        List<Order> result = orderDAO.getAll().stream()
                .filter(order -> order.getAccepted()==0)
                .collect(Collectors.toList());

        connectionDAO.close();
        return result;
    }
}
