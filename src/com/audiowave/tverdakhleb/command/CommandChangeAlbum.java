package com.audiowave.tverdakhleb.command;

import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.RoleType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandChangeAlbum implements ICommandAction{
    private static final String ROLE = "role";
    private static final String PARAM_ALBUM_NAME = "albumName";
    private static final String PARAM_ALBUM_ID = "albumId";
    private static final String PARAM_RELEASE_YEAR = "releaseYear";
    private static final String PARAM_CHANGE_ALBUM = "changeAlbum";

    @Override
    public String execute(HttpServletRequest request) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (RoleType.ADMIN.getRole().equals(session.getAttribute(ROLE))) {
            String albumName = request.getParameter(PARAM_ALBUM_NAME);
            long albumId = Long.parseLong(request.getParameter(PARAM_ALBUM_ID));
            int releaseYear = Integer.parseInt(request.getParameter(PARAM_RELEASE_YEAR));
            Album changeAlbum = new Album(albumId,albumName,releaseYear);
            session.setAttribute(PARAM_CHANGE_ALBUM, changeAlbum);
        }
        setProcessRedirect(request);
        String previousPage = getPreviousPage(request);
        return previousPage.equals("null") ? HOME_PAGE : previousPage;
    }
}
