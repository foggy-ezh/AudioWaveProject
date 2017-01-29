package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.AlbumCommentService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandDeleteComment implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String PARAM_ALBUM_ID = "albumId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_COMMENT_ID = "commentId";
    private static final String CURRENT_USER = "currentUser";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String role = String.valueOf(session.getAttribute(ROLE));
        long currentUserId = ((User) session.getAttribute(CURRENT_USER)).getId();
        long commentUserId = 0;
        try {
            commentUserId = Long.parseLong(request.getParameter(PARAM_USER_ID));
        }catch (NumberFormatException e){
            LOGGER.log(Level.ERROR, e);
        }
        if (RoleType.ADMIN.getRole().equals(role) || currentUserId == commentUserId) {
            try {
                long albumId = Long.parseLong(request.getParameter(PARAM_ALBUM_ID));
                long commentId = Long.parseLong(request.getParameter(PARAM_COMMENT_ID));
                if(commentId>0 && albumId>0) {
                    AlbumCommentService albumCommentService = new AlbumCommentService();
                    albumCommentService.deleteComment(commentUserId, albumId, commentId);
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
