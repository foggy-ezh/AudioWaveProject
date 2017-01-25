package test.com.audiowave.tverdakhleb;

import com.audiowave.tverdakhleb.dao.AlbumDAO;
import com.audiowave.tverdakhleb.dao.AudiotrackDAO;
import com.audiowave.tverdakhleb.dao.UserDAO;
import com.audiowave.tverdakhleb.dbconnection.ConnectionPool;
import com.audiowave.tverdakhleb.dbconnection.ProxyConnection;
import com.audiowave.tverdakhleb.entity.Album;
import com.audiowave.tverdakhleb.entity.Audiotrack;
import com.audiowave.tverdakhleb.entity.User;
import com.audiowave.tverdakhleb.exception.DAOException;
import org.testng.annotations.Test;

import java.util.List;


public class TestDAO {

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

            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findUserByLogin("artem");
            System.out.println(user.getLogin());

        } catch ( DAOException e) {
            e.printStackTrace();
        } finally {
            pool.restoreConnection(connection);
            pool.closeConnections();
        }
    }

    @Test
    public void testInsert() {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection connection = null;
        try {
            connection = pool.getConnection();
            User user = new User();
            user.setLogin("test");
            user.setPassword("test");
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            System.out.println(user.getId());
        } catch ( DAOException e) {
            e.printStackTrace();
        } finally {
            pool.restoreConnection(connection);
            pool.closeConnections();
        }
    }
}
