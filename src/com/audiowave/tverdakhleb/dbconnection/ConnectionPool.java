package com.audiowave.tverdakhleb.dbconnection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Lock LOCK = new ReentrantLock();
    private static final int DEFAULT_COUNT = 20;

    private static ConnectionPool instance;
    private static AtomicInteger connectionsCount = new AtomicInteger();
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);

    private BlockingQueue<ProxyConnection> connections;

    private ConnectionPool(int count) {
        connectionsCount.getAndSet(count > 0 ? count : DEFAULT_COUNT);
        connections = new ArrayBlockingQueue<>(connectionsCount.get());
        for (int i = 0; i < connectionsCount.get(); i++) {
            ProxyConnection proxyConnection = new ProxyConnection(new ConnectionCreator().getConnection());
            connections.offer(proxyConnection);
        }
    }

    public static ConnectionPool getInstance() {
        return getInstance(DEFAULT_COUNT);
    }

    public static ConnectionPool getInstance(int count) {
        if (!instanceCreated.get()) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ConnectionPool(count);
                    instanceCreated.getAndSet(true);
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e);
        }
        return connection;
    }


    public void restoreConnection(ProxyConnection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    public void closeConnections() {
        for (int i = 0; i < connectionsCount.get(); i++) {
            try {
                connections.take().close();
            } catch (SQLException | InterruptedException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }
}