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

    private static final String SQL_SELECT_ROWS ="SELECT FOUND_ROWS();";

    public abstract boolean remove(long id) throws DAOException;
    public abstract boolean remove(T entity) throws DAOException;
    public abstract void create(T entity) throws DAOException;
    public abstract T update(T entity) throws DAOException;
    abstract void parseResultSet(ResultSet resultSet, List<T> list) throws DAOException;
    abstract void parseFullResultSet(ResultSet resultSet, List<T> list) throws DAOException;

    List<T> findEntityByParameter(String sql, String param, boolean fullParse) throws DAOException{
        List<T> list = new ArrayList<>();
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

    void close(Statement statement) throws DAOException {
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
    List<String> findFirstLetter(String sql) throws DAOException {
        List<String> list = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
        return list;
    }

    int findEntityBySymbol(String sql, List<T> list, String symbol, int start, int count) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, symbol);
            stmt.setInt(2, start);
            stmt.setInt(3, count);
            ResultSet resultSet = stmt.executeQuery();
            parseResultSet(resultSet, list);
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ROWS);
            int totalCount=0;
            if(rs.next()){
                totalCount = rs.getInt(1);}
            return totalCount;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }

    void changeBlockedStatus(String sql, long id) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }
}