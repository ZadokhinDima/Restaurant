package model.service.impl;

import model.dao.FactoryDAO;
import model.dao.LoginDAO;
import model.dao.UserDAO;
import model.dao.impl.mysql.DbManager;
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

    private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class);

    private AccountServiceImpl() {

    }

    private static class Holder {
        private static AccountServiceImpl INSTANCE = new AccountServiceImpl();
    }

    public static AccountServiceImpl getInstance() {
        return Holder.INSTANCE;
    }


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


    public void registerUser(Login login, User user) throws EmailExistsException {
        MySQLFactory factory = (MySQLFactory) FactoryDAO.getInstance();
        Connection connection = DbManager.getConnection();
        try {
            connection.setAutoCommit(false);
            factory.setConnection(connection);
            LoginDAO loginDAO = factory.getLoginDAO();
            if (loginDAO.insert(login)) {
                user.setId(login.getId());
                UserDAO userDAO = factory.getUserDAO();
                if (userDAO.insert(user)) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.error(e);
            throw new EmailExistsException();
        } catch (SQLException e){
            LOGGER.error(e);
        }

        finally {
            DbManager.putConnection(connection);
        }
    }
}
