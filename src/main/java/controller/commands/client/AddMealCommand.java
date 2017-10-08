package controller.commands.client;

import controller.commands.Command;
import controller.commands.CommandFactory;
import model.entities.Meal;
import model.entities.User;
import model.service.MenuService;
import model.service.impl.MenuServiceImpl;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import java.util.List;


public class AddMealCommand implements Command {


    private static String PARAMETER_MEAL = "meal";
    private static String PARAMETER_AMOUNT = "amount";
    private static String MESSAGE_ATTRIBUTE = "message";
    private static String ATTRIBUTE_ORDER_MEALS = "currentOrder";
    private static String ATTRIBUTE_USER = "user";

    private static String REDIRECT_PAGE = "redirect:" + CommandFactory.MEALS_SEARCH;

    private static final Logger LOGGER = Logger.getLogger(AddMealCommand.class);

    private int mealId;
    private int amount;
    private User client;
    private MenuService service = MenuServiceImpl.getInstance();
    private List<Meal> meals;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        service.addMealToList(mealId, amount, meals);
        saveCommandResults(request);
        return REDIRECT_PAGE;
    }

    private List<Meal> getCurrentOrderMeals(HttpSession session){
        List<Meal> currentOrderMeals = (List<Meal>) session.getAttribute(ATTRIBUTE_ORDER_MEALS);
        if(currentOrderMeals == null){
            currentOrderMeals = new ArrayList<>();
        }
        return currentOrderMeals;
    }

    private void initCommand(HttpServletRequest request){
        mealId = Integer.parseInt(request.getParameter(PARAMETER_MEAL));
        amount = Integer.parseInt(request.getParameter(PARAMETER_AMOUNT));
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        meals = getCurrentOrderMeals(request.getSession());
    }

    private void saveCommandResults(HttpServletRequest request){
        request.getSession().setAttribute(ATTRIBUTE_ORDER_MEALS, meals);
        LOGGER.info("User " + client.getId() + " added meal: " + mealId + " to order.");
        request.setAttribute(MESSAGE_ATTRIBUTE, "message.added");
    }


}
