package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.ConfigurationManager;
import com.audiowave.tverdakhleb.service.AudiotrackService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CommandUserAudiotracks implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String CURRENT_USER = "currentUser";
    private static final String AUDIOTRACKS = "audiotracks";
    private static final String PATH_PAGE_USER_AUDIOTRACKS = "path.page.user.audiotracks";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        setPreviousPage(request);
        HttpSession session = request.getSession();
        if (RoleType.USER.getRole().equals(session.getAttribute(ROLE))) {
            User user = (User) session.getAttribute(CURRENT_USER);
            try {
                AudiotrackService audiotrackService = new AudiotrackService();
                List<Audiotrack> audiotracks = audiotrackService.getUserAudiotracks(user.getId());
                request.setAttribute(AUDIOTRACKS, audiotracks);
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessForward(request);
        ConfigurationManager config = new ConfigurationManager();
        return config.getProperty(PATH_PAGE_USER_AUDIOTRACKS);
    }
}
