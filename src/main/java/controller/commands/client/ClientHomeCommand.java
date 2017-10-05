package controller.commands.client;

import controller.commands.Command;
import model.entities.Order;
import model.entities.User;
import model.service.OrderService;
import model.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class ClientHomeCommand implements Command {

    private static final String ATTRIBUTE_CLIENT_ORDERS = "ordersHistory";
    private static final String ATTRIBUTE_USER = "ordersHistory";

    private OrderService service = new OrderServiceImpl();

    private User client;
    private List<Order> orders;

    private static final Logger LOGGER = Logger.getLogger(ClientHomeCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        orders = service.ordersOfUser(client);
        saveCommandResults(request);
        return CLIENT_PAGE_JSP;
    }

    private void initCommand(HttpServletRequest request) {
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }

    private void saveCommandResults(HttpServletRequest request) {
        request.setAttribute(ATTRIBUTE_CLIENT_ORDERS, orders);
        LOGGER.info("Client: " + client.getId() + " entered home page");
    }
}
