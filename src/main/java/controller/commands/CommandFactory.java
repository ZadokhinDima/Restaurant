package controller.commands;

import controller.commands.outer.*;

public class CommandFactory {
    public static final String INDEX = "index";
    public static final String REGISTRATION = "registration";
    public static final String REGISTER_USER = "register.user";
    public static final String LOGIN = "login";
    public static final String ERROR = "error";


    public static Command create(String query){
        if(query == null){
            query = INDEX;
        }
        switch (query){
            case REGISTRATION :
                return new RegistrationCommand();
            case LOGIN :
                return new LoginCommand();
            case INDEX :
                return new IndexCommand();
            case ERROR:
                return new ErrorCommand();
            default:
                return new UnknownCommand();
        }

    }

}
