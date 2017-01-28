package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AudiotrackDAO extends AbstractDAO<Audiotrack> {
    private static final String COLUMN_ID = "audio_track_id";
    private static final String COLUMN_NAME = "audio_track_name";
    private static final String COLUMN_LOCATION = "audio_track_location";
    private static final String COLUMN_COST = "audio_track_cost";
    private static final String COLUMN_BLOCKED = "audio_track_blocked";
    private static final String COLUMN_ALBUM_ID = "album_id";

    private static final String SQL_SELECT_POPULAR = "SELECT * FROM (SELECT audio1.audio_track_id, audio1.audio_track_name," +
            " audio1.audio_track_location, audio1.audio_track_blocked, audio1.album_id, audio1.audio_track_cost, audio3.COUNT FROM audio_track AS audio1" +
            " INNER JOIN ( SELECT COUNT(user_id) AS COUNT,audio_track_id FROM user_has_audio_track GROUP BY audio_track_id ) AS audio3" +
            " ON audio1.audio_track_id = audio3.audio_track_id WHERE audio1.audio_track_blocked = 0) as t1 ORDER BY COUNT DESC limit 9;";
    private static final String SQL_SELECT_BY_ALBUM_ID_ADMIN = "SELECT * FROM audio_track WHERE audio_track.album_id = ?;";
    private static final String SQL_SELECT_BY_ALBUM_ID = "SELECT * FROM audio_track WHERE audio_track_blocked = 0 AND audio_track.album_id = ?;";

    public AudiotrackDAO(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public boolean remove(long id) throws DAOException {
        return false;
    }

    @Override
    public boolean remove(Audiotrack entity) throws DAOException {
        return false;
    }

    @Override
    public void create(Audiotrack entity) throws DAOException {
    }

    @Override
    public Audiotrack update(Audiotrack entity) throws DAOException {
        return null;
    }

    @Override
    void parseFullResultSet(ResultSet resultSet, List<Audiotrack> list) throws DAOException {

    }

    @Override
     void parseResultSet(ResultSet resultSet, List<Audiotrack> list) throws DAOException {
        if (resultSet != null){
            try {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COLUMN_ID);
                    String name = resultSet.getString(COLUMN_NAME);
                    String location = resultSet.getString(COLUMN_LOCATION);
                    BigDecimal cost = resultSet.getBigDecimal(COLUMN_COST);
                    Boolean blocked = resultSet.getBoolean(COLUMN_BLOCKED);
                    long albumID = resultSet.getLong(COLUMN_ALBUM_ID);
                    list.add(new Audiotrack(id, name, location, cost, blocked, albumID));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public List<Audiotrack> findPopularAudiotrack() throws DAOException {
        return findResultSet(SQL_SELECT_POPULAR, false);
    }

    public List<Audiotrack> findNonBlockedAudiotrackByAlbumId(long albumId) throws DAOException {
        return findEntityByParameter(SQL_SELECT_BY_ALBUM_ID, String.valueOf(albumId), false);
    }

    public List<Audiotrack> findAllAudiotrackByAlbumId(long albumId) throws DAOException {
        return findEntityByParameter(SQL_SELECT_BY_ALBUM_ID_ADMIN, String.valueOf(albumId), false);
    }
}
