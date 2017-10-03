package controller.commands.client;

import controller.commands.Command;
import model.entities.User;
import model.service.OrderService;
import model.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClientHomeCommand implements Command {

    private OrderService service = new OrderServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("ordersHistory",
                service.ordersOfUser((User) request.getSession().getAttribute("user")));
        return CLIENT_PAGE_JSP;
    }
}
