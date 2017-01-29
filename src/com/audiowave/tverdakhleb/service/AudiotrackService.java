package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.AudiotrackDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.ServiceException;

public class AudiotrackService extends AbstractService {
    public void changeAudiotrackToUnblocked(long audioId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
            audiotrackDAO.updateAudiotrackToUnblocked(audioId);
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
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
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }
}
