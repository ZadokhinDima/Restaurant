package model.dao.impl.mysql;

import controller.util.BundleUtil;
import model.dao.CategoryDAO;
import model.entities.MealCategory;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MySQLCategoryDAO implements CategoryDAO {

	private final String TABLE_NAME = "menu_category";
	private final String ID_COLUMN = "id";
	private final String NAME_COLUMN = BundleUtil.getString("column.name");
	private final String INSERT_MESSAGE = "Unsupported operation : INSERT";
	private final String UPDATE_MESSAGE = "Unsupported operation : UPDATE";

    DbUtil<MealCategory> util = new DbUtil<>(this::parseMealCategory, TABLE_NAME);

	@Override
	public List<MealCategory> getAll() {
        return util.getAll();
    }

	@Override
	public Optional<MealCategory> getForId(int id) {
	    return util.getForId(id);
    }

	@Override
	public boolean insert(MealCategory item) {
		throw new UnsupportedOperationException(INSERT_MESSAGE);
	}

	@Override
	public int update(MealCategory item) {
		throw new UnsupportedOperationException(UPDATE_MESSAGE);
	}

    private MealCategory parseMealCategory(ResultSet resultSet){
        try{
            return new MealCategory(resultSet.getInt(ID_COLUMN),
                    resultSet.getString(NAME_COLUMN));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
