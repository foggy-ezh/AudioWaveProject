package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.AlbumComment;
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

public class CommandAlbum implements ICommandAction {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PATH_PAGE_ALBUM = "path.page.album";
    private static final String PARAM_LETTER = "letters";
    private static final String PARAM_SYMBOL = "symbol";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_TOTAL = "total";
    private static final String PARAM_ALBUMS = "albums";
    private static final String ROLE = "role";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        setPreviousPage(request);
        String symbol = request.getParameter(PARAM_SYMBOL);
        String role = String.valueOf(request.getSession().getAttribute(ROLE));
        if (symbol == null) {
            symbol = "%";
        }
        String currentPage = request.getParameter(PARAM_PAGE);
        int page;
        if (currentPage == null) {
            currentPage = "1";
        }
        try {
            page = Integer.parseInt(currentPage);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, e);
            page = 1;
        }
        if(page < 1){
            page = 1;
        }
        try {
            AlbumService service = new AlbumService();
            List<String> letters = service.getSingerStartLetter(role);
            List<Album> albums = service.getAlbums(symbol, page, role);
            int totalPages = service.getTotalPages();
            request.setAttribute(PARAM_LETTER, letters);
            request.setAttribute(PARAM_SYMBOL, symbol);
            request.setAttribute(PARAM_PAGE, totalPages > page ? page : totalPages);
            request.setAttribute(PARAM_TOTAL, totalPages);
            request.setAttribute(PARAM_ALBUMS, albums);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e);
        }
        setProcessForward(request);
        ConfigurationManager config = new ConfigurationManager();
        return config.getProperty(PATH_PAGE_ALBUM);
    }
}
