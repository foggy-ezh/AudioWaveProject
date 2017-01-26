package com.audiowave.tverdakhleb.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ICommandAction {

    String PREVIOUS_PAGE = "previousPage";
    String PROCESS = "process";
    String REDIRECT = "redirect";
    String FORWARD = "forward";
    String HOME_PAGE = "/AudioWave";

    String execute(HttpServletRequest request) throws IOException, ServletException;

    default void setPreviousPage(HttpServletRequest request){
        String uri = request.getRequestURI()+'?'+request.getQueryString();
        request.getSession().setAttribute(PREVIOUS_PAGE, uri);
    }

    default String getPreviousPage(HttpServletRequest request){
        return String.valueOf(request.getSession().getAttribute(PREVIOUS_PAGE));
    }

    default void setProcessForward(HttpServletRequest request){
     request.getSession().setAttribute(PROCESS, FORWARD);
    }
    default void setProcessRedirect(HttpServletRequest request){
        request.getSession().setAttribute(PROCESS, REDIRECT);
    }
}