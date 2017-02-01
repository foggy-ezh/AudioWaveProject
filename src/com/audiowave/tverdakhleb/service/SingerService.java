package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.SingerDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Singer;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.ArrayList;
import java.util.List;


public class SingerService extends AbstractService {


    public List<String> getSingerStartLetter() throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            SingerDAO singerDAO = new SingerDAO(connection);
            return singerDAO.findSingerFirstLetter();
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public List<Singer> getSingers(String symbol, int currentPage) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            SingerDAO singerDAO = new SingerDAO(connection);
            int start = (currentPage - 1) * RECORDS_PER_PAGE;
            List<Singer> list = new ArrayList<>();
            totalPages = (int) Math.ceil(singerDAO.findSingerBySymbol(list, symbol, start, RECORDS_PER_PAGE) * 1.0 / RECORDS_PER_PAGE);
            if (totalPages != 0 && totalPages < currentPage) {
                return getSingers(symbol, totalPages);
            }
            return list;
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public Singer getSingerById(long id) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            SingerDAO singerDAO = new SingerDAO(connection);
            return singerDAO.findSingerById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public void insertNewSinger(String singerName) throws ServiceException {
        String safe = Jsoup.clean(singerName, Whitelist.basic());
        if (!safe.isEmpty()) {
            ConnectionPool pool = ConnectionPool.getInstance();
            ProxyConnection connection = null;
            try {
                connection = pool.getConnection();
                SingerDAO singerDAO = new SingerDAO(connection);
                Singer singer = new Singer(0, safe);
                singerDAO.create(singer);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                restorePoolConnection(pool, connection);
            }
        }
    }

    public void updateSinger(String singerName, long singerId) throws ServiceException {
        String safe = Jsoup.clean(singerName, Whitelist.basic());
        if (!safe.isEmpty()) {
            ConnectionPool pool = ConnectionPool.getInstance();
            ProxyConnection connection = null;
            try {
                connection = pool.getConnection();
                SingerDAO singerDAO = new SingerDAO(connection);
                Singer singer = new Singer(singerId, safe);
                singerDAO.update(singer);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                restorePoolConnection(pool, connection);
            }
        }
    }

    public List<Singer> getFeaturedSingersForAudio(long audioId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            SingerDAO singerDAO = new SingerDAO(connection);
            return singerDAO.findFeaturedSingerByAudiotrackId(audioId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }
}
