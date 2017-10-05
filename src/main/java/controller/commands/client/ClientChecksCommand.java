package controller.commands.client;

import controller.commands.Command;
import model.entities.Check;
import model.entities.User;
import model.service.CheckService;
import model.service.impl.CheckServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ClientChecksCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CHECKS = "userChecks";

    private static final Logger LOGGER = Logger.getLogger(ClientChecksCommand.class);



    private User client;
    private List<Check> checks;
    private CheckService service = new CheckServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        checks = service.getChecks(client);
        saveCommandResults(request);
        return CLIENT_CHECKS_PAGE;
    }

    private void initCommand(HttpServletRequest request){
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }

    private void saveCommandResults(HttpServletRequest request){
        request.setAttribute(ATTRIBUTE_CHECKS, checks);
        LOGGER.info("User " + client.getId() + " got his checks.");
    }
}
