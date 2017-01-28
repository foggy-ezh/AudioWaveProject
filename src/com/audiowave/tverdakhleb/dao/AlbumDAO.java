package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO extends AbstractDAO<Album> {

    private static final String COLUMN_ID = "album_id";
    private static final String COLUMN_NAME = "album_name";
    private static final String COLUMN_YEAR = "album_release_year";
    private static final String COLUMN_COVER = "album_cover";
    private static final String COLUMN_BLOCKED = "album_blocked";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM album WHERE album_id=?";
    private static final String SQL_SELECT_POPULAR = "SELECT * FROM album AS album1 INNER JOIN (SELECT * FROM " +
            "(SELECT audio1.audio_track_id, audio1.audio_track_blocked, audio1.album_id, audio3.COUNT FROM audio_track AS audio1 " +
            "INNER JOIN (SELECT COUNT(user_id) AS COUNT, audio_track_id FROM user_has_audio_track GROUP BY audio_track_id ) AS audio3 " +
            "ON audio1.audio_track_id = audio3.audio_track_id WHERE audio1.audio_track_blocked = 0) as t1 GROUP BY album_id) as t2" +
            " ON album1.album_id = t2.album_id WHERE album1.album_blocked = 0 ORDER BY COUNT DESC LIMIT 4;\n";
    private static final String SQL_SELECT_BY_SINGER_ID = "SELECT t1.* FROM album AS t1 INNER JOIN audio_track AS t2 ON t1.album_id=t2.album_id" +
            " INNER JOIN singer_has_audio_track AS t3 ON t2.audio_track_id=t3.audio_track_id INNER JOIN singer AS t4 ON t3.singer_id=t4.singer_id" +
            "  WHERE t3.featured_musician != 1 AND t1.album_blocked !=1 AND t4.singer_id = ?  GROUP BY t1.album_id;";
    private static final String SQL_SELECT_LETTER_NOT_BLOCKED ="SELECT UPPER ( SUBSTRING(album_name, 1, 1)) AS letter "+
    "FROM album WHERE album_blocked = 0 GROUP BY letter ORDER BY letter;";
    private static final String SQL_SELECT_LETTER = "SELECT UPPER ( SUBSTRING(album_name, 1, 1)) AS letter \n" +
            "FROM album GROUP BY letter ORDER BY letter;";
    private static final String SQL_SELECT_BY_SYMBOL ="SELECT SQL_CALC_FOUND_ROWS * FROM(SELECT album_id, album_name, album_cover, " +
            "album_release_year, album_blocked ,SUBSTRING(album_name, 1, 1) AS letter FROM album ORDER BY album_name) as album1 \n" +
            "WHERE album1.letter LIKE ? LIMIT ?, ?;";
    private static final String SQL_SELECT_BY_SYMBOL_NOT_BLOCKED ="SELECT SQL_CALC_FOUND_ROWS * FROM(SELECT album_id, album_name, album_cover, " +
            "album_release_year, album_blocked ,SUBSTRING(album_name, 1, 1) AS letter FROM album ORDER BY album_name) as album1 \n" +
            "WHERE album1.letter LIKE ? AND album1.album_blocked = 0 LIMIT ?, ?;";

    public AlbumDAO(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public boolean remove(long id) throws DAOException {
        return false;
    }

    @Override
    public boolean remove(Album entity) throws DAOException {
        return false;
    }

    @Override
    public void create(Album entity) throws DAOException {
    }

    @Override
    public Album update(Album entity) throws DAOException {
        return null;
    }

    @Override
    void parseFullResultSet(ResultSet resultSet, List<Album> list) throws DAOException {
    }

    @Override
    void parseResultSet(ResultSet resultSet, List<Album> list) throws DAOException {
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COLUMN_ID);
                    String albumName = resultSet.getString(COLUMN_NAME);
                    int releaseYear = resultSet.getInt(COLUMN_YEAR);
                    String coverURI = resultSet.getString(COLUMN_COVER);
                    Boolean blocked = resultSet.getBoolean(COLUMN_BLOCKED);
                    list.add(new Album(id, albumName, releaseYear, coverURI, blocked));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public Album findAlbumById(long id) throws DAOException {
        List<Album> list = findEntityByParameter(SQL_SELECT_BY_ID, String.valueOf(id), false);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<Album> findAlbumBySingerId(long id) throws DAOException {
        return findEntityByParameter(SQL_SELECT_BY_SINGER_ID, String.valueOf(id), false);
    }

    public List<Album> findPopularAlbums() throws DAOException {
        return findResultSet(SQL_SELECT_POPULAR, false);
    }

    public List<String> findNonBlockedAlbumFirstLetter() throws DAOException {
        return findFirstLetter(SQL_SELECT_LETTER_NOT_BLOCKED);
    }
    public List<String> findAllAlbumFirstLetter() throws DAOException {
        return findFirstLetter(SQL_SELECT_LETTER);
    }

    public int findNonBlockedAlbumBySymbol(List<Album> list, String symbol, int start, int count) throws DAOException {
        return findEntityBySymbol(SQL_SELECT_BY_SYMBOL_NOT_BLOCKED, list, symbol, start, count);
    }
    public int findAllAlbumBySymbol(List<Album> list, String symbol, int start, int count) throws DAOException {
        return findEntityBySymbol(SQL_SELECT_BY_SYMBOL, list, symbol, start, count);
    }

}
