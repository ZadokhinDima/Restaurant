package controller.commands.client;

import controller.commands.Command;
import model.entities.Meal;
import model.service.MenuService;
import model.service.impl.MenuServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RemoveMealCommand implements Command {

    private static String PARAMETER_MEAL = "meal";
    private static String PARAMETER_AMOUNT = "amount";

    private static String ATTRIBUTE_ORDER_MEALS = "currentOrder";
    private static String MESSAGE_ATTRIBUTE = "message";

    private int mealId;
    private int amount;
    private MenuService service = new MenuServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        removeMealsFromSession(request.getSession());
        request.setAttribute(MESSAGE_ATTRIBUTE, "message.deleted");
        return CLIENT_ORDER_PAGE;
    }

    private List<Meal> getCurrentOrderMeals(HttpSession session){
        return (List<Meal>) session.getAttribute(ATTRIBUTE_ORDER_MEALS);
    }

    private void initCommand(HttpServletRequest request){
        mealId = Integer.parseInt(request.getParameter(PARAMETER_MEAL));
        amount = Integer.parseInt(request.getParameter(PARAMETER_AMOUNT));
    }

    private void removeMealsFromSession(HttpSession session){
        List<Meal> meals = getCurrentOrderMeals(session);
        service.removeMealFromList(mealId, amount, meals);
        session.setAttribute(ATTRIBUTE_ORDER_MEALS, meals);
    }

}
