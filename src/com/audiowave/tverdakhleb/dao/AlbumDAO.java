package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.PreparedStatement;
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
    private static final String SQL_SELECT_POPULAR ="SELECT * FROM album as album1" +
            " inner join( select album_id from audio_track as audio1 " +
            "inner join (select audio_track_id from (select count(user_id) as count, audio_track_id from user_has_audio_track " +
            "group by audio_track_id order by count desc ) as audio2) as audio3 on audio1.audio_track_id = audio3.audio_track_id" +
            " group by audio1.album_id) as album2 on album1.album_id = album2.album_id where album1.album_blocked != 1 LIMIT 4;";

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
    public boolean create(Album entity) throws DAOException {
        return false;
    }

    @Override
    public Album update(Album entity) throws DAOException {
        return null;
    }

    @Override
    void parseResultSet(ResultSet resultSet, List<Album> list) throws DAOException {
        if (resultSet != null){
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
        return findEntityById(SQL_SELECT_BY_ID, id).get(0);
    }

    public List<Album> findPopularAlbums() throws DAOException {
        return findResultSet(SQL_SELECT_POPULAR);
    }
}
