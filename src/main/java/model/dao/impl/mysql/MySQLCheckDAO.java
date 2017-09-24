package model.dao.impl.mysql;

import model.dao.CheckDAO;
import model.entities.Check;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MySQLCheckDAO implements CheckDAO {

    private final String TABLE_NAME = "checks";
    private final String ID_COLUMN = "id";
    private final String ORDER_COLUMN = "id_order";
    private final String PRICE_COLUMN = "price";
    private final String TIME_PAID_COLUMN = "paid";
    private final String ADMIN_COLUMN = "id_admin";
    private final String INSERT_QUERY ="INSERT INTO checks (id_order, price, id_admin, paid) VALUES (?, ?, ?, ?)";
    private final String UPDATE_QUERY = "UPDATE  checks SET id_order = ?, price = ?, id_admin = ?, paid = ? WHERE id = ?";


    DbUtil<Check> util = new DbUtil<>(this::parseCheck,TABLE_NAME);

	@Override
	public List<Check> getAll() {
		return util.getAll();
	}

	@Override
	public Optional<Check> getForId(int id) {
        return util.getForId(id);
	}

	@Override
	public boolean insert(Check item) {
		Connection connection = DbManager.getConnection();
		try{
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY , Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, item.getOrder().getId());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getAdmin().getId());
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
		    throw  new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
    }

	@Override
	public int update(Check item) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, item.getOrder().getId());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getAdmin().getId());
            statement.setTimestamp(4, item.getPaid());
            statement.setInt(5, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
	}

    private Check parseCheck(ResultSet resultSet){
        try{
            return new Check(resultSet.getInt(ID_COLUMN), resultSet.getInt(ORDER_COLUMN),
                    resultSet.getInt(PRICE_COLUMN), resultSet.getTimestamp(TIME_PAID_COLUMN),
                        resultSet.getInt(ADMIN_COLUMN));
            }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
	
}
