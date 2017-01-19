package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.ConfigurationManager;
import com.audiowave.tverdakhleb.service.HomePageService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EmptyCommand implements ICommandAction {
    private static final Logger LOG = LogManager.getLogger();

    private static final String PATH_PAGE_MAIN = "path.page.main";
    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HomePageService service = new HomePageService();
        try {
            request.setAttribute("albums", service.getPopularAlbum());
        } catch (ServiceException e) {
            LOG.log(Level.ERROR,e);
        }
        ConfigurationManager config = new ConfigurationManager();
        return config.getProperty(PATH_PAGE_MAIN);
    }
}
