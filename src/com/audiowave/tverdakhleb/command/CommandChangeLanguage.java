package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.manager.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

public class CommandChangeLanguage implements ICommandAction {

    private static final String PARAM_LANGUAGE = "lang";
    private static final String DEFAULT_LOCALE_VALUE = "en_US";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {

        String language = request.getParameter(PARAM_LANGUAGE);
        HttpSession session = request.getSession();

        if(language != null) {
            session.setAttribute(PARAM_LANGUAGE, language);
        } else {
            session.setAttribute(PARAM_LANGUAGE, DEFAULT_LOCALE_VALUE);
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
