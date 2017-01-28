package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.ConfigurationManager;
import com.audiowave.tverdakhleb.service.AlbumService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CommandCurrentAlbum implements ICommandAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PATH_PAGE_CURRENT_ALBUM = "path.page.current.album";
    private static final String PARAM_ID = "id";
    private static final String PARAM_ALBUM = "album";
    private static final String PARAM_SYMBOL = "symbol";
    private static final String ROLE = "role";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        setPreviousPage(request);
        boolean proceed = true;
        String id = request.getParameter(PARAM_ID);
        long albumId = 0;
        try {
            albumId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, e);
            proceed = false;
        }
        if (proceed) {
            try {
                AlbumService service = new AlbumService();
                Album album = service.getAlbumById(albumId, String.valueOf(request.getSession().getAttribute(ROLE)));
                request.setAttribute(PARAM_ALBUM, album);
                request.setAttribute(PARAM_SYMBOL, album.getAlbumName().toUpperCase().charAt(0));
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessForward(request);
        ConfigurationManager config = new ConfigurationManager();
        return config.getProperty(PATH_PAGE_CURRENT_ALBUM);
    }
}
