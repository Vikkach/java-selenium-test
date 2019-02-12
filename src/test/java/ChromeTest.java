import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeTest {
    WebDriver chromeDrv;

    @Before
    public void test_setup(){
        WebDriverManager.firefoxdriver().setup();
        chromeDrv = new ChromeDriver();
    }

    @After
    public void test_cleanup(){
        chromeDrv.quit();
    }

    @Test
    public void chromeTest(){
        chromeDrv.get("https://www.seleniumhq.org");
    }

}
