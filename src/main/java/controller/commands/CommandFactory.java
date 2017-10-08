package controller.commands;

import controller.commands.admin.AcceptOrderCommand;
import controller.commands.admin.AdminDeclineOrderCommand;
import controller.commands.admin.AdminHomeCommand;
import controller.commands.admin.GoToOrderCommand;
import controller.commands.client.*;
import controller.commands.outer.*;
import org.apache.log4j.Logger;

public class  CommandFactory {
    public static final String LOGIN_PAGE = "/restaurant/login_page";
    public static final String REGISTRATION = "/restaurant/registration";
    public static final String REGISTER_USER = "/restaurant/registration/register";
    public static final String LOGIN = "/restaurant/login";
    public static final String SET_LOCALE = "/restaurant/change_locale";
    public static final String EXIT = "/restaurant/exit";


    public static final String MEALS_SEARCH = "/restaurant/client/menu";
    public static final String CLIENT_HOME = "/restaurant/client/home_page";
    public static final String GET_ORDER_MEALS = "/restaurant/client/show_order";
    public static final String ADD_MEAL_TO_ORDER = "/restaurant/client/add_meal";
    public static final String REMOVE_MEAL_FROM_ORDER = "/restaurant/client/remove_meal";
    public static final String CREATE_ORDER = "/restaurant/client/create_order";
    public static final String CLIENT_ORDER = "/restaurant/client/order";
    public static final String CLIENT_DECLINE_ORDER = "/restaurant/client/decline_order";
    public static final String CLIENT_CHECKS = "/restaurant/client/checks";
    public static final String PAY_CHECK = "/restaurant/client/pay";


    public static final String ADMIN_HOME = "/restaurant/admin/home_page";
    public static final String GO_TO_ORDER = "/restaurant/admin/order";
    public static final String ADMIN_DECLINE_ORDER = "/restaurant/admin/decline_order";
    public static final String ADMIN_ACCEPT_ORDER = "/restaurant/admin/accept_order";




    private static final Logger LOGGER = Logger.getLogger(CommandFactory.class);

    public static Command create(String query){

        if(query == null){
            query = LOGIN_PAGE;
        }

        LOGGER.info("Creating command for query: " + query);

        switch (query){
            case SET_LOCALE :
                return new SetLocaleCommand();
            case REGISTRATION :
                return new RegistrationCommand();
            case LOGIN :
                return new LoginCommand();
            case LOGIN_PAGE :
                return new LoginPageCommand();
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
