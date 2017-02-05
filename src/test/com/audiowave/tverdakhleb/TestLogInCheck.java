package test.com.audiowave.tverdakhleb;

import com.audiowave.tverdakhleb.exception.ServiceException;
import com.audiowave.tverdakhleb.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestLogInCheck {

    @Test
    public void testHash() {
        System.out.print("BCrypt.hashpw : ");
        String pw1 = "1ABC3e";
        String pw2 = "1ABC3e";

        String h1 = BCrypt.hashpw(pw1, BCrypt.gensalt());
        System.out.println(h1);
        if (BCrypt.checkpw(pw2, h1)) {
            System.out.println("It matches");
        } else {
            Assert.fail("It does not match");
        }
    }


    @Test
    public void testLogin() throws ServiceException {
        UserService service = new UserService();
        String login = "artem";
        System.out.println(service.checkLogin(login));
    }
}
