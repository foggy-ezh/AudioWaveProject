package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "user_password";
    private static final String COLUMN_IS_ADMIN = "isAdmin";
    private static final String COLUMN_DISCOUNT_ID = "discount_id";

    private static final String SQL_SELECT_BY_LOGIN = "SELECT * FROM user WHERE user.login = ?;";
    private static final String SQL_INSERT_USER = "INSERT INTO mydb.user (`login`, `user_password`) VALUES (?,?);";
    private static final String SQL_INSERT_USER_INFO = "INSERT INTO mydb.user_info (`user_id`, `email`, `first_name`, `last_lastname`) VALUES (?, ?, ?, ?);";

    public UserDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public boolean remove(long id) throws DAOException {
        return false;
    }

    @Override
    public boolean remove(User entity) throws DAOException {
        return false;
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
    public User update(User entity) throws DAOException {
        return null;
    }

    @Override
    void parseFullResultSet(ResultSet resultSet, List<User> list) throws DAOException {

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
                    long discountId = resultSet.getLong(COLUMN_DISCOUNT_ID);
                    list.add(new User(id, login, password, isAdmin, discountId));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public  User findUserByLogin(String login) throws DAOException {
        List<User>  list =  findEntityByParameter(SQL_SELECT_BY_LOGIN, login, false);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
