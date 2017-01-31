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

public class CommandAddAlbum implements ICommandAction {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String PARAM_ALBUM_NAME = "albumName";
    private static final String PARAM_ALBUM_ID = "albumId";
    private static final String PARAM_SINGER_ID = "singerId";
    private static final String PARAM_RELEASE_YEAR = "releaseYear";
    private static final String PARAM_COVER = "cover";
    private static final String PARAM_AUDIOTRACK = "audiotrack";
    private static final String PARAM_COST = "audioCost";
    private static final String PARAM_FEATURED_SINGER = "featured_singer[]";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String albumName = request.getParameter(PARAM_ALBUM_NAME);
            Part partCover = request.getPart(PARAM_COVER);
            int releaseYear = 0;
            long albumId=0;
            try {
                releaseYear=Integer.parseInt(request.getParameter(PARAM_RELEASE_YEAR));
                albumId= Long.parseLong(request.getParameter(PARAM_ALBUM_ID));
            } catch (NumberFormatException e) {
                LOGGER.log(Level.ERROR, e);
            }
            try {
                AlbumService albumService = new AlbumService();
                if(albumId > 0){
                    albumService.updateAlbum(albumId, albumName, releaseYear, partCover);
                } else{
                    long singerId= Long.parseLong(request.getParameter(PARAM_SINGER_ID));
                    String[] featuredSingers = request.getParameterValues(PARAM_FEATURED_SINGER);
                    albumId = albumService.insertNewAlbum(albumName, releaseYear, partCover);
                    AudiotrackService audiotrackService = new AudiotrackService();
                    String audioName = request.getParameter(PARAM_ALBUM_NAME);
                    Part partAudio = request.getPart(PARAM_AUDIOTRACK);
                    BigDecimal cost = new BigDecimal(request.getParameter(PARAM_COST));
                    audiotrackService.insertNewAudiotrack(audioName, partAudio, cost, albumId, singerId, featuredSingers);
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
