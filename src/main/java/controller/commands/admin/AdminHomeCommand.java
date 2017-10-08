package controller.commands.admin;

import controller.commands.Command;
import model.entities.Order;
import model.service.OrderService;
import model.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminHomeCommand implements Command {

    private static final String ATTRIBUTE_ACTIVE_ORDERS = "activeOrders";

    private List<Order> activeOrders;

    private OrderService orderService = OrderServiceImpl.getInstance();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        activeOrders = orderService.getActiveOrders();
        saveCommandResults(request);
        return ADMIN_PAGE_JSP;
    }


    private void saveCommandResults(HttpServletRequest request){
        request.setAttribute(ATTRIBUTE_ACTIVE_ORDERS, activeOrders);
    }


}
