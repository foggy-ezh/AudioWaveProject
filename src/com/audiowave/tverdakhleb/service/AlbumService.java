package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.AlbumDAO;
import com.audiowave.tverdakhleb.dao.AudiotrackDAO;
import com.audiowave.tverdakhleb.dao.CommentDAO;
import com.audiowave.tverdakhleb.dao.SingerDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class AlbumService extends AbstractService {

    public List<Album> getSingerAlbums(long singerId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            return albumDAO.findAlbumBySingerId(singerId);
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }
    public Album getAlbumById(long id) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            Album album = albumDAO.findAlbumById(id);
            if(album != null){
                AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
                album.setAudiotracks(audiotrackDAO.findAudiotrackByAlbumId(album.getId()));
                SingerDAO singerDAO = new SingerDAO(connection);
                for (Audiotrack audiotrack : album.getAudiotracks()) {
                 audiotrack.setFeaturedSinger(singerDAO.findFeaturedSingerByAudiotrackId(audiotrack.getId()));
                }
                album.setSinger(singerDAO.findSingerByAlbumId(album.getId()));
                CommentDAO commentDAO = new CommentDAO(connection);
                album.setAlbumComments(commentDAO.findCommentsByAlbumId(album.getId()));
            }
            return album;
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }

}
