/*
package com.audiowave.tverdakhleb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/AudioWave"},
        initParams = {@WebInitParam(name="command", value="changeLanguage")}

)

public class EnLanguageFilter implements Filter {
    String command;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        command = filterConfig.getInitParameter("command");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            String url = ((HttpServletRequest)servletRequest).getRequestURL().toString();
            String queryString = ((HttpServletRequest)servletRequest).getQueryString();
            System.out.println(url + "?" + queryString );
        }
        filterChain.doFilter(servletRequest,servletResponse);
        if(servletRequest.getParameter("en") != null);
    }

    @Override
    public void destroy() {

    }
}
*/
