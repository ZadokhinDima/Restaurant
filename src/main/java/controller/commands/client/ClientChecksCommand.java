package controller.commands.client;

import controller.commands.Command;
import model.entities.Check;
import model.entities.User;
import model.service.CheckService;
import model.service.impl.CheckServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ClientChecksCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CHECKS = "userChecks";

    private User client;
    private CheckService service = new CheckServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        initCommand(request);
        List<Check> checks = service.getChecks(client);
        request.setAttribute(ATTRIBUTE_CHECKS, checks);
        return CLIENT_CHECKS_PAGE;
    }

    private void initCommand(HttpServletRequest request){
        client = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
    }
}
