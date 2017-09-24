package controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.annotation.WebInitParam;


@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(
                name = "encoding",
                value = "UTF-8",
                description = "Encoding parameter")})
public class EncodingFilter implements Filter {


    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html;charset=UTF-8");
        String requestEncoding = servletRequest.getCharacterEncoding();
        if (encoding != null && !encoding.equalsIgnoreCase(requestEncoding)) {
            servletRequest.setCharacterEncoding(encoding);
            servletResponse.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}

