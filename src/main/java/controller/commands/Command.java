package controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String INDEX_JSP = "/index.jsp";
    String REGISTRATION_JSP = "/WEB-INF/view/general/registration.jsp";
    String ERROR_JSP = "/WEB-INF/view/general/error.jsp";


    String execute(HttpServletRequest request, HttpServletResponse response);
}
