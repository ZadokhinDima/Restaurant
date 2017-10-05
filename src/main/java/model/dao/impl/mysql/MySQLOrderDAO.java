package model.dao.impl.mysql;

import model.dao.OrderDAO;
import model.entities.Meal;
import model.entities.Order;
import model.entities.OrderBuilder;
import model.entities.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLOrderDAO implements OrderDAO {

    private Connection connection;


    private static final String ID_COLUMN = "orders.id";
    private static final String CLIENT_COLUMN = "orders.id_client";
    private static final String TIME_ORDER_COLUMN = "orders.order_time";
    private static final String ACCEPTED_COLUMN = "orders.accepted";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM orders ORDER BY order_time";
    private static final String SELECT_FOR_ID_QUERY = "SELECT * FROM orders WHERE orders.id = ? ";
    private static final String INSERT_QUERY = "INSERT INTO orders (id_client, order_time, accepted) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE  orders SET id_client = ?, order_time = ?, accepted = ? WHERE id = ?";
    private static final String USER_ORDERS_QUERY = "SELECT * FROM orders WHERE id_client = ? ORDER BY order_time DESC";
    private static final String INSERT_MEAL_TO_ORDER_QUERY = "INSERT INTO meals_in_order (id_meal, id_order, amount) VALUES (?, ?, ?)";
;
    private static final Logger LOGGER = Logger.getLogger(MySQLOrderDAO.class);


    public MySQLOrderDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
	public List<Order> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            return parseOrderList(resultSet);
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	@Override
	public Optional<Order> getForId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_FOR_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseOrder(resultSet));
            }
            else {
                return Optional.empty();
            }
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	@Override
	public boolean insert(Order item) {
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, item.getClient().getId());
            statement.setTimestamp(2, item.getOrdered());
            statement.setInt(3, item.getAccepted());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                item.setId(resultSet.getInt(1));
                if(insertMealsToOrder(item)){
                    connection.commit();
                    return true;
                }
                else {
                    connection.rollback();
                    return false;
                }
            }
            else return false;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
	}

	private boolean insertMealsToOrder(Order order) throws SQLException{
        System.out.println(order.getMeals());
        for(Meal meal : order.getMeals()){
            PreparedStatement statement = connection.prepareStatement(INSERT_MEAL_TO_ORDER_QUERY);
	        statement.setInt(1, meal.getId());
	        statement.setInt(2, order.getId());
	        statement.setInt(3, meal.getAmount());
            if(!(statement.executeUpdate() > 0)){
                return false;
            }
        }
        return true;
    }

	@Override
	public int update(Order item) {
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, item.getClientId());
            statement.setTimestamp(2, item.getOrdered());
            statement.setInt(3, item.getAccepted());
            statement.setInt(4, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
	}

    @Override
    public List<Order> getForUser(User user) {
        try{
            PreparedStatement statement = connection.prepareStatement(USER_ORDERS_QUERY);
            statement.setInt(1, user.getId());
            List<Order> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(parseOrder(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
    }

    protected static Order parseOrder(ResultSet resultSet){
		try{
			return new OrderBuilder()
                    .setId(resultSet.getInt(ID_COLUMN))
                    .setOrdered(resultSet.getTimestamp(TIME_ORDER_COLUMN))
                    .setAccepted(resultSet.getInt(ACCEPTED_COLUMN))
                    .setClientId(resultSet.getInt(CLIENT_COLUMN))
                    .createOrder();
		}
		catch (SQLException e){
            LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

    protected static List<Order> parseOrderList(ResultSet resultSet){
        try {
            List<Order> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(parseOrder(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
	
}
