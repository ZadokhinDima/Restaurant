package model.dao.impl.mysql;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultSetParser<T>{
    T parse(ResultSet resultSet);
}
