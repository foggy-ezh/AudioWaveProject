package com.audiowave.tverdakhleb.dao;

import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Singer;
import com.audiowave.tverdakhleb.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SingerDAO extends AbstractDAO<Singer> {
    private static final String COLUMN_ID = "singer_id";
    private static final String COLUMN_NAME = "singer_name";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM singer WHERE singer_id=?";
    private static final String SQL_SELECT_BY_AUDIOTRACK_ID = "SELECT * FROM singer " +
            "INNER JOIN singer_has_audio_track " +
            " ON singer.singer_id = singer_has_audio_track.singer_id " +
            "INNER JOIN audio_track " +
            "ON singer_has_audio_track.audio_track_id = audio_track.audio_track_id " +
            "WHERE singer_has_audio_track.featured_musician !=1 AND audio_track.audio_track_id = ?;";
    private static final String SQL_SELECT_FEATURED_BY_AUDIOTRACK_ID = "SELECT * FROM singer " +
            "INNER JOIN singer_has_audio_track " +
            "ON singer.singer_id = singer_has_audio_track.singer_id " +
            "INNER JOIN audio_track " +
            "ON singer_has_audio_track.audio_track_id = audio_track.audio_track_id " +
            "WHERE singer_has_audio_track.featured_musician = 1 AND audio_track.audio_track_id = ?;";
    private static final String SQL_SELECT_BY_ALBUM_ID = "SELECT * FROM singer " +
            "INNER JOIN singer_has_audio_track " +
            "ON singer.singer_id = singer_has_audio_track.singer_id " +
            "INNER JOIN audio_track " +
            "ON singer_has_audio_track.audio_track_id = audio_track.audio_track_id " +
            "INNER JOIN album " +
            "ON album.album_id = audio_track.album_id " +
            "WHERE singer_has_audio_track.featured_musician !=1 AND album.album_id = ? " +
            "GROUP BY album.album_id;";
    private static final String SQL_SELECT_LETTER ="SELECT UPPER ( SUBSTRING(singer_name, 1, 1)) AS letter " +
            "FROM singer GROUP BY letter ORDER BY letter;";
    private static final String SQL_SELECT_BY_SYMBOL ="SELECT SQL_CALC_FOUND_ROWS * FROM(SELECT singer_id, singer_name,SUBSTRING(singer_name, 1, 1) AS letter " +
            "FROM singer ORDER BY singer_name) as singer1 WHERE singer1.letter LIKE ? LIMIT ?, ?;";
    private static final String SQL_SELECT_ROWS ="SELECT FOUND_ROWS();";
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
    public void create(Singer entity) throws DAOException {
    }

    @Override
    public Singer update(Singer entity) throws DAOException {
        return null;
    }

    @Override
    void parseFullResultSet(ResultSet resultSet, List<Singer> list) throws DAOException {
    }

    @Override
    void parseResultSet(ResultSet resultSet, List<Singer> list) throws DAOException {
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
        List<Singer> list = findEntityByParameter(SQL_SELECT_BY_ID, String.valueOf(id), false);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public Singer findSingerByAudiotrackId(long id) throws DAOException {
        List<Singer> list = findEntityByParameter(SQL_SELECT_BY_AUDIOTRACK_ID, String.valueOf(id), false);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    public List<Singer> findFeaturedSingerByAudiotrackId(long id) throws DAOException {
        return findEntityByParameter(SQL_SELECT_FEATURED_BY_AUDIOTRACK_ID, String.valueOf(id), false);
    }

    public Singer findSingerByAlbumId(long id) throws DAOException {
        List<Singer> list = findEntityByParameter(SQL_SELECT_BY_ALBUM_ID, String.valueOf(id), false);
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public List<String> findFirstLetter() throws DAOException {
        List<String> list = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(SQL_SELECT_LETTER);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
        return list;
    }

    public int findSingerBySymbol(List<Singer> list, String symbol, int start, int count) throws DAOException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQL_SELECT_BY_SYMBOL);
            stmt.setString(1, symbol);
            stmt.setInt(2, start);
            stmt.setInt(3, count);
            ResultSet resultSet = stmt.executeQuery();
            parseResultSet(resultSet, list);
            ResultSet rs = stmt.executeQuery(SQL_SELECT_ROWS);
            int totalCount=0;
            if(rs.next()){
                totalCount = rs.getInt(1);}
                return totalCount;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            this.close(stmt);
        }
    }
}
