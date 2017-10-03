package model.dao;

import model.entities.Login;
import model.entities.User;

import java.util.Optional;

public interface UserDAO extends GenericDAO<User> {
    Optional<User> getUserByAccount(Login login);
}
