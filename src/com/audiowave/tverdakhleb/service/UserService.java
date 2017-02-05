package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.AudiotrackDAO;
import com.audiowave.tverdakhleb.dao.UserDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.ValidationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.sql.SQLException;

public class UserService extends AbstractService {
    private static final Logger LOGGER = LogManager.getLogger();

    public boolean checkLogin(String login) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUserByLogin(login);
            return user != null;
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
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
            return user != null && BCrypt.checkpw(password, user.getPassword());
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
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
            if (!user.isAdmin()) {
                userDAO.findUserInfo(user);
            }
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public boolean checkLoginExist(String login) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUserByLogin(login);
            return user == null;
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public boolean addUser(User user) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            ValidationManager validator = new ValidationManager();
            if (!validator.checkLogin(user.getLogin()) || !validator.checkPassword(user.getPassword()) ||
                    !validator.checkMail(user.getMail()) || !validator.checkName(user.getFirstName()) ||
                    !validator.checkName(user.getLastName())) {
                return false;
            }
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            if (user.getId() == 0) {
                return false;
            }
            userDAO.addInfo(user);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public boolean updateUser(User user) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            ValidationManager validator = new ValidationManager();
            if (!validator.checkLogin(user.getLogin()) || !validator.checkPassword(user.getPassword()) ||
                    !validator.checkMail(user.getMail()) || !validator.checkName(user.getFirstName()) ||
                    !validator.checkName(user.getLastName())) {
                return false;
            }
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.update(user);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public void increaseUserMoney(User user, BigDecimal funds) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.updateAmountOfMoney(user, funds);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }

    public void buyAudiotrack(User user,long audioId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
            Audiotrack audiotrack = audiotrackDAO.findAllAudiotrackById(audioId);
            if(audiotrack != null) {
                connection.setAutoCommit(false);
                UserDAO userDAO = new UserDAO(connection);
                userDAO.updateAmountOfMoney(user, audiotrack.getCost().negate());
                userDAO.addUserHasAudiotrack(user.getId(), audiotrack.getId(), audiotrack.getCost());
                connection.commit();
            }
        } catch (DAOException | SQLException e) {
            if(connection != null){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    LOGGER.log(Level.ERROR,e1);
                }
            }
            throw new ServiceException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    LOGGER.log(Level.ERROR,e);
                }
            }
            restorePoolConnection(pool, connection);
        }
    }
}

