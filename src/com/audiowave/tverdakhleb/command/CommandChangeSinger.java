package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.entity.Singer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandChangeSinger implements ICommandAction {
    private static final String ROLE = "role";
    private static final String PARAM_SINGER_NAME = "singerName";
    private static final String PARAM_SINGER_ID = "singerId";
    private static final String PARAM_CHANGE_SINGER = "changeSinger";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String singerName = request.getParameter(PARAM_SINGER_NAME);
            long singerId = Long.parseLong(request.getParameter(PARAM_SINGER_ID));
            Singer changeSinger = new Singer(singerId,singerName);
            session.setAttribute(PARAM_CHANGE_SINGER, changeSinger);
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
