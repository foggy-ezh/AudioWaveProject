package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Entity;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO <T extends Entity> {
    protected ProxyConnection connection;

    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    public abstract boolean remove(long id) throws DAOException;
    public abstract boolean remove(T entity) throws DAOException;
    public abstract boolean create(T entity) throws DAOException;
    public abstract T update(T entity) throws DAOException;
    public abstract void parseResultSet(ResultSet resultSet, List<T> list) throws DAOException;

    protected List<T> findEntityById(String sql, long id) throws DAOException{
        List<T> list = new ArrayList<>();
        T entity = null;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            parseResultSet(stmt.executeQuery(), list);
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
        return list;
    }

    protected void close(Statement statement) throws DAOException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    protected List<T> findResultSet(String sql) throws DAOException {
        List<T> list = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            parseResultSet(resultSet, list);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
        return list;
    }
}