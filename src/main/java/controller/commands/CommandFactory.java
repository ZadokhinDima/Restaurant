package controller.commands;

import controller.commands.admin.AcceptOrderCommand;
import controller.commands.admin.AdminDeclineOrderCommand;
import controller.commands.admin.AdminHomeCommand;
import controller.commands.admin.GoToOrderCommand;
import controller.commands.client.*;
import controller.commands.outer.*;
import org.apache.log4j.Logger;

public class  CommandFactory {
    private static final String INDEX = "index";
    private static final String REGISTRATION = "registration";
    private static final String REGISTER_USER = "register.user";
    private static final String LOGIN = "login";
    private static final String SET_LOCALE = "set.locale";
    private static final String EXIT = "exit";
    private static final String MEALS_SEARCH = "search.meals";
    private static final String CLIENT_HOME = "client.home.page";
    private static final String GET_ORDER_MEALS = "get.order.items";
    private static final String ADD_MEAL_TO_ORDER = "add.meal.to.order";
    private static final String REMOVE_MEAL_FROM_ORDER = "remove.meal.from.order";
    private static final String CREATE_ORDER = "create.order";
    private static final String CLIENT_ORDER = "client.current.order";
    private static final String CLIENT_DECLINE_ORDER = "client.decline.order";
    private static final String CLIENT_CHECKS = "client.checks.page";
    private static final String PAY_CHECK = "pay.check";


    private static final String ADMIN_HOME = "admin.home.page";
    private static final String GO_TO_ORDER = "go.to.order";
    private static final String ADMIN_DECLINE_ORDER = "admin.decline";
    private static final String ADMIN_ACCEPT_ORDER = "admin.accept";



    private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

    public static Command create(String query){

        if(query == null){
            query = INDEX;
        }

        LOGGER.info("Creating command for query: " + query);

        switch (query){
            case SET_LOCALE :
                return new SetLocaleCommand();
            case REGISTRATION :
                return new RegistrationCommand();
            case LOGIN :
                return new LoginCommand();
            case INDEX :
                return new IndexCommand();
            case CLIENT_HOME:
                return new ClientHomeCommand();
            case MEALS_SEARCH:
                return new SearchMealsCommand();
            case REGISTER_USER:
                return new RegisterUserCommand();
            case GET_ORDER_MEALS:
                return new OrderMealsCommand();
            case ADD_MEAL_TO_ORDER:
                return new AddMealCommand();
            case CLIENT_DECLINE_ORDER:
                return new ClientDeclineOrderCommand();
            case REMOVE_MEAL_FROM_ORDER:
                return new RemoveMealCommand();
            case CLIENT_ORDER:
                return new OrderPageCommand();
            case CREATE_ORDER:
                return new CreateOrderCommand();
            case CLIENT_CHECKS:
                return new ClientChecksCommand();
            case PAY_CHECK:
                return new PayForCheckCommand();
            case ADMIN_HOME:
                return new AdminHomeCommand();
            case GO_TO_ORDER:
                return new GoToOrderCommand();
            case ADMIN_ACCEPT_ORDER:
                return new AcceptOrderCommand();
            case ADMIN_DECLINE_ORDER:
                return new AdminDeclineOrderCommand();
            case EXIT:
                return new ExitCommand();
            default:
                return new UnknownCommand();
        }

    }

}
