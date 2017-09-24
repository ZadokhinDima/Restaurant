package model.dao.impl.mysql;

import model.entities.Check;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public  class DbUtil <T>{

    private ResultSetParser<T> parser;
    private String tableName;

    public DbUtil(ResultSetParser<T> resultSetParser, String tableName) {
        this.parser = resultSetParser;
        this.tableName = tableName;
    }

    public List<T> getAll(){
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            List<T> result = new ArrayList<>();
            while (rs.next()){
                result.add(parser.parse(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
    }


    public Optional<T> getForId(int id){
        Connection connection = DbManager.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(parser.parse(resultSet));
            }
            else {
                return Optional.empty();
            }

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        finally {
            DbManager.putConnection(connection);
        }
    }
}
