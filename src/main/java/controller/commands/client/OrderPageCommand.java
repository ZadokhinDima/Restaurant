package controller.commands.client;

import controller.commands.Command;
import model.entities.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderPageCommand implements Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return CLIENT_ORDER_PAGE;
    }

}
