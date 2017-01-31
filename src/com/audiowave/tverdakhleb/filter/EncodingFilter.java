package com.audiowave.tverdakhleb.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(filterName = "encodingFilter", urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class EncodingFilter implements Filter {
    private static final String ENCODING_PARAM = "encoding";
    private String code;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        code = filterConfig.getInitParameter(ENCODING_PARAM);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String codeRequest = servletRequest.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        code = null;
    }
}