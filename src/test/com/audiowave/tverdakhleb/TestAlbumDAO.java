package test.com.audiowave.tverdakhleb;

import com.audiowave.tverdakhleb.dao.AlbumDAO;
import com.audiowave.tverdakhleb.dao.AudiotrackDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.exception.DAOException;
import org.testng.annotations.Test;

import java.util.List;


public class TestAlbumDAO {

    @Test
    public void testing() {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            AlbumDAO albumDAO = new AlbumDAO(connection);
            List<Album> popular = albumDAO.findPopularAlbums();
            for ( Album album: popular) {
                System.out.println(album.getReleaseYear());
            }
            AudiotrackDAO audiotrackDAO = new AudiotrackDAO(connection);
            List<Audiotrack> popular1 = audiotrackDAO.findPopularAudiotrack();
            for ( Audiotrack audiotrack: popular1) {
                System.out.println(audiotrack.getName());
            }
        } catch ( DAOException e) {
            e.printStackTrace();
        } finally {
            pool.restoreConnection(connection);
            pool.closeConnections();
        }
    }
}
