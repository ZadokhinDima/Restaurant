package model.dao.impl.mysql;

import model.dao.UserDAO;
import model.entities.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;


public class MySQLUserDAO implements UserDAO {

    private final String TABLE_NAME = "users";
    private final String ID_COLUMN = "id";
    private final String NAME_COLUMN = "name";
    private final String BIRTH_DATE_COLUMN = "birth_date";
    private final String ROLE_COLUMN = "role";
    private final String INSERT_QUERY = "INSERT INTO users (id, name, birthdate, role) VALUES (?, ?, ?, ?)";
    private final String UPDATE_QUERY = "UPDATE  users SET name = ?, birthdate = ? WHERE id = ?";
    private final String LOGIN_QUERY = "SELECT id FROM login WHERE email = ? AND password = ?";


    private DbUtil<User> util = new DbUtil<>(this::parseUser, TABLE_NAME);

	@Override
	public List<User> getAll() {
		return util.getAll();
	}

	@Override
	public Optional<User> getForId(int id) {
		return util.getForId(id);
	}

    @Override
    public Optional<User> logIn(String login, String password) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){

            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
	public boolean insert(User item) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, item.getId());
            statement.setString(2, item.getName());
            statement.setDate(3, item.getBirthDate());
            statement.setString(4, item.getRole().name().toLowerCase());
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
	public int update(User item) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, item.getName());
            statement.setDate(2, item.getBirthDate());
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

	private User parseUser(ResultSet resultSet){
        try{
            return new User(resultSet.getInt(ID_COLUMN), resultSet.getString(NAME_COLUMN),
                    resultSet.getDate(BIRTH_DATE_COLUMN), resultSet.getString(ROLE_COLUMN));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}
