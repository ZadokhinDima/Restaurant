package model.dao.impl.mysql;

import model.dao.LoginDAO;
import model.entities.Login;


import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MySQLLoginDAO implements LoginDAO{

    private final String TABLE_NAME = "login";
    private final String ID_COLUMN = "id";
    private final String EMAIL_COLUMN = "email";
    private final String PASSWORD_COLUMN = "birth_date";
    private final String INSERT_QUERY = "INSERT INTO login (email, password) VALUES (?, ?)";
    private final String UPDATE_QUERY = "UPDATE  login SET email = ?, password = ? WHERE id = ?";

    DbUtil<Login> util = new DbUtil<>(this::parseLogin,TABLE_NAME);

	@Override
	public List<Login> getAll() {
        return util.getAll();
	}

	@Override
	public Optional<Login> getForId(int id) {
        return util.getForId(id);
	}

	 

	@Override
	public boolean insert(Login item) {
        Connection connection = DbManager.getConnection();
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
            throw  new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
	}

	@Override
	public int update(Login item) {
        Connection connection = DbManager.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            statement.setString(1, item.getName());
            statement.setString(2, item.getPassword());
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

	private Login parseLogin(ResultSet resultSet){
        try{
            return new Login(resultSet.getInt(ID_COLUMN), resultSet.getString(EMAIL_COLUMN),
                    resultSet.getString(PASSWORD_COLUMN));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
