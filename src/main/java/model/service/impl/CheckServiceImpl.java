package model.service.impl;

import model.dao.*;
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

    private FactoryDAO factory;
    private static final Logger LOGGER = Logger.getLogger(CheckServiceImpl.class);

    private CheckServiceImpl(){
        factory = FactoryDAO.getInstance();
    }

    private static class Holder {
        private static CheckServiceImpl INSTANCE = new CheckServiceImpl();
    }

    public static CheckServiceImpl getInstance(){
        return Holder.INSTANCE;
    }




    @Override
    public List<Check> getChecks(User client){
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        CheckDAO checkDAO = factory.getCheckDAO(connectionDAO);
        List<Check> result = checkDAO.getAllChecksForUser(client);
        loadOrdersIntoChecks(result, connectionDAO);
        loadAdminsIntoChecks(result, connectionDAO);
        connectionDAO.close();
        return result;
    }

    private void loadOrdersIntoChecks(List<Check> checks, ConnectionDAO connectionDAO){
        OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
        for(Check check : checks){
            if(check.getOrder() == null){
                check.setOrder(orderDAO.getForId(check.getOrderId()).get());
            }
        }
    }

    private void loadAdminsIntoChecks(List<Check> checks, ConnectionDAO connectionDAO){
        UserDAO userDAO = factory.getUserDAO(connectionDAO);
        for(Check check : checks){
            if(check.getAdmin() == null){
                check.setAdmin(userDAO.getForId(check.getAdminId()).get());
            }
        }
    }
    @Override
    public void payCheck(int checkId){
        try(ConnectionDAO connectionDAO = factory.getConnectionDAO()){
            connectionDAO.beginTransaction();
            CheckDAO checkDAO = factory.getCheckDAO(connectionDAO);
            Check check = checkDAO.getForId(checkId).get();
            if(check.getPaid() == null){
                check.pay();
                checkDAO.update(check);
                connectionDAO.commitTransaction();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.check");
            }
        }
    }


    @Override
    public Check acceptOrder(Order order, User admin){
        try(ConnectionDAO connectionDAO = factory.getConnectionDAO()) {
            connectionDAO.beginTransaction();
            OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);


            order = orderDAO.getForId(order.getId()).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(1);
            }
            else{
                throw new ConcurrentProcessingException("concurrent.order");
            }
            orderDAO.update(order);

            OrderService service = OrderServiceImpl.getInstance();
            int price = service.getSummaryPrice(order);

            Check check = new CheckBuilder()
                    .setAdmin(admin)
                    .setOrder(order)
                    .setPrice(price)
                    .createCheck();

            CheckDAO checkDAO = factory.getCheckDAO(connectionDAO);

            if(!checkDAO.insert(check)){
                connectionDAO.rollbackTransaction();
                return null;
            }
            else {
                connectionDAO.commitTransaction();
                return check;
            }
        }
    }


    @Override
    public void declineOrder(int orderId){
        try(ConnectionDAO connectionDAO = factory.getConnectionDAO()){
            connectionDAO.beginTransaction();
            OrderDAO orderDAO = factory.getOrderDAO(connectionDAO);
            Order order = orderDAO.getForId(orderId).get();
            if(order.getAccepted() == 0) {
                order.setAccepted(-1);
                if(orderDAO.update(order) == 1){
                    connectionDAO.commitTransaction();
                }
                else throw new RuntimeException();
            }
            else {
                throw new ConcurrentProcessingException("concurrent.order");
            }
        }
    }



}
