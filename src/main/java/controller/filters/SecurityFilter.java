package controller.filters;

import model.entities.Role;
import model.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static controller.commands.CommandFactory.*;

@WebFilter(urlPatterns = {"/restaurant/*"})
public class SecurityFilter implements Filter {

    private Set<String> generalQueries = new HashSet<>();

    private Set<String> clientQueries = new HashSet<>();

    private Set<String> adminQueries = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // general queries
        generalQueries.add(LOGIN_PAGE);
        generalQueries.add(LOGIN);
        generalQueries.add(REGISTRATION);
        generalQueries.add(REGISTER_USER);
        generalQueries.add(SET_LOCALE);

        // subscriber queries
        clientQueries.add(GET_ORDER_MEALS);
        clientQueries.add(CLIENT_HOME);
        clientQueries.add(MEALS_SEARCH);
        clientQueries.add(ADD_MEAL_TO_ORDER);
        clientQueries.add(REMOVE_MEAL_FROM_ORDER);
        clientQueries.add(CREATE_ORDER);
        clientQueries.add(CLIENT_ORDER);
        clientQueries.add(CLIENT_CHECKS);
        clientQueries.add(CLIENT_DECLINE_ORDER);
        clientQueries.add(PAY_CHECK);
        clientQueries.add(EXIT);


        // admin queries
        adminQueries.add(ADMIN_HOME);
        adminQueries.add(GO_TO_ORDER);
        adminQueries.add(ADMIN_ACCEPT_ORDER);
        adminQueries.add(ADMIN_DECLINE_ORDER);
        adminQueries.add(EXIT);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String query = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");

        boolean isAllowedGuestAccess =
                (user == null) &&
                        generalQueries.contains(query);


        boolean isAllowedSubscriberAccess =
                (user != null) &&
                        clientQueries.contains(query) &&
                        "CLIENT".equals(user.getRole().toString());
        boolean isAllowedAdminAccess =
                (user != null) &&
                        adminQueries.contains(query) &&
                        "ADMIN".equals(user.getRole().toString());

        boolean isAlreadySignedIn = user != null && generalQueries.contains(query);

        boolean needToSignIn = user == null && (clientQueries.contains(query) || adminQueries.contains(query));

        if (isAllowedGuestAccess || isAllowedSubscriberAccess || isAllowedAdminAccess) {
            request.setAttribute("query", query);
        }

        if(isAlreadySignedIn){
            if(user.getRole() == Role.CLIENT){
                request.setAttribute("query", CLIENT_HOME);
            }
            else {
                request.setAttribute("query", ADMIN_HOME);
            }
        }

        if(needToSignIn){
            request.setAttribute("query", LOGIN_PAGE);
        }
        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        generalQueries = null;
        clientQueries = null;
        adminQueries = null;
    }
}