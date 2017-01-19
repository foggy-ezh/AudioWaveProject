package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Singer;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SingerDAO extends AbstractDAO<Singer> {
    private static final String COLUMN_ID = "singer_id";
    private static final String COLUMN_NAME = "singer_name";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM singer WHERE singer_id=?";
    private static final String SQL_SELECT_BY_AUDIOTRACK_ID = "SELECT * FROM singer\n" +
            "INNER JOIN singer_has_audio_track\n" +
            " ON singer.singer_id = singer_has_audio_track.singer_id\n" +
            "\tINNER JOIN audio_track \n" +
            "\tON singer_has_audio_track.audio_track_id = audio_track.audio_track_id\n" +
            "WHERE singer_has_audio_track.featured_musician !=1 AND audio_track.audio_track_id = ?;";
    private static final String SQL_SELECT_BY_ALBUM_ID = "SELECT * FROM singer\n" +
            "INNER JOIN singer_has_audio_track\n" +
            "ON singer.singer_id = singer_has_audio_track.singer_id\n" +
            "\tINNER JOIN audio_track \n" +
            "\tON singer_has_audio_track.audio_track_id = audio_track.audio_track_id\n" +
            "\t\tINNER JOIN album \n" +
            "\t\tON album.album_id = audio_track.album_id\n" +
            "WHERE singer_has_audio_track.featured_musician !=1 AND album.album_id = ?\n" +
            "GROUP BY album.album_id;";

    public SingerDAO(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public boolean remove(long id) throws DAOException {
        return false;
    }

    @Override
    public boolean remove(Singer entity) throws DAOException {
        return false;
    }

    @Override
    public boolean create(Singer entity) throws DAOException {
        return false;
    }

    @Override
    public Singer update(Singer entity) throws DAOException {
        return null;
    }

    @Override
    public void parseResultSet(ResultSet resultSet, List<Singer> list) throws DAOException {
        if (resultSet != null){
            try {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COLUMN_ID);
                    String name = resultSet.getString(COLUMN_NAME);
                    list.add(new Singer(id, name));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public Singer findSingerById(long id) throws DAOException {
        return findEntityById(SQL_SELECT_BY_ID, id).get(0);
    }

    public Singer findSingerByAudiotrackId(long id) throws DAOException {
        return findEntityById(SQL_SELECT_BY_AUDIOTRACK_ID, id).get(0);
    }

    public Singer findSingerByAlbumId(long id) throws DAOException {
        return findEntityById(SQL_SELECT_BY_ALBUM_ID, id).get(0);
    }
}
