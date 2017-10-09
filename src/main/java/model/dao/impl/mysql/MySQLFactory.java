package model.dao.impl.mysql;

import model.dao.*;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLFactory extends FactoryDAO{

	private static final Logger LOGGER = Logger.getLogger(MySQLFactory.class);
	private DataSource dataSource;


	public MySQLFactory(){
		try {
			InitialContext initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/restaurant");
		} catch (NamingException e) {
			LOGGER.error("Error in looking up the data source: ", e);
			throw new RuntimeException(e);
		}
	}


	@Override
	public ConnectionDAO getConnectionDAO() {
		try {
			return new MySQLConnection(dataSource.getConnection());
		} catch (SQLException e) {
			LOGGER.error("Error during the getting connection with connection pool: ", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDAO getUserDAO(ConnectionDAO connectionDAO) {
		MySQLConnection connection = (MySQLConnection)connectionDAO;
		return new MySQLUserDAO(connection.getConnection());
	}

	@Override
	public OrderDAO getOrderDAO(ConnectionDAO connectionDAO) {
		MySQLConnection connection = (MySQLConnection)connectionDAO;
		return new MySQLOrderDAO(connection.getConnection());
	}

	@Override
	public MealsDAO getMealsDAO(ConnectionDAO connectionDAO) {
		MySQLConnection connection = (MySQLConnection)connectionDAO;
		return new MySQLMealsDAO(connection.getConnection());
	}

	@Override
	public LoginDAO getLoginDAO(ConnectionDAO connectionDAO) {
		MySQLConnection connection = (MySQLConnection)connectionDAO;
		return new MySQLLoginDAO(connection.getConnection());
	}

	@Override
	public CheckDAO getCheckDAO(ConnectionDAO connectionDAO) {
		MySQLConnection connection = (MySQLConnection)connectionDAO;
		return new MySQLCheckDAO(connection.getConnection());
	}

	@Override
	public CategoryDAO getCategoryDAO(ConnectionDAO connectionDAO) {
		MySQLConnection connection = (MySQLConnection)connectionDAO;
		return new MySQLCategoryDAO(connection.getConnection());
	}
}
