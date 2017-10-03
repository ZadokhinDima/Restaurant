package controller.commands.client;

import controller.commands.Command;
import model.entities.Meal;
import model.service.MenuService;
import model.service.impl.MenuServiceImpl;


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


    private int mealId;
    private int amount;
    private MenuService service = new MenuServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        putMealToSession(request.getSession());
        request.setAttribute(MESSAGE_ATTRIBUTE, "message.added");
        return CLIENT_MENU_PAGE;
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
    }

    private void putMealToSession(HttpSession session){
        List<Meal> meals = getCurrentOrderMeals(session);
        service.addMealToList(mealId, amount, meals);
        session.setAttribute(ATTRIBUTE_ORDER_MEALS, meals);
    }


}
