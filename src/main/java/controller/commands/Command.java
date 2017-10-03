package controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String INDEX_JSP = "/index.jsp";
    String REGISTRATION_JSP = "/WEB-INF/view/general/registration.jsp";
    String ADMIN_PAGE_JSP = "/WEB-INF/view/admin/home_page.jsp";
    String CLIENT_PAGE_JSP = "/WEB-INF/view/client/home_page.jsp";
    String ERROR_404 = "error404.jsp";
    String ERROR_500 = "error500.jsp";
    String CLIENT_MENU_PAGE = "/WEB-INF/view/client/menu_page.jsp";
    String CLIENT_ORDER_PAGE = "/WEB-INF/view/client/order_page.jsp";
    String CLIENT_CHECKS_PAGE = "/WEB-INF/view/client/checks_page.jsp";


    String execute(HttpServletRequest request, HttpServletResponse response);
}
