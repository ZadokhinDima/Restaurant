package model.dao.impl.mysql;

import model.dao.MealsDAO;
import model.entities.Meal;
import model.entities.MealBuilder;
import model.entities.MealCategory;
import model.entities.Order;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLMealsDAO implements MealsDAO{

    private Connection connection;

    private static final String ID_COLUMN = "meals.id";
    private static final String CATEGORY_COLUMN = "meals.id_category";
    private static final String NAME_COLUMN = "meals.name";
    private static final String PRICE_COLUMN = "meals.price";
    private static final String AMOUNT_COLUMN = "amount";
    private static final String WEIGHT_COLUMN = "meals.weight";


    private static final String SELECT_ALL_QUERY = "SELECT * FROM meals";
    private static final String SELECT_FOR_ID_QUERY = "SELECT * FROM meals WHERE  meals.id = ?";
    private static final String UPDATE_QUERY = "UPDATE  meals SET price = ?, weight = ? WHERE id = ?";
    private static final String SEARCH_FOR_CATEGORY_QUERY = "SELECT * FROM meals WHERE id_category = ?";
    private static final String ORDER_MEALS_QUERY = "SELECT * FROM meals" +
            " JOIN meals_in_order ON (id = meals_in_order.id_meal)" +
            "WHERE id_order = ?";
    private static final String INSERT_MESSAGE = "Unsupported operation : INSERT";

    private static final Logger LOGGER = Logger.getLogger(MySQLMealsDAO.class);


    public MySQLMealsDAO(Connection connection) {
        this.connection = connection;
    }


	@Override
	public List<Meal> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            return parseMealsList(resultSet);
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	@Override
	public Optional<Meal> getForId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_FOR_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseMeal(resultSet));
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
	public boolean insert(Meal item) {
        throw new UnsupportedOperationException(INSERT_MESSAGE);
	}

	@Override
	public int update(Meal item) {
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, item.getPrice());
            statement.setInt(2, item.getWeight());
            statement.setInt(3, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
	}

    @Override
    public List<Meal> getFromOrder(Order order) {
        try{
            PreparedStatement statement = connection.prepareStatement(ORDER_MEALS_QUERY);
            statement.setInt(1, order.getId());
            List<Meal> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){

                Meal meal =  new MealBuilder().setId(resultSet.getInt(ID_COLUMN)).
                        setName(resultSet.getString(NAME_COLUMN)).
                        setPrice(resultSet.getInt(PRICE_COLUMN)).
                        setWeight(resultSet.getInt(WEIGHT_COLUMN)).
                        setAmount(resultSet.getInt(AMOUNT_COLUMN)).
                        setCategoryId(resultSet.getInt(CATEGORY_COLUMN)).
                        createMeal();
                result.add(meal);
            }
            order.setMeals(result);
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<Meal> getAllForCategory(MealCategory category) {
        try {
            List<Meal> result = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(SEARCH_FOR_CATEGORY_QUERY);
            statement.setInt(1, category.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Meal meal= parseMeal(resultSet);
                meal.setCategory(category);
                result.add(meal);
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private Meal parseMeal(ResultSet resultSet){
        try{
            return new MealBuilder().setId(resultSet.getInt(ID_COLUMN)).
                            setName(resultSet.getString(NAME_COLUMN)).
                            setPrice(resultSet.getInt(PRICE_COLUMN)).
                            setWeight(resultSet.getInt(WEIGHT_COLUMN)).
                            setCategoryId(resultSet.getInt(CATEGORY_COLUMN)).
                            createMeal();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private List<Meal> parseMealsList(ResultSet resultSet){
        try {
            List<Meal> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(parseMeal(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

}
