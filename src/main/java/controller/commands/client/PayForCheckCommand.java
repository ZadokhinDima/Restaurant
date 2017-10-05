package controller.commands.client;

import controller.commands.Command;
import model.entities.User;
import model.exeptions.ConcurrentProcessingException;
import model.service.CheckService;
import model.service.impl.CheckServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayForCheckCommand implements Command {

    private static final String PARAMETER_CHECK = "check.id";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_USER = "user";

    private static final Logger LOGGER = Logger.getLogger(PayForCheckCommand.class);

    private User client;
    private int checkId;

    private CheckService checkService = new CheckServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        try {
            checkService.payCheck(checkId);
            LOGGER.info("Client:" + client.getId() + " paid check: " + checkId);
            return new ClientChecksCommand().execute(request, response);
        } catch (ConcurrentProcessingException e) {
            request.setAttribute(ATTRIBUTE_MESSAGE, "error.concurensy.check");
            return new ClientChecksCommand().execute(request, response);
        }
    }

    private void initCommand(HttpServletRequest request) {
        checkId = Integer.parseInt(request.getParameter(PARAMETER_CHECK));
        client = (User)request.getSession().getAttribute(ATTRIBUTE_USER);
    }

}