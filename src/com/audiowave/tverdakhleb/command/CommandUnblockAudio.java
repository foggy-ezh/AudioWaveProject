package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.AudiotrackService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandUnblockAudio implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_ID = "id";
    private static final String ROLE = "role";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String id = request.getParameter(PARAM_ID);
            try {
                long audioId = Long.parseLong(id);
                AudiotrackService audiotrackService = new AudiotrackService();
                audiotrackService.changeAudiotrackToUnblocked(audioId);
            } catch (ServiceException | NumberFormatException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    setProcessRedirect(request);
    String previousPage = getPreviousPage(request);
        return previousPage.equals("null")?HOME_PAGE :previousPage;
}
}
