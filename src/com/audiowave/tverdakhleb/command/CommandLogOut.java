package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandLogOut implements ICommandAction {
    private static final String ROLE = "role";
    private static final String CURRENT_USER = "currentUser";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (!RoleType.GUEST.getRole().equals(session.getAttribute(ROLE))) {
            session.setAttribute(ROLE, RoleType.GUEST.getRole());
            session.setAttribute(CURRENT_USER, null);
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
