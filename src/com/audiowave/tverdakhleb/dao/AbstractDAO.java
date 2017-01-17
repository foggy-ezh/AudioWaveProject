package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Entity;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO <T extends Entity> {
    protected ProxyConnection connection;

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll() throws DAOException;
    public abstract T findEntityById(long id) throws DAOException;
    public abstract boolean remove(long id) throws DAOException;
    public abstract boolean remove(T entity) throws DAOException;
    public abstract boolean create(T entity) throws DAOException;
    public abstract T update(T entity) throws DAOException;
    public abstract void parseResultSet(ResultSet resultSet, List<T> list) throws DAOException;

    public void close(Statement statement) throws DAOException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}