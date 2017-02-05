package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandChangeUser implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "pwd1";
    private static final String PARAM_MAIL = "mail";
    private static final String PARAM_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String ROLE = "role";
    private static final String CURRENT_USER = "currentUser";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.USER.getRole().equals(session.getAttribute(ROLE))) {
            try {
                User user = new User();
                user.setId(((User) session.getAttribute(CURRENT_USER)).getId());
                user.setLogin(request.getParameter(PARAM_LOGIN));
                user.setPassword(BCrypt.hashpw(request.getParameter(PARAM_PASSWORD), BCrypt.gensalt()));
                user.setMail(request.getParameter(PARAM_MAIL));
                user.setFirstName(request.getParameter(PARAM_NAME));
                user.setLastName(request.getParameter(PARAM_LAST_NAME));
                UserService service = new UserService();
               if (service.updateUser(user)){
                session.setAttribute(CURRENT_USER, user);}
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
