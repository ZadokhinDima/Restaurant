package model.dao.impl.mysql;

import model.dao.UserDAO;
import model.entities.Login;
import model.entities.User;
import model.entities.UserBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MySQLUserDAO implements UserDAO {

    private Connection connection;


    private static final String ID_COLUMN = "users.id";
    private static final String NAME_COLUMN = "users.name";
    private static final String BIRTH_DATE_COLUMN = "users.birthdate";
    private static final String ROLE_COLUMN = "users.role";

    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String SELECT_FOR_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO users (id, name, birthdate, role) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE  users SET name = ?, birthdate = ? WHERE id = ?";

    private static final Logger LOGGER = Logger.getLogger(MySQLUserDAO.class);



    public MySQLUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
	public List<User> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            return parseUsersList(resultSet);
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	@Override
	public Optional<User> getForId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_FOR_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseUser(resultSet));
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
	public boolean insert(User item) {
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setInt(1, item.getId());
            statement.setString(2, item.getName());
            statement.setDate(3, item.getBirthDate());
            statement.setString(4, item.getRole().name().toLowerCase());
            return statement.executeUpdate() > 0;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
	}

	@Override
	public int update(User item) {
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, item.getName());
            statement.setDate(2, item.getBirthDate());
            statement.setInt(3, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
	}

    @Override
    public Optional<User> getUserByAccount(Login login) {
        return getForId(login.getId());
    }

    static User parseUser(ResultSet resultSet){
        try{
            return new UserBuilder()
                    .setId(resultSet.getInt(ID_COLUMN))
                    .setName(resultSet.getString(NAME_COLUMN))
                    .setBirthDate(resultSet.getDate(BIRTH_DATE_COLUMN))
                    .setRole(resultSet.getString(ROLE_COLUMN))
                    .createUser();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private List<User> parseUsersList(ResultSet resultSet){
        try {
            List<User> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(parseUser(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }


}
