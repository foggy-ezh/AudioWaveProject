package com.audiowave.tverdakhleb.command;

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

public class CommandRegister implements ICommandAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "pwd1";
    private static final String PARAM_MAIL = "mail";
    private static final String PARAM_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";

    private static final String PARAM_LOGIN_ERR = "regErr";
    private static final String FAILED_MESSAGE_LOGIN="login";
    private static final String FAILED_MESSAGE_SUCCESS="success";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        boolean proceed = true;
        HttpSession session = request.getSession();
        String login = request.getParameter(PARAM_LOGIN);
        try {
            UserService service = new UserService();
            if(login == null || !service.checkLoginExist(login)) {
                session.setAttribute(PARAM_LOGIN_ERR,FAILED_MESSAGE_LOGIN);
                proceed = false;
            }
            if (proceed){
                User user = new User();
                user.setLogin(login);
                user.setPassword(BCrypt.hashpw(request.getParameter(PARAM_PASSWORD),BCrypt.gensalt()));
                user.setMail(request.getParameter(PARAM_MAIL));
                user.setFirstName(request.getParameter(PARAM_NAME));
                user.setLastName(request.getParameter(PARAM_LAST_NAME));
                if(service.addUser(user)){
                    session.setAttribute(PARAM_LOGIN_ERR,FAILED_MESSAGE_SUCCESS);
                }else{
                    session.setAttribute(PARAM_LOGIN_ERR,true);
                }
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,e);
        }

        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
