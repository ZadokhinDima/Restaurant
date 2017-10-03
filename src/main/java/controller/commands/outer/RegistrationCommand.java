package controller.commands.outer;

import controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return REGISTRATION_JSP;
    }
}
