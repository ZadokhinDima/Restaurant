package controller.commands.outer;

import controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ERROR_404;
    }
}
