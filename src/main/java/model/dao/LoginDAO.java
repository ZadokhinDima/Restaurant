package model.dao;

import model.entities.Login;

import java.util.Optional;


public interface LoginDAO extends GenericDAO<Login> {
    Optional<Login> findAccount(String email, String password);
}
