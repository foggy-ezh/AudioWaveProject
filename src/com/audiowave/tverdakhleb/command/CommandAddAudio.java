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
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;

public class CommandAddAudio implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String PARAM_ALBUM_ID = "albumId";
    private static final String PARAM_SINGER_ID = "singerId";
    private static final String PARAM_AUDIOTRACK = "audiotrack";
    private static final String PARAM_FEATURED_SINGERS = "featured_singer[]";
    private static final String PARAM_AUDIO_NAME = "audioName";
    private static final String PARAM_AUDIO_ID = "audioId";
    private static final String PARAM_COST = "audioCost";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String audioName = request.getParameter(PARAM_AUDIO_NAME);
            String[] featuredSingers = request.getParameterValues(PARAM_FEATURED_SINGERS);
            Part partAudio = request.getPart(PARAM_AUDIOTRACK);
            BigDecimal cost = null;
            long audioId=0;
            try {
                cost = new BigDecimal(request.getParameter(PARAM_COST));
                audioId= Long.parseLong(request.getParameter(PARAM_AUDIO_ID));
            } catch (NumberFormatException e) {
                LOGGER.log(Level.ERROR, e);
            }
            try {
                AudiotrackService audiotrackService = new AudiotrackService();
                long albumId= Long.parseLong(request.getParameter(PARAM_ALBUM_ID));
                if(audioId > 0){
                    audiotrackService.updateAudio(audioId, audioName, cost, partAudio, albumId, featuredSingers);
                } else{
                    long singerId= Long.parseLong(request.getParameter(PARAM_SINGER_ID));
                    audiotrackService.insertNewAudiotrack(audioName, partAudio, cost,  albumId, singerId, featuredSingers);
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
