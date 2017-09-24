package model.dao.impl.mysql;

import model.dao.OrderDAO;
import model.entities.Meal;
import model.entities.Order;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MySQLOrderDAO implements OrderDAO {

    private final String TABLE_NAME = "orders";
    private final String ID_COLUMN = "id";
    private final String CLIENT_COLUMN = "id_client";
    private final String TIME_ORDER_COLUMN = "order_time";
    private final String TIME_DONE_COLUMN = "done_time";
    private final String INSERT_QUERY = "INSERT INTO orders (id_client, order_time, done_time) VALUES (?, ?, ?)";
    private final String UPDATE_QUERY = "UPDATE  orders SET id_client = ?, order_time = ?, done_time = ? WHERE id = ?";

    private DbUtil<Order> util = new DbUtil<>(this::parseOrder, TABLE_NAME);

	@Override
	public List<Order> getAll() {
		return util.getAll();
	}

	@Override
	public Optional<Order> getForId(int id) {
		return util.getForId(id);
	}

	@Override
	public boolean insert(Order item) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, item.getClient().getId());
            statement.setTimestamp(2, item.getOrdered());
            statement.setTimestamp(3, item.getDone());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                item.setId(resultSet.getInt(1));
                return true;
            }
            else return false;
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
	}

	@Override
	public int update(Order item) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, item.getClient().getId());
            statement.setTimestamp(2, item.getOrdered());
            statement.setTimestamp(3, item.getDone());
            statement.setInt(4, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
	}


	private Order parseOrder(ResultSet resultSet){
		try{
			return new Order(resultSet.getInt(ID_COLUMN), resultSet.getInt(CLIENT_COLUMN),
                    resultSet.getTimestamp(TIME_ORDER_COLUMN), resultSet.getTimestamp(TIME_DONE_COLUMN));
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
}
