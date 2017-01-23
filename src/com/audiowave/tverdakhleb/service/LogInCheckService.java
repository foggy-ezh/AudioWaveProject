package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.UserDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.ServiceException;
import org.mindrot.jbcrypt.BCrypt;

public class LogInCheckService  extends  AbstractService{

    public boolean checkLogin(String login) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUserByLogin(login);
            if(user == null){ return false;}
            return true;
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }

    public boolean checkPassword(String login, String password) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUserByLogin(login);
            if(user == null){ return false;}
            return BCrypt.checkpw(password, user.getPassword());
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }

    public User getCurrentUser(String login) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUserByLogin(login);
            return user;
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }
}