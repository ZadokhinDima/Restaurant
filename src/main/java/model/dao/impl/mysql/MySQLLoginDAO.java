package model.dao.impl.mysql;

import model.dao.LoginDAO;
import model.entities.Login;
import model.entities.LoginBuilder;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLLoginDAO implements LoginDAO{

    private Connection connection;


    private static final String ID_COLUMN = "id";
    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "password";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM login";
    private static final String SELECT_FOR_ID_QUERY = "SELECT * FROM login WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO login (email, password) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE  login SET email = ?, password = ? WHERE id = ?";
    private static final String LOGIN_QUERY = "SELECT * FROM login WHERE email = ? AND  password = ?";
    private static final Logger LOGGER = Logger.getLogger(MySQLLoginDAO.class);



    public MySQLLoginDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
	public List<Login> getAll() {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            return parseLoginList(resultSet);
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
	}

	@Override
	public Optional<Login> getForId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_FOR_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseLogin(resultSet));
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
	public boolean insert(Login item) {
        try{
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setString(2, item.getPassword());
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
            throw new RuntimeException(e);
        }
	}

	@Override
	public int update(Login item) {
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, item.getName());
            statement.setString(2, item.getPassword());
            statement.setInt(3, item.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
	}

    @Override
    public Optional<Login> findAccount(String email, String password) {
        try{
            PreparedStatement statement = connection.prepareStatement(LOGIN_QUERY);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Optional.of(parseLogin(resultSet));
            }
            else return Optional.empty();
        }
        catch(SQLException e){
            LOGGER.error(e);
            throw  new RuntimeException(e);
        }
    }

    private Login parseLogin(ResultSet resultSet){
        try{
            return new LoginBuilder()
                    .setId(resultSet.getInt(ID_COLUMN))
                    .setName(resultSet.getString(EMAIL_COLUMN))
                    .setPassword(resultSet.getString(PASSWORD_COLUMN))
                    .createLogin();
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private List<Login> parseLoginList(ResultSet resultSet){
        try {
            List<Login> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(parseLogin(resultSet));
            }
            return result;
        }
        catch (SQLException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

}
