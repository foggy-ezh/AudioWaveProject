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
import com.audiowave.tverdakhleb.exception.FailedManagerWorkException;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.FileSaveManager;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.http.Part;
import java.math.BigDecimal;
import java.util.List;

public class AudiotrackService extends AbstractService {
    public void changeAudiotrackToUnblocked(long audioId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
            audiotrackDAO.updateAudiotrackToUnblocked(audioId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public void changeAudiotrackToBlocked(long audioId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
            audiotrackDAO.updateAudiotrackToBlocked(audioId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public List<Audiotrack> getPopularAudiotrack() throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        List<Audiotrack> list;
        try {
            connection = pool.getConnection();
            AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
            list = audiotrackDAO.findPopularAudiotrack();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            SingerDAO singerDAO = new SingerDAO(connection);
            for (Audiotrack audio : list) {
                Album album = albumDAO.findAlbumById(audio.getAlbumId());
                audio.setAlbumCoverURI(album.getCoverURI());
                Singer singer = singerDAO.findSingerByAudiotrackId(audio.getId());
                audio.setSinger(singer);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
        return list;
    }

    public void insertNewAudiotrack(String audioName,Part partAudio,BigDecimal cost,long albumId,long singerId,String[] featuredSingers) throws ServiceException {
        audioName = Jsoup.clean(audioName, Whitelist.basic());
        if (!audioName.isEmpty()) {
            ConnectionPool pool = ConnectionPool.getInstance();
            ProxyConnection connection = null;
            try {
                connection = pool.getConnection();
                AlbumDAO albumDAO = new AlbumDAO(connection);
                Album album = albumDAO.findAlbumById(albumId);
                FileSaveManager saveManager = new FileSaveManager();
                String location = saveManager.saveUploadedFile(partAudio, album.getReleaseYear(), album.getAlbumName());
                AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
                Audiotrack audiotrack = new Audiotrack(0, audioName, location, cost, false, albumId);
                audiotrackDAO.create(audiotrack);
                audiotrackDAO.connectAudiotrackWithSinger(singerId, audiotrack.getId(), false);
                SingerDAO singerDAO = new SingerDAO(connection);
                for (String singerName: featuredSingers){
                    if(!singerName.isEmpty()){
                        Singer singer = singerDAO.findSingerByName(singerName);
                        if(singer != null){
                            audiotrackDAO.connectAudiotrackWithSinger(singer.getId(), audiotrack.getId(), true);
                        }
                    }
                }
            } catch (DAOException | FailedManagerWorkException e) {
                throw new ServiceException(e);
            } finally {
                restorePoolConnection(pool, connection);
            }
        }
    }
}

