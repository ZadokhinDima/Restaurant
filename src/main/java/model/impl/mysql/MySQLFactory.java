package model.impl.mysql;

import model.dao.*;

public class MySQLFactory extends FactoryDAO{

	//TODO: Create connection pool and use it as parameter in DAOs.
	
	@Override
	public UserDAO getUserDAO() {
		return new MySQLUserDAO();
	}

	@Override
	public OrderDAO getOrderDAO() {
		return new MySQLOrderDAO();
	}

	@Override
	public MealsDAO getMealsDAO() {
		return new MySQLMealsDAO();
	}

	@Override
	public LoginDAO getLoginDAO() {
		return new MySQLLoginDAO();
	}

	@Override
	public CheckDAO getCheckDAO() {
		return new MySQLCheckDAO();
	}

	@Override
	public CategoryDAO getCategoryDAO() {
		return  new MySQLCategoryDAO();
	}
	
}
