package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.ConfigurationManager;
import com.audiowave.tverdakhleb.service.AlbumService;
import com.audiowave.tverdakhleb.service.AudiotrackService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EmptyCommand implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ROLE = "role";
    private static final String PATH_PAGE_MAIN = "path.page.main";
    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        setPreviousPage(request);
        AlbumService albumService = new AlbumService();
        AudiotrackService audiotrackService = new AudiotrackService();
        try {
            request.setAttribute("albums", albumService.getPopularAlbum());
            request.setAttribute("audiotracks", audiotrackService.getPopularAudiotrack());
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,e);
        }
        setProcessForward(request);
        ConfigurationManager config = new ConfigurationManager();
        return config.getProperty(PATH_PAGE_MAIN);
    }
}
