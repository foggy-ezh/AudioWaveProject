package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.SingerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandAddSinger implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String PARAM_SINGER_NAME = "singerName";
    private static final String PARAM_SINGER_ID = "singerId";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String singerName = request.getParameter(PARAM_SINGER_NAME);
            long singerId=0;
            try {
                singerId= Long.parseLong(request.getParameter(PARAM_SINGER_ID));
            } catch (NumberFormatException e) {
                LOGGER.log(Level.ERROR, e);
            }
            try {
                SingerService singerService = new SingerService();
                if(singerId > 0){
                    singerService.updateSinger(singerName, singerId);
                } else{
                singerService.insertNewSinger(singerName);}
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
