package controller.commands.outer;

import controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class ExitCommand implements Command {
    @Override
    /**
     * This command will clear all session attributes except of locale and then redirect to login page.
     */
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> attributes = request.getSession().getAttributeNames();
        for(; attributes.hasMoreElements(); ){
            String attribute = attributes.nextElement();
            if(!attribute.equals("locale")){
                request.getSession().setAttribute(attribute, null);
            }
        }
        return SIGN_IN_JSP;
    }
}
