package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.SingerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

public class CommandChangeAudio implements ICommandAction {
    private static final String ROLE = "role";
    private static final String PARAM_AUDIO_NAME = "audioName";
    private static final String PARAM_AUDIO_ID = "audioId";
    private static final String PARAM_COST = "cost";
    private static final String PARAM_CHANGE_AUDIO = "changeAudio";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String audioName = request.getParameter(PARAM_AUDIO_NAME);
            long audioId = Long.parseLong(request.getParameter(PARAM_AUDIO_ID));
            BigDecimal cost = new BigDecimal(request.getParameter(PARAM_COST));
            Audiotrack audiotrack = new Audiotrack(audioId, audioName,null, cost,false, 0);
            try {
                audiotrack.setFeaturedSinger(new SingerService().getFeaturedSingersForAudio(audioId));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            session.setAttribute(PARAM_CHANGE_AUDIO, audiotrack);
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
