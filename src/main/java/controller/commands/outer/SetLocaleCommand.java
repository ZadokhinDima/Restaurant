package controller.commands.outer;

import controller.Config;
import controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SetLocaleCommand implements Command {

    private final static String PARAMETER_LOCALE = "language";
    private final static String PARAMETER_LAST_PAGE = "page";
    private final static String ATTRIBUTE_LOCALE = "locale";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(PARAMETER_LOCALE).toLowerCase();
        Locale locale = new Locale(language);
        request.getSession().setAttribute(ATTRIBUTE_LOCALE, locale);
        return request.getParameter(PARAMETER_LAST_PAGE);
    }
}
