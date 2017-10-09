package model.dao;

import controller.Config;
import org.apache.log4j.Logger;


public abstract class FactoryDAO {

    private static final Logger LOGGER = Logger.getLogger(FactoryDAO.class);
    private static FactoryDAO INSTANCE;


    public abstract ConnectionDAO getConnectionDAO();
	public abstract UserDAO getUserDAO(ConnectionDAO connectionDAO);
	public abstract OrderDAO getOrderDAO(ConnectionDAO connectionDAO);
	public abstract MealsDAO getMealsDAO(ConnectionDAO connectionDAO);
	public abstract LoginDAO getLoginDAO(ConnectionDAO connectionDAO);
	public abstract CheckDAO getCheckDAO(ConnectionDAO connectionDAO);
	public abstract CategoryDAO getCategoryDAO(ConnectionDAO connectionDAO);


	public static FactoryDAO getInstance(){
        if (INSTANCE == null) {
            synchronized (FactoryDAO.class) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = (FactoryDAO) Class.forName(Config.getInstance().getFactoryClass()).newInstance();
                    } catch (Exception e) {
                        LOGGER.error("Error in getting the instance of DaoFactory: ", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return INSTANCE;
    }
}
