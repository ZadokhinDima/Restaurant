package model.dao;

public interface ConnectionDAO extends AutoCloseable {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

    void close();
}