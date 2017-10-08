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

public class CreateOrderCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CURRENT_ORDER_MEALS = "currentOrder";

    private static final String REDIRECT_PAGE = "redirect:" + CommandFactory.CLIENT_HOME;

    private User client;
    private List<Meal> meals;
    private int orderId;

    private OrderService service = OrderServiceImpl.getInstance();

    private static final Logger LOGGER = Logger.getLogger(CreateOrderCommand.class);



    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        orderId = service.createOrder(client, meals);
        saveCommandResults(request);
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request){
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        meals = (List<Meal>) request.getSession().getAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS);
    }

    private void saveCommandResults(HttpServletRequest request){
        request.getSession().setAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS, null);
        LOGGER.info("Client: " + client.getId() + " created order: " + orderId);
    }
}
