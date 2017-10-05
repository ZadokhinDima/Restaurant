package controller.commands.client;

import controller.commands.Command;
import model.entities.Meal;
import model.entities.User;
import model.service.MenuService;
import model.service.impl.MenuServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class RemoveMealCommand implements Command {

    private static String PARAMETER_MEAL = "meal";
    private static String PARAMETER_AMOUNT = "amount";

    private static String ATTRIBUTE_ORDER_MEALS = "currentOrder";
    private static String ATTRIBUTE_USER = "user";
    private static String MESSAGE_ATTRIBUTE = "message";

    private int mealId;
    private int amount;
    private User client;
    private MenuService service = new MenuServiceImpl();

    private static final Logger LOGGER = Logger.getLogger(RemoveMealCommand.class);
    private List<Meal> meals;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        service.removeMealFromList(mealId, amount, meals);
        saveCommandResults(request);
        return CLIENT_ORDER_PAGE;
    }


    private void initCommand(HttpServletRequest request){
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        meals = (List<Meal>) request.getSession().getAttribute(ATTRIBUTE_ORDER_MEALS);
        mealId = Integer.parseInt(request.getParameter(PARAMETER_MEAL));
        amount = Integer.parseInt(request.getParameter(PARAMETER_AMOUNT));
    }

    private void saveCommandResults(HttpServletRequest request){
        LOGGER.info("User " + client.getId() + " deleted meal: " + mealId + " from order.");
        request.setAttribute(MESSAGE_ATTRIBUTE, "message.deleted");
        request.getSession().setAttribute(ATTRIBUTE_ORDER_MEALS, meals);
    }

}
