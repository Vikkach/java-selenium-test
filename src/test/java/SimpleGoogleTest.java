import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SimpleGoogleTest {
    WebDriver drv;

    @Before
    public void googleTest_setup(){
        System.out.println("Test started");
        WebDriverManager.chromedriver().setup();
        drv = new ChromeDriver();
    }

    @After
    public void googleTest_cleanup(){
        System.out.println("Test finished");
        drv.quit();
    }

    @Test
    public void googleTest(){
        drv.get("https://google.com");
        drv.findElement(By.name("q")).sendKeys("123");
    }
}
