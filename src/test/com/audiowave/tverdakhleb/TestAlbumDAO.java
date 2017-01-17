package test.com.audiowave.tverdakhleb;

import com.audiowave.tverdakhleb.dao.AlbumDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.exception.DAOException;
import com.audiowave.tverdakhleb.exception.DBConnectionException;
import org.testng.annotations.Test;

import java.util.List;


public class TestAlbumDAO {

    @Test
    public void testing() throws DBConnectionException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            List<Album> popular = albumDAO.findPopularAlbums();
            for ( Album album: popular) {
                System.out.println(album.getReleaseYear());
            }
        } catch (DBConnectionException | DAOException e) {
            e.printStackTrace();
        } finally {
            pool.restoreConnection(connection);
            pool.closeConnections();
        }
    }
}
