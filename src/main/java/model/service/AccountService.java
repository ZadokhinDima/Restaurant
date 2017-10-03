package model.service;


import model.entities.Login;
import model.entities.User;

import java.util.Optional;

public interface AccountService {

    Optional<User> logIn(String email, String password);

    boolean registerUser(Login login, User user);
}