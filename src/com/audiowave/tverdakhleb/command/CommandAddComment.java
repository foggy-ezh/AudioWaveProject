package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.AlbumCommentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandAddComment implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String PARAM_ALBUM_ID = "albumId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_TEXT = "text";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (!RoleType.GUEST.getRole().equals(session.getAttribute(ROLE))) {
            String currentUserId = request.getParameter(PARAM_USER_ID);
            String currentAlbumId = request.getParameter(PARAM_ALBUM_ID);
            String text = request.getParameter(PARAM_TEXT);
            try {
                long userId = Long.parseLong(currentUserId);
                long albumId = Long.parseLong(currentAlbumId);
                if(userId>0 && albumId>0) {
                    AlbumCommentService albumCommentService = new AlbumCommentService();
                    albumCommentService.insertNewComment(userId,albumId,text);
                }
            } catch (ServiceException | NumberFormatException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
