package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private static final String SQL_UPDATE_SET_UNBLOCKED = "UPDATE audio_track INNER JOIN album ON album.album_id=audio_track.album_id\n" +
            "SET audio_track_blocked=0 WHERE audio_track_id=? AND album_blocked = 0;";
    private static final String SQL_UPDATE_SET_BLOCKED = "UPDATE audio_track SET audio_track_blocked=1 WHERE audio_track_id=?;";
    private static final String SQL_INSERT_AUDIOTRACK = "INSERT INTO `audio_track` (`audio_track_name`, `audio_track_location`, `audio_track_cost`, `album_id`) VALUES (?, ?, ?, ?);\n";
    private static final String SQL_INSERT_SINGER_HAS_AUDIOTRACK = "INSERT INTO `singer_has_audio_track` (`singer_id`, `audio_track_id`, `featured_musician`) VALUES (?, ?, ?);\n";
    private static final String SQL_UPDATE_AUDIOTRACK = "UPDATE `audio_track` SET `audio_track_name`= ?, `audio_track_cost`= ? WHERE `audio_track_id`= ?;";
    private static final String SQL_UPDATE_AUDIOTRACK_LOCATION = "UPDATE `audio_track` SET `audio_track_location`= ? WHERE `audio_track_id`= ?;";

    public AudiotrackDAO(ProxyConnection connection) {
        super(connection);
    }


    @Override
    public void remove(Audiotrack entity) throws DAOException {
    }

    @Override
    public void create(Audiotrack entity) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_INSERT_AUDIOTRACK, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getLocation());
            stmt.setBigDecimal(3, entity.getCost());
            stmt.setLong(4, entity.getAlbumId());
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

    @Override
    public void update(Audiotrack entity) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_UPDATE_AUDIOTRACK);
            stmt.setString(1, entity.getName());
            stmt.setBigDecimal(2, entity.getCost());
            stmt.setLong(3, entity.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }

    public void updateLocation(Audiotrack entity) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_UPDATE_AUDIOTRACK_LOCATION);
            stmt.setString(1, entity.getLocation());
            stmt.setLong(2, entity.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
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

    public void updateAudiotrackToUnblocked(long albumId) throws DAOException {
        changeBlockedStatus(SQL_UPDATE_SET_UNBLOCKED, albumId);
    }
    public void updateAudiotrackToBlocked(long albumId) throws DAOException {
        changeBlockedStatus(SQL_UPDATE_SET_BLOCKED, albumId);
    }

    public void connectAudiotrackWithSinger(long singerId, long audioId, boolean featured) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_INSERT_SINGER_HAS_AUDIOTRACK);
            stmt.setLong(1, singerId);
            stmt.setLong(2, audioId);
            stmt.setBoolean(3, featured);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }
}
