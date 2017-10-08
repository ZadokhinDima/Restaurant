package controller.commands.admin;

import controller.commands.Command;
import model.entities.Order;
import model.entities.User;
import model.exeptions.ConcurrentProcessingException;
import model.service.CheckService;
import model.service.impl.CheckServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminDeclineOrderCommand implements Command {

    private Order order;
    private User admin;

    private static final String REDIRECT_PAGE = "redirect:admin.home.page";

    private static final String ATTRIBUTE_CURRENT_ORDER = "currentOrder";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_MESSAGE = "message";

    private CheckService checkService = CheckServiceImpl.getInstance();

    private static final Logger LOGGER = Logger.getLogger(AdminDeclineOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        try {
            checkService.declineOrder(order.getId());
            processSuccessfulDeclination();
        }
        catch (ConcurrentProcessingException e){
            processConcurrentDeclination(request);
        }
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request){
        order = (Order) request.getSession().getAttribute(ATTRIBUTE_CURRENT_ORDER);
        admin = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }

    private void processSuccessfulDeclination(){
        LOGGER.info("Admin: " + admin.getId() + " declined order: " + order.getId());
    }

    private void processConcurrentDeclination(HttpServletRequest request){
        request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurensy.processed");
    }
}
