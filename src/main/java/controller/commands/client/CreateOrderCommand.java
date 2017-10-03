package controller.commands.client;

import controller.commands.Command;
import model.entities.Meal;
import model.entities.User;
import model.service.OrderService;
import model.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateOrderCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CURRENT_ORDER_MEALS = "currentOrder";


    private User client;
    private List<Meal> meals;

    private OrderService service = new OrderServiceImpl();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        service.createOrder(client, meals);
        request.getSession().setAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS, null);
        return new ClientHomeCommand().execute(request, response);
    }

    private void initCommand(HttpServletRequest request){
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        meals = (List<Meal>) request.getSession().getAttribute(ATTRIBUTE_CURRENT_ORDER_MEALS);
    }
}
