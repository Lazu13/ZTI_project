package pl.edu.agh.zti.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring component for CORS filtering
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {

    /**
     * Overridden [[Filter]] method
     *
     * @param fc CORS filter config
     * @throws ServletException if something goes wrong
     */
    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    /**
     * Overridden method used to filter requests.
     * It sets response headers for all origins allowing to do one of the following: POST, GET, OPTIONS, DELETE
     * with headers: "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN"
     * and max age of 3600
     *
     * @param req request servlet containing request data
     * @param resp object assisting for response to client
     * @param chain filter chain
     * @throws IOException if IO operation exception
     * @throws ServletException if servlet exception
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, resp);
        }

    }

    /**
     * Overriden destroy method
     */
    @Override
    public void destroy() {
    }

}