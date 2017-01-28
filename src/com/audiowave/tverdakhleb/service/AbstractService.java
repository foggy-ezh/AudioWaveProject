package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;

public abstract class AbstractService {
    static final int RECORDS_PER_PAGE = 10;
    int totalPages = 0;

    public int getTotalPages() {
        return totalPages;
    }
    
    void restorePoolConnection(ConnectionPool pool, ProxyConnection connection) {
        if (pool != null && connection != null) {
            pool.restoreConnection(connection);
        }
    }
}
