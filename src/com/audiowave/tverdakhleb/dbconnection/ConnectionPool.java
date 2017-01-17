package com.audiowave.tverdakhleb.dbconnection;

import com.audiowave.tverdakhleb.exception.DBConnectionException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();
    private static final Lock LOCK = new ReentrantLock();
    private static final int INITIAL_COUNT = 20;

    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);

    private BlockingQueue<ProxyConnection> connections;

    private ConnectionPool(){
        connections = new ArrayBlockingQueue<>(INITIAL_COUNT);
        for (int i = 0; i < INITIAL_COUNT; i++) {
            ConnectionCreator connectionCreator = new ConnectionCreator();
            ProxyConnection proxyConnection = new ProxyConnection(connectionCreator.getConnection());
            connections.offer(proxyConnection);
        }
    }


    public static ConnectionPool getInstance(){
        if (!instanceCreated.get()) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.getAndSet(true);
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() throws DBConnectionException {
        try {
            return connections.take();
        } catch (InterruptedException e) {
            throw new DBConnectionException(e);
        }
    }


    public void restoreConnection(ProxyConnection connection) throws DBConnectionException {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            throw new DBConnectionException(e);
        }
    }

    public void closeConnections() {
        try {
            for (int i = 0; i < INITIAL_COUNT; i++) {
                connections.take().close();
            }
        } catch (SQLException | InterruptedException e) {
            LOG.log(Level.ERROR, e);
        }
    }
}