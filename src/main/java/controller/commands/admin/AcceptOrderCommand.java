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

public class AcceptOrderCommand implements Command {

    private Order order;
    private User admin;


    private static final String ATTRIBUTE_CURRENT_ORDER = "currentOrder";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_MESSAGE = "message";

    private static final String REDIRECT_PAGE = "redirect:admin.home.page";

    private CheckService checkService = CheckServiceImpl.getInstance();

    private static final Logger LOGGER = Logger.getLogger(AcceptOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        try {
            checkService.acceptOrder(order, admin);
            processSuccessfulAcceptance();
        }
        catch (ConcurrentProcessingException e){
            processConcurrentAcceptance(request);
        }
        return REDIRECT_PAGE;
    }

    private void initCommand(HttpServletRequest request){
        order = (Order) request.getSession().getAttribute(ATTRIBUTE_CURRENT_ORDER);
        admin = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }

    private void processSuccessfulAcceptance(){
        LOGGER.info("Admin: " + admin.getId() + " accepted order: " + order.getId());
    }

    private void processConcurrentAcceptance(HttpServletRequest request){
        request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurensy.processed");
    }


}
