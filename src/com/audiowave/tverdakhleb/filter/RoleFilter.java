package com.audiowave.tverdakhleb.filter;

import com.audiowave.tverdakhleb.entity.RoleType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/*"}

)

public class RoleFilter implements Filter {
    private static final String ROLE = "role";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest)servletRequest).getSession(false);
            if (session == null){
                session = ((HttpServletRequest)servletRequest).getSession();
                session.setAttribute(ROLE, RoleType.GUEST.getRole());
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
