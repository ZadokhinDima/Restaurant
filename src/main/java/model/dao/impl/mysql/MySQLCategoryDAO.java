package model.dao.impl.mysql;


import model.dao.CategoryDAO;
import model.entities.MealCategory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLCategoryDAO implements CategoryDAO {

	private Connection connection;


	private static final String ID_COLUMN = "menu_category.id";
	private static final String NAME_COLUMN = "menu_category.name";

	private static final String SELECT_ALL_QUERY = "SELECT * FROM menu_category ORDER BY id";
	private static final String SELECT_FOR_ID_QUERY = "SELECT * FROM menu_category WHERE id = ?";
	private static final String INSERT_MESSAGE = "Unsupported operation : INSERT";
	private static final String UPDATE_MESSAGE = "Unsupported operation : UPDATE";
	private static final Logger LOGGER = Logger.getLogger(MySQLCategoryDAO.class);


    public MySQLCategoryDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
	public List<MealCategory> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            return parseMealCategoryList(resultSet);
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

	@Override
	public Optional<MealCategory> getForId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_FOR_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseMealCategory(resultSet));
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
	public boolean insert(MealCategory item) {
		LOGGER.error(INSERT_MESSAGE, new UnsupportedOperationException(INSERT_MESSAGE));
		throw new UnsupportedOperationException(INSERT_MESSAGE);
	}

	@Override
	public int update(MealCategory item) {
		LOGGER.error(UPDATE_MESSAGE, new UnsupportedOperationException(UPDATE_MESSAGE));
		throw new UnsupportedOperationException(UPDATE_MESSAGE);
	}

    static MealCategory parseMealCategory(ResultSet resultSet){
        try{
            return new MealCategory(resultSet.getInt(ID_COLUMN),
                    resultSet.getString(NAME_COLUMN));
        }
        catch (SQLException e){
        	LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private List<MealCategory> parseMealCategoryList(ResultSet resultSet){
        try {
            List<MealCategory> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(parseMealCategory(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
