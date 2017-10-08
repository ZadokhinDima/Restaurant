package model.service;


import model.entities.Login;
import model.entities.User;
import model.exeptions.EmailExistsException;

import java.util.Optional;

public interface AccountService {

    Optional<User> logIn(String email, String password);

    void registerUser(Login login, User user) throws EmailExistsException;
}