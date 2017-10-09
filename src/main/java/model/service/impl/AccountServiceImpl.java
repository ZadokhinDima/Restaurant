package model.service.impl;

import model.dao.ConnectionDAO;
import model.dao.FactoryDAO;
import model.dao.LoginDAO;
import model.dao.UserDAO;
import model.dao.impl.mysql.MySQLFactory;
import model.entities.Login;
import model.entities.User;
import model.exeptions.EmailExistsException;
import model.service.AccountService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private FactoryDAO factory;

    private AccountServiceImpl() {
        factory = FactoryDAO.getInstance();
    }

    private static class Holder {
        private static AccountServiceImpl INSTANCE = new AccountServiceImpl();
    }

    public static AccountServiceImpl getInstance() {
        return Holder.INSTANCE;
    }


    public Optional<User> logIn(String email, String password) {
        ConnectionDAO connectionDAO = factory.getConnectionDAO();
        LoginDAO loginDAO = factory.getLoginDAO(connectionDAO);
        Optional<Login> login = loginDAO.findAccount(email, password);
        if (login.isPresent()) {
            UserDAO userDAO = factory.getUserDAO(connectionDAO);
            Optional<User> result = userDAO.getUserByAccount(login.get());
            connectionDAO.close();
            return result;
        }
        connectionDAO.close();
        return Optional.empty();
    }


    public void registerUser(Login login, User user) throws EmailExistsException {
        try(ConnectionDAO connectionDAO = factory.getConnectionDAO()) {
            connectionDAO.beginTransaction();
            LoginDAO loginDAO = factory.getLoginDAO(connectionDAO);
            if (loginDAO.insert(login)) {
                user.setId(login.getId());
                UserDAO userDAO = factory.getUserDAO(connectionDAO);
                if (userDAO.insert(user)) {
                    connectionDAO.commitTransaction();
                } else {
                    connectionDAO.rollbackTransaction();
                }
            }
        }
    }
}
