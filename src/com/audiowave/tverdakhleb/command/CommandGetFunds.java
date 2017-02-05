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

public class CommandGetFunds implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String CURRENT_USER = "currentUser";
    private static final String SHOW_FUNDS = "showFunds";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.USER.getRole().equals(session.getAttribute(ROLE))) {
            try {
                User user = (User) session.getAttribute(CURRENT_USER);
                UserService service = new UserService();
                user = service.getCurrentUser(user.getLogin());
                session.setAttribute(CURRENT_USER, user);
                session.setAttribute(SHOW_FUNDS, true);
            } catch (ServiceException | NumberFormatException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
