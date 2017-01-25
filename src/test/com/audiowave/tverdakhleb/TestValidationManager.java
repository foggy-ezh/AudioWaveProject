package test.com.audiowave.tverdakhleb;

import com.audiowave.tverdakhleb.manager.ValidationManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestValidationManager {
    @DataProvider(name = "password")
    public static Object[][] passwordForTest() {
        return new Object[][] {{"123456",false}, {"22",false}, {"2sssss",false}, {"DDDDv332",true}};
    }

    @Test(dataProvider = "password")
    public void passwordValidationTest(String password, boolean expected) {
        Assert.assertEquals(new ValidationManager().checkPassword(password),expected);
    }

    @DataProvider(name = "login")
    public static Object[][] loginForTest() {
        return new Object[][] {{"123456",false}, {"22",false}, {"2sssss",false}, {"DDDDv332",true}};
    }

    @Test(dataProvider = "login")
    public void loginValidationTest(String login, boolean expected) {
        Assert.assertEquals(new ValidationManager().checkLogin(login),expected);
    }


    @DataProvider(name = "mail")
    public static Object[][] mailForTest() {
        return new Object[][] {{"12.b@.v",false}, {"22",false}, {"2ss@ff.b",false}, {"3d@v4.nn",true}};
    }

    @Test(dataProvider = "mail")
    public void mailValidationTest(String mail, boolean expected) {
        Assert.assertEquals(new ValidationManager().checkMail(mail),expected);
    }

    @DataProvider(name = "name")
    public static Object[][] nameForTest() {
        return new Object[][] {{"12.b@.v",false}, {"Ef22",false}, {"vsvv",false}, {"YTgg",true}};
    }

    @Test(dataProvider = "name")
    public void nameValidationTest(String name, boolean expected) {
        Assert.assertEquals(new ValidationManager().checkName(name),expected);
    }
}
