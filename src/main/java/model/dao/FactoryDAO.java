package model.dao;

import controller.Config;

public abstract class FactoryDAO {
	public abstract UserDAO getUserDAO();
	public abstract OrderDAO getOrderDAO();
	public abstract MealsDAO getMealsDAO();
	public abstract LoginDAO getLoginDAO();
	public abstract CheckDAO getCheckDAO();
	public abstract CategoryDAO getCategoryDAO();
	public static FactoryDAO getInstance(){
        try {
            String className = Config.getInstance().getFactoryClass();
            return  (FactoryDAO) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
