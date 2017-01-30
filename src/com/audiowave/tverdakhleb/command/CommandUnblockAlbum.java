package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.AlbumService;
import com.audiowave.tverdakhleb.service.AudiotrackService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandUnblockAlbum implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PARAM_ALBUM_ID = "albumId";
    private static final String ROLE = "role";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String id = request.getParameter(PARAM_ALBUM_ID);
            try {
                long albumId = Long.parseLong(id);
                AlbumService albumService = new AlbumService();
                albumService.changeAlbumToUnblocked(albumId);
            } catch (ServiceException | NumberFormatException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null")?HOME_PAGE :previousPage;
    }
}
