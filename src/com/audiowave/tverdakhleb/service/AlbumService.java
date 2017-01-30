package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.AlbumDAO;
import com.audiowave.tverdakhleb.dao.AudiotrackDAO;
import com.audiowave.tverdakhleb.dao.CommentDAO;
import com.audiowave.tverdakhleb.dao.SingerDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.entity.RoleType;
import com.audiowave.tverdakhleb.entity.Singer;
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
    public Album getAlbumById(long id, String role) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            Album album = albumDAO.findAlbumById(id);
            if(album.getBlocked() && !RoleType.ADMIN.getRole().equals(role)){
                album = null;
            }
            if(album != null){
                AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
                if(RoleType.ADMIN.getRole().equals(role)){
                    album.setAudiotracks(audiotrackDAO.findAllAudiotrackByAlbumId(album.getId()));
                }else {
                    album.setAudiotracks(audiotrackDAO.findNonBlockedAudiotrackByAlbumId(album.getId()));
                }
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
    public List<String> getSingerStartLetter(String role) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            if(RoleType.ADMIN.getRole().equals(role)){
                return albumDAO.findAllAlbumFirstLetter();
            }else {
                return albumDAO.findNonBlockedAlbumFirstLetter();
            }
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }

    public List<Album> getAlbums(String symbol, int currentPage, String role) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            int start = (currentPage-1)*RECORDS_PER_PAGE;
            List<Album> list = new ArrayList<>();
            if(RoleType.ADMIN.getRole().equals(role)){
                totalPages = (int) Math.ceil(albumDAO.findAllAlbumBySymbol( list, symbol, start, RECORDS_PER_PAGE)* 1.0/RECORDS_PER_PAGE);
            }else {
                totalPages = (int) Math.ceil(albumDAO.findNonBlockedAlbumBySymbol( list, symbol, start, RECORDS_PER_PAGE)* 1.0/RECORDS_PER_PAGE);
            }
            if(totalPages !=0 && totalPages < currentPage){
                return getAlbums(symbol, totalPages,role);
            }
            return list;
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }
    public List<Album> getPopularAlbum() throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        List<Album> list;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            list = albumDAO.findPopularAlbums();
            SingerDAO singerDAO = new SingerDAO(connection);
            for(Album album : list){
                Singer singer = singerDAO.findSingerByAlbumId(album.getId());
                album.setSinger(singer);
            }
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
        return list;
    }

    public void changeAlbumToUnblocked(long albumId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            albumDAO.updateAlbumToUnblocked(albumId);
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }
    public void changeAlbumToBlocked(long albumId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            albumDAO.updateAlbumToBlocked(albumId);
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }
}
