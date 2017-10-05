package controller.filters;

import model.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private Set<String> generalQueries = new HashSet<>();

    private Set<String> clientQueries = new HashSet<>();

    private Set<String> adminQueries = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // general queries
        generalQueries.add("login");
        generalQueries.add("registration");
        generalQueries.add("register.user");
        generalQueries.add("index");
        generalQueries.add("set.locale");

        // subscriber queries
        clientQueries.addAll(generalQueries);
        clientQueries.add("get.order.items");
        clientQueries.add("client.home.page");
        clientQueries.add("search.meals");
        clientQueries.add("add.meal.to.order");
        clientQueries.add("remove.meal.from.order");
        clientQueries.add("create.order");
        clientQueries.add("client.current.order");
        clientQueries.add("exit");
        clientQueries.add("client.checks.page");
        clientQueries.add("client.decline.order");
        clientQueries.add("pay.check");


        // admin queries
        adminQueries.addAll(generalQueries);
        adminQueries.add("admin.home.page");
        adminQueries.add("go.to.order");
        adminQueries.add("admin.accept");
        adminQueries.add("admin.decline");
        adminQueries.add("exit");


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        String query = request.getParameter("query");
        if (query == null) {
            query = "index";
        }

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
        if (isAllowedGuestAccess || isAllowedSubscriberAccess || isAllowedAdminAccess) {
            request.setAttribute("query", query);
        } else {
            request.setAttribute("query", "index");
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