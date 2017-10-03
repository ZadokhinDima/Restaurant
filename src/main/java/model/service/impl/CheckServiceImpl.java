package model.service.impl;

import model.dao.CheckDAO;
import model.dao.FactoryDAO;
import model.dao.OrderDAO;
import model.dao.impl.mysql.DbManager;
import model.dao.impl.mysql.MySQLFactory;
import model.entities.*;
import model.exeptions.ConcurrentProcessingException;
import model.service.CheckService;
import model.service.OrderService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CheckServiceImpl implements CheckService{

    private static final Logger LOGGER = Logger.getLogger(CheckServiceImpl.class);

    @Override
    public List<Check> getChecks(User client){
        Connection connection = DbManager.getConnection();
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        factory.setConnection(connection);
        CheckDAO checkDAO = factory.getCheckDAO();
        List<Check> result = checkDAO.getAllChecksForUser(client);
        loadOrdersIntoChecs(result, factory);
        DbManager.putConnection(connection);
        return result;
    }

    private void loadOrdersIntoChecs(List<Check> checks, FactoryDAO factoryDAO){
        OrderDAO orderDAO = factoryDAO.getOrderDAO();
        for(Check check : checks){
            if(check.getOrder() == null){
                check.setOrder(orderDAO.getForId(check.getOrderId()).get());
            }
        }
    }

    @Override
    public void payCheck(int checkId){
        try(Connection connection = DbManager.getConnection()){
            connection.setAutoCommit(false);
            MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
            factory.setConnection(connection);
            CheckDAO checkDAO = factory.getCheckDAO();
            Check check = checkDAO.getForId(checkId).get();
            if(check.getPaid() == null){
                check.pay();
                checkDAO.update(check);
                connection.commit();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.check");
            }
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public Check acceptOrder(int orderId, User admin){
        try(Connection connection = DbManager.getConnection()) {
            connection.setAutoCommit(false);
            MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
            factory.setConnection(connection);
            OrderDAO orderDAO = factory.getOrderDAO();
            Order order = orderDAO.getForId(orderId).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(1);
            }
            else{
                throw new ConcurrentProcessingException("concurrent.order");
            }
            orderDAO.update(order);

            OrderService service = new OrderServiceImpl();
            int price = service.getSummaryPrice(order);

            Check check = new CheckBuilder()
                    .setAdmin(admin)
                    .setOrder(order)
                    .setPrice(price)
                    .createCheck();

            CheckDAO checkDAO = factory.getCheckDAO();
            if(!checkDAO.insert(check)){
                connection.rollback();
                return null;
            }
            else {
                connection.commit();
                return check;
            }
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void declineOrder(int orderId){
        try(Connection connection = DbManager.getConnection()){
            connection.setAutoCommit(false);
            MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
            factory.setConnection(connection);
            OrderDAO orderDAO = factory.getOrderDAO();
            Order order = orderDAO.getForId(orderId).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(-1);
                if(orderDAO.update(order) == 1){
                    connection.commit();
                }
                else throw new RuntimeException();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.order");
            }
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }



}
