package model.dao.impl.mysql;

import controller.util.BundleUtil;
import model.dao.MealsDAO;
import model.entities.Meal;
import model.entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MySQLMealsDAO implements MealsDAO{

    private final String ID_COLUMN = "id";
    private final String NAME_COLUMN = BundleUtil.getString("column.name");
    private final String PRICE_COLUMN = "price";
    private final String WEIGHT_COLUMN = "weight";
    private final String CATEGORY_COLUMN = "id_category";
    private final String IMAGE_COLUMN = "image";
    private final String MINUTES_COLUMN = "minutes_done";
    private final String TABLE_NAME = "meals";
    private final String UPDATE_QUERY = "UPDATE  meals SET price = ?, weight = ? WHERE id = ?";
    private final String INSERT_MESSAGE = "Unsupported operation : INSERT";


    @Override
    public List<Meal> getFromOrder(Order order) {
        return null;
    }

    private DbUtil<Meal> util = new DbUtil<>(this::parseMeal, TABLE_NAME);

	@Override
	public List<Meal> getAll() {
		return util.getAll();
	}

	@Override
	public Optional<Meal> getForId(int id) {
		return util.getForId(id);
	}

	@Override
	public boolean insert(Meal item) {
        throw new UnsupportedOperationException(INSERT_MESSAGE);
	}

	@Override
	public int update(Meal item) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, item.getPrice());
            statement.setInt(2, item.getWeight());
            statement.setInt(3, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
	}

	private Meal parseMeal(ResultSet resultSet){
        try{
            return new Meal(resultSet.getInt(ID_COLUMN), resultSet.getString(NAME_COLUMN),
                    resultSet.getInt(PRICE_COLUMN), resultSet.getInt(WEIGHT_COLUMN),
                    resultSet.getInt(CATEGORY_COLUMN), resultSet.getString(IMAGE_COLUMN),
                    resultSet.getInt(MINUTES_COLUMN));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
	

}
