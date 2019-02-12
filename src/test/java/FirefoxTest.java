import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxTest {
    WebDriver firefoxDrv;

    @Before
    public void test_setup(){
        WebDriverManager.firefoxdriver().setup();
        firefoxDrv = new FirefoxDriver();
    }

    @After
    public void test_cleanup(){
        firefoxDrv.quit();
    }


    @Test
    public void firefoxTest(){
        firefoxDrv.get("https://www.seleniumhq.org");
    }
}
