package model.dao.impl.mysql;

import model.dao.*;

import java.sql.Connection;

public class MySQLFactory extends FactoryDAO{

	Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAO(connection);
	}

	@Override
	public OrderDAO getOrderDAO() {
		return new MySQLOrderDAO(connection);
	}

	@Override
	public MealsDAO getMealsDAO() {
		return new MySQLMealsDAO(connection);
	}

	@Override
	public LoginDAO getLoginDAO() {
		return new MySQLLoginDAO(connection);
	}

	@Override
	public CheckDAO getCheckDAO() {
		return new MySQLCheckDAO(connection);
	}

	@Override
	public CategoryDAO getCategoryDAO() {
		return  new MySQLCategoryDAO(connection);
	}
	
}
