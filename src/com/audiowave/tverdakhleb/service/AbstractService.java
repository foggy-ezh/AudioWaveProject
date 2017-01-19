package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.exception.DBConnectionException;
import com.audiowave.tverdakhleb.exception.ServiceException;

public class AbstractService {
    private void restorePoolConnection(ConnectionPool pool, ProxyConnection connection) throws ServiceException {
        if (pool != null && connection != null) {
            try {
                pool.restoreConnection(connection);
            } catch (DBConnectionException e) {
                throw new ServiceException(e);
            }
        }
    }
}
