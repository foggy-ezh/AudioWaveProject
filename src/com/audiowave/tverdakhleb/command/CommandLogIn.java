package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandLogIn implements ICommandAction{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_LOGIN_ERR = "loginErr";
    private static final String ROLE = "role";
    private static final String CURRENT_USER = "currentUser";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        boolean proceed = true;
        HttpSession session = request.getSession();
        String login = request.getParameter(PARAM_LOGIN);

        if(RoleType.GUEST.getRole().equals(session.getAttribute(ROLE))) {
            try {
                UserService service = new UserService();
                if (login == null || login.isEmpty()|| !service.checkLogin(login) || !service.checkPassword(login, request.getParameter(PARAM_PASSWORD))) {
                    session.setAttribute(PARAM_LOGIN_ERR, true);
                    proceed = false;
                }
                if (proceed) {
                    User user = service.getCurrentUser(login);
                    if (user.isAdmin()) {
                        session.setAttribute(ROLE, RoleType.ADMIN.getRole());
                    } else {
                        session.setAttribute(ROLE, RoleType.USER.getRole());
                    }
                    user.setPassword("");
                    session.setAttribute(CURRENT_USER, user);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}

