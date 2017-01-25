package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.UserDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.manager.ValidationManager;

public class RegistrationService extends AbstractService {

    public boolean checkLoginExist(String login) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUserByLogin(login);
            return user == null;
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
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
                    !validator.checkName(user.getLastName())){
                return false;
            }
            connection = pool.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            if(user.getId()== 0){
                return false;
            }
            userDAO.addInfo(user);
            return true;
        } catch ( DAOException e) {
            throw new ServiceException(e);
        }finally {
            restorePoolConnection(pool, connection);
        }
    }

}
