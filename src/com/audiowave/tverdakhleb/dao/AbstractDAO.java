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
    abstract void parseResultSet(ResultSet resultSet, List<T> list) throws DAOException;
    abstract void parseFullResultSet(ResultSet resultSet, List<T> list) throws DAOException;

    List<T> findEntityByParameter(String sql, String param, boolean fullParse) throws DAOException{
        List<T> list = new ArrayList<>();
        T entity = null;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, param);
            if (fullParse){parseFullResultSet(stmt.executeQuery(),list);}
            else {
                parseResultSet(stmt.executeQuery(), list);}
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
        return list;
    }

     private void close(Statement statement) throws DAOException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    List<T> findResultSet(String sql, boolean fullParse) throws DAOException {
        List<T> list = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            if (fullParse){parseFullResultSet(resultSet,list);}
            else {
            parseResultSet(resultSet, list);}
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
        return list;
    }
}