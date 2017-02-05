package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "user_password";
    private static final String COLUMN_IS_ADMIN = "isAdmin";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_AMOUNT_OF_MONEY = "amount_of_money";

    private static final String SQL_SELECT_BY_LOGIN = "SELECT * FROM user WHERE user.login = ?;";
    private static final String SQL_SELECT_USER_INFO_BY_ID = "SELECT * FROM user_info WHERE user_info.user_id = ?;";
    private static final String SQL_INSERT_USER = "INSERT INTO mydb.user (`login`, `user_password`) VALUES (?,?);";
    private static final String SQL_INSERT_USER_INFO = "INSERT INTO mydb.user_info (`user_id`, `email`, `first_name`, `last_name`) VALUES (?, ?, ?, ?);";
    private static final String SQL_UPDATE_USER = "UPDATE user as u, user_info as uf SET u.login = ?,u.user_password=?, uf.email = ?, uf.first_name=?, uf.last_name =? WHERE u.user_id=uf.user_id AND u.user_id= ?;";
    private static final String SQL_UPDATE_AMOUNT_OF_MONEY="UPDATE `user_info` SET `amount_of_money`=`amount_of_money`+ ?  WHERE `user_id`=?;";
    private static final String SQL_INSERT_USER_HAS_AUDIOTRACK = "INSERT INTO `user_has_audio_track` (`user_id`, `audio_track_id`, `final_audio_track_cost`) VALUES (?,?,?);";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public void remove(User entity) throws DAOException {
    }

    @Override
    public void create(User entity) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }

    public void addInfo(User user) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_INSERT_USER_INFO);
            stmt.setLong(1, user.getId());
            stmt.setString(2, user.getMail());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }
    @Override
    public void update(User entity) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_UPDATE_USER);
            stmt.setString(1, entity.getLogin());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getMail());
            stmt.setString(4, entity.getFirstName());
            stmt.setString(5, entity.getLastName());
            stmt.setLong(6, entity.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }

    @Override
    void parseResultSet(ResultSet resultSet, List<User> list) throws DAOException {
        if (resultSet != null){
            try {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COLUMN_ID);
                    String login = resultSet.getString(COLUMN_LOGIN);
                    String password = resultSet.getString(COLUMN_PASSWORD);
                    boolean isAdmin = resultSet.getBoolean(COLUMN_IS_ADMIN);
                    list.add(new User(id, login, password, isAdmin));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public  User findUserByLogin(String login) throws DAOException {
        List<User>  list =  findEntityByParameter(SQL_SELECT_BY_LOGIN, login);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    public  void findUserInfo(User user) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_SELECT_USER_INFO_BY_ID);
            stmt.setLong(1, user.getId());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user.setMail(resultSet.getString(COLUMN_EMAIL));
                user.setFirstName(resultSet.getString(COLUMN_FIRST_NAME));
                user.setLastName(resultSet.getString(COLUMN_LAST_NAME));
                user.setAmountOfMoney(resultSet.getBigDecimal(COLUMN_AMOUNT_OF_MONEY));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }

    public void updateAmountOfMoney(User user, BigDecimal funds) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_UPDATE_AMOUNT_OF_MONEY);
            stmt.setBigDecimal(1, funds);
            stmt.setLong(2, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }

    public void addUserHasAudiotrack(long userId,long audioId, BigDecimal cost) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_INSERT_USER_HAS_AUDIOTRACK);
            stmt.setLong(1, userId);
            stmt.setLong(2, audioId);
            stmt.setBigDecimal(3, cost);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }
}
