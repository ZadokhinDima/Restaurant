package model.service.impl;

import model.dao.FactoryDAO;
import model.dao.LoginDAO;
import model.dao.UserDAO;
import model.dao.impl.mysql.DbManager;
import model.dao.impl.mysql.MySQLFactory;
import model.entities.Login;
import model.entities.User;
import model.service.AccountService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class);

    public Optional<User> logIn(String email, String password) {
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        factory.setConnection(connection);
        LoginDAO loginDAO = factory.getLoginDAO();
        Optional<Login> login = loginDAO.findAccount(email, password);
        if (login.isPresent()) {
            UserDAO userDAO = factory.getUserDAO();
            Optional<User> result = userDAO.getUserByAccount(login.get());
            DbManager.putConnection(connection);
            return result;
        }
        DbManager.putConnection(connection);
        return Optional.empty();
    }


    public boolean registerUser(Login login, User user) {
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        try {
            connection.setAutoCommit(false);
            factory.setConnection(connection);
            LoginDAO loginDAO = factory.getLoginDAO();
            if (loginDAO.insert(login)) {
                user.setId(login.getId());
                UserDAO userDAO = factory.getUserDAO();
                if(userDAO.insert(user)) {
                    connection.commit();
                    return true;
                }
                else {
                    connection.rollback();
                    return false;
                }

            }else return false;
        } catch (Exception e) {
            return false;
        }
        finally {
            DbManager.putConnection(connection);
        }
    }
}
