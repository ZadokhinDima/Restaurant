package model.dao.impl.mysql;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DbManager {

    public static Connection getConnection(){
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/restaurant");
            return dataSource.getConnection();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void putConnection(Connection connection){
        try {
            if(connection != null) connection.close();
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

}
