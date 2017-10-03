package model.dao.impl.mysql;

import model.dao.CheckDAO;
import model.entities.Check;
import model.entities.CheckBuilder;
import model.entities.Order;
import model.entities.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLCheckDAO implements CheckDAO {

    private Connection connection;


    private static final String ORDER_COLUMN = "checks.id_order";
    private static final String ADMIN_COLUMN = "checks.id_admin";
    private static final String ID_COLUMN = "checks.id";
    private static final String PRICE_COLUMN = "checks.price";
    private static final String TIME_PAID_COLUMN = "checks.paid";


    private static final String SELECT_ALL_QUERY = "SELECT * FROM checks";
    private static final String SELECT_FOR_ID_QUERY = "SELECT * FROM checks WHERE id = ?";
    private static final String INSERT_QUERY ="INSERT INTO checks (id_order, price, id_admin, paid) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE  checks SET id_order = ?, price = ?, id_admin = ?, paid = ? WHERE id = ?";
    private static final String USER_CHECKS_QUERY = "SELECT checks.id AS id, id_order, price, paid, id_admin FROM checks JOIN orders ON checks.id_order = orders.id WHERE id_client = ? ORDER BY orders.order_time DESC";
    private static final Logger LOGGER = Logger.getLogger(MySQLCheckDAO.class);



    public MySQLCheckDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
	public List<Check> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            return parseChecksList(resultSet);
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	@Override
	public Optional<Check> getForId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_FOR_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseCheck(resultSet));
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
	public boolean insert(Check item) {
		try{
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY , Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, item.getOrderId());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getAdminId());
            statement.setTimestamp(4, item.getPaid());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                item.setId(resultSet.getInt(1));
                return true;
            }
            else return false;
        }
        catch (SQLException e){
		    LOGGER.error(e);
		    throw  new RuntimeException(e);
        }
    }

	@Override
	public int update(Check item) {
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, item.getOrderId());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getAdminId());
            statement.setTimestamp(4, item.getPaid());
            statement.setInt(5, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
	}

    @Override
    public List<Check> getAllChecksForUser(User user) {
        try{
            List<Check> result = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(USER_CHECKS_QUERY);
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result.add(parseCheck(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
    }

    private Check parseCheck(ResultSet resultSet){
        try{

            return new CheckBuilder()
                    .setId(resultSet.getInt(ID_COLUMN))
                    .setPrice(resultSet.getInt(PRICE_COLUMN))
                    .setPaid(resultSet.getTimestamp(TIME_PAID_COLUMN))
                    .setOrderId(resultSet.getInt(ORDER_COLUMN))
                    .setAdminId(resultSet.getInt(ADMIN_COLUMN))
                    .createCheck();
            }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private List<Check> parseChecksList(ResultSet resultSet){
        try {
            List<Check> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(parseCheck(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
	
}
