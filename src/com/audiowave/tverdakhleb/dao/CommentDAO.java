package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.AlbumComment;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDAO extends AbstractDAO<AlbumComment> {
    private static final String COLUMN_COMMENT_ID="comment_id";
    private static final String COLUMN_USER_ID="user_id";
    private static final String COLUMN_USER_LOGIN="login";
    private static final String COLUMN_COMMENT="comment";
    private static final String COLUMN_ALBUM_ID="album_id";
    private static final String SQL_SELECT_COMMENTS_BY_ALBUM_ID = "SELECT comment_id, album_comment.user_id, comment, login FROM" +
            " album_comment INNER JOIN user ON user.user_id = album_comment.user_id WHERE album_id = ?;";

    public CommentDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public boolean remove(long id) throws DAOException {
        return false;
    }

    @Override
    public boolean remove(AlbumComment entity) throws DAOException {
        return false;
    }

    @Override
    public void create(AlbumComment entity) throws DAOException {

    }

    @Override
    public AlbumComment update(AlbumComment entity) throws DAOException {
        return null;
    }

    @Override
    void parseResultSet(ResultSet resultSet, List<AlbumComment> list) throws DAOException {
        if (resultSet != null){
            try {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COLUMN_COMMENT_ID);
                    String comment = resultSet.getString(COLUMN_COMMENT);
                    AlbumComment albumComment = new AlbumComment(id, comment);
                    albumComment.setUserId(resultSet.getLong(COLUMN_USER_ID));
                    albumComment.setUserLogin(resultSet.getString(COLUMN_USER_LOGIN));
                    list.add(albumComment);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    void parseFullResultSet(ResultSet resultSet, List<AlbumComment> list) throws DAOException {
    }

    public List<AlbumComment> findCommentsByAlbumId(long id) throws DAOException {
        return findEntityByParameter(SQL_SELECT_COMMENTS_BY_ALBUM_ID, String.valueOf(id), false);
    }

}
