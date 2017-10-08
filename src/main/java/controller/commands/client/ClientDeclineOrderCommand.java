package controller.commands.client;

import controller.commands.Command;
import model.entities.User;
import model.exeptions.ConcurrentProcessingException;
import model.service.CheckService;
import model.service.OrderService;
import model.service.impl.CheckServiceImpl;
import model.service.impl.OrderServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientDeclineOrderCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ClientDeclineOrderCommand.class);

    private int orderId;
    private User client;
    private OrderService orderService = OrderServiceImpl.getInstance();
    private CheckService checksService = CheckServiceImpl.getInstance();

    private static final String PARAMETER_ORDER = "order.id";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_MESSAGE = "message";

    private static final String REDIRECT_PAGE = "redirect:client.home.page";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        if (!orderService.checkClientRightsOnOrder(orderId, client)) {
            LOGGER.error("Client attempt to decline someones order.");
            throw new RuntimeException();
        }
        try {
            processSuccessfulDeclination();
            checksService.declineOrder(orderId);
        } catch (ConcurrentProcessingException e) {
            processConcurrentDeclination(request);
        }
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request) {
        orderId = Integer.parseInt(request.getParameter(PARAMETER_ORDER));
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }

    private void processSuccessfulDeclination() {
        LOGGER.info("Client id: " + client.getId() + " declined order: " + orderId);
    }

    private void processConcurrentDeclination(HttpServletRequest request) {
        request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurensy.processed");
    }

}
