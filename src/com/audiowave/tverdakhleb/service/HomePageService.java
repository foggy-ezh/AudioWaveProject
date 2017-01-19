package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.AlbumDAO;
import com.audiowave.tverdakhleb.dao.AudiotrackDAO;
import com.audiowave.tverdakhleb.dao.SingerDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.entity.Singer;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.DBConnectionException;
import com.audiowave.tverdakhleb.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class HomePageService extends AbstractService {
    public List<Album> getPopularAlbum() throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        List<Album> list = new ArrayList<>();
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            list = albumDAO.findPopularAlbums();
            SingerDAO singerDAO = new SingerDAO(connection);
            for(Album album : list){
                Singer singer = singerDAO.findSingerByAlbumId(album.getId());
                album.setSingerID(singer.getId());
                album.setSingerName(singer.getName());
            }
        } catch (DBConnectionException | DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
        return list;
    }

    public List<Audiotrack> getPopularAudiotrack() throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        List<Audiotrack> list = new ArrayList<>();
        try {
            connection = pool.getConnection();
            AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
            list = audiotrackDAO.findPopularAudiotrack();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            SingerDAO singerDAO = new SingerDAO(connection);
            for(Audiotrack audio : list){
                Album album = albumDAO.findAlbumById(audio.getAlbumID());
                audio.setAlbumCoverURI(album.getCoverURI());
                Singer singer = singerDAO.findSingerByAudiotrackId(audio.getId());
                audio.setSingerID(singer.getId());
                audio.setSingerName(singer.getName());
            }
        } catch (DBConnectionException | DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
        return list;
    }
}
