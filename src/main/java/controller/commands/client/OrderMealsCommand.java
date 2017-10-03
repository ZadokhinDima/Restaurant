package controller.commands.client;

import controller.commands.Command;
import model.entities.Meal;
import model.service.OrderService;
import model.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderMealsCommand implements Command {

    private final static String PARAMETER_ORDER_ID = "order.id";
    private final static String ATTRIBUTE_ORDER_MEALS = "orderMeals";

    int orderId;

    private OrderService service = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        List<Meal> meals = service.getOrderMeals(orderId);
        request.setAttribute(ATTRIBUTE_ORDER_MEALS, meals);
        return new ClientHomeCommand().execute(request, response);
    }


    private void initCommand(HttpServletRequest request){
        orderId = Integer.parseInt(request.getParameter(PARAMETER_ORDER_ID));
    }
}
