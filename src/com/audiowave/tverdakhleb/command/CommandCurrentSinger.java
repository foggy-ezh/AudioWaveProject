package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.Singer;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.ConfigurationManager;
import com.audiowave.tverdakhleb.service.AlbumService;
import com.audiowave.tverdakhleb.service.SingerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class CommandCurrentSinger implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PATH_PAGE_CURRENT_SINGER = "path.page.current.singer";
    private static final String PARAM_ID = "id";
    private static final String PARAM_SINGER = "singer";
    private static final String PARAM_SYMBOL = "symbol";
    private static final String ROLE = "role";


    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        setPreviousPage(request);
        boolean proceed = true;
        String id = request.getParameter(PARAM_ID);
        long singerId = 0;
        try {
            singerId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, e);
            proceed = false;
        }
        if (proceed) {
            try {
                SingerService service = new SingerService();
                Singer singer = service.getSingerById(singerId);
                if (singer == null) {
                    proceed = false;
                } else {
                    request.setAttribute(PARAM_SINGER, singer);
                    request.setAttribute(PARAM_SYMBOL, singer.getName().toUpperCase().charAt(0));
                }
                if (proceed) {
                    AlbumService albumService = new AlbumService();
                    List<Album> list = albumService.getSingerAlbums(singer.getId(),request.getParameter(ROLE));
                    singer.setAlbums(list);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessForward(request);
        ConfigurationManager config = new ConfigurationManager();
        return config.getProperty(PATH_PAGE_CURRENT_SINGER);
    }
}
