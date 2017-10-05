package controller.commands.admin;

import controller.commands.Command;
import model.entities.Order;
import model.service.OrderService;
import model.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToOrderCommand implements Command {

    private static final String PARAMETER_ORDER = "order.id";
    private static final String ATTRIBUTE_CURRENT_ORDER = "currentOrder";

    private OrderService orderService = new OrderServiceImpl();


    private int orderId;
    private Order order;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        order = orderService.getFullInfoAboutOrder(orderId);
        saveCommandResults(request);
        return ADMIN_ORDER_PAGE;
    }

    private void initCommand(HttpServletRequest request){
        orderId = Integer.parseInt(request.getParameter(PARAMETER_ORDER));
    }

    private void saveCommandResults(HttpServletRequest request){
        request.getSession().setAttribute(ATTRIBUTE_CURRENT_ORDER, order);
    }
}
