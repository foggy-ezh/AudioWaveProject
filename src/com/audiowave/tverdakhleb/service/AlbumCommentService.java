package com.audiowave.tverdakhleb.service;

import com.audiowave.tverdakhleb.dao.CommentDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.AlbumComment;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.ServiceException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class AlbumCommentService extends AbstractService {
    public void insertNewComment(long userId, long albumId, String text) throws ServiceException {
        String safe = Jsoup.clean(text, Whitelist.basic());
        if (!safe.isEmpty()) {
            ConnectionPool pool = ConnectionPool.getInstance();
            ProxyConnection connection = null;
            try {
                connection = pool.getConnection();
                CommentDAO commentDAO = new CommentDAO(connection);
                AlbumComment comment = new AlbumComment(0, safe);
                comment.setUserId(userId);
                comment.setAlbumId(albumId);
                commentDAO.create(comment);
            } catch (DAOException e) {
                throw new ServiceException(e);
            } finally {
                restorePoolConnection(pool, connection);
            }
        }
    }

    public void deleteComment(long userId, long albumId, long commentId) throws ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            CommentDAO commentDAO = new CommentDAO(connection);
            AlbumComment comment = new AlbumComment(commentId, "");
            comment.setUserId(userId);
            comment.setAlbumId(albumId);
            commentDAO.remove(comment);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } finally {
            restorePoolConnection(pool, connection);
        }
    }
}
