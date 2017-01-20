package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;

public abstract class AbstractService {
    
    public void restorePoolConnection(ConnectionPool pool, ProxyConnection connection) {
        if (pool != null && connection != null) {
            pool.restoreConnection(connection);
        }
    }
}
