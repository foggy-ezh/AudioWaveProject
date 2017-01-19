package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AudiotrackDAO extends AbstractDAO<Audiotrack> {
    private static final String COLUMN_ID = "audio_track_id";
    private static final String COLUMN_NAME = "audio_track_name";
    private static final String COLUMN_LOCATION = "audio_track_location";
    private static final String COLUMN_COST = "audio_track_cost";
    private static final String COLUMN_BLOCKED = "audio_track_blocked";
    private static final String COLUMN_ALBUM_ID = "album_id";

    private static final String SQL_SELECT_POPULAR = "SELECT *\n" +
            "        FROM\n" +
            "            audio_track AS audio1\n" +
            "        INNER JOIN\n" +
            "            (\n" +
            "                SELECT\n" +
            "                    audio_track_id\n" +
            "                FROM\n" +
            "                    (\n" +
            "                        SELECT\n" +
            "                            COUNT(user_id) AS COUNT,\n" +
            "                            audio_track_id\n" +
            "                        FROM\n" +
            "                            user_has_audio_track\n" +
            "                        GROUP BY\n" +
            "                            audio_track_id\n" +
            "                        ORDER BY\n" +
            "                            COUNT DESC ) AS audio2) AS audio3\n" +
            "        ON\n" +
            "            audio1.audio_track_id = audio3.audio_track_id\n" +
            "            LIMIT 9;";

    public AudiotrackDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<Audiotrack> findAll() throws DAOException {
        return null;
    }

    @Override
    public Audiotrack findEntityById(long id) throws DAOException {
        return null;
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
    public boolean create(Audiotrack entity) throws DAOException {
        return false;
    }

    @Override
    public Audiotrack update(Audiotrack entity) throws DAOException {
        return null;
    }

    @Override
    public void parseResultSet(ResultSet resultSet, List<Audiotrack> list) throws DAOException {
        if (resultSet != null){
            try {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COLUMN_ID);
                    String name = resultSet.getString(COLUMN_NAME);
                    String location = resultSet.getString(COLUMN_LOCATION);
                    double cost = resultSet.getDouble(COLUMN_COST);
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
        return findResultSet(SQL_SELECT_POPULAR);
    }
}
