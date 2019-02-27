import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeTest {
    WebDriver chromeDrv;

    @Before
    public void test_setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("start-fullscreen");
        chromeDrv = new ChromeDriver(opt);
    }

    @After
    public void test_cleanup(){
        chromeDrv.quit();
    }

    @Test
    public void chromeTest(){
        chromeDrv.get("https://www.seleniumhq.org");
        System.out.println("test");
    }

}
