package controller.commands.client;

import controller.commands.Command;
import controller.commands.CommandFactory;
import model.entities.Meal;
import model.entities.User;
import model.service.OrderService;
import model.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderMealsCommand implements Command {

    private final static String PARAMETER_ORDER_ID = "order.id";
    private final static String ATTRIBUTE_ORDER_MEALS = "orderMeals";
    private final static String ATTRIBUTE_USER = "user";

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_HOME;

    private int orderId;
    private List<Meal> meals;
    private User client;


    private OrderService service = OrderServiceImpl.getInstance();

    private static final Logger LOGGER = Logger.getLogger(OrderMealsCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        meals = service.getOrderMeals(orderId);
        saveCommandResults(request);
        return REDIRECT_PAGE;
    }


    private void initCommand(HttpServletRequest request) {
        orderId = Integer.parseInt(request.getParameter(PARAMETER_ORDER_ID));
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }

    private void saveCommandResults(HttpServletRequest request) {
        request.setAttribute(ATTRIBUTE_ORDER_MEALS, meals);
        LOGGER.info("Client: " + client.getId() + " got meals from order: " + orderId);
    }
}
