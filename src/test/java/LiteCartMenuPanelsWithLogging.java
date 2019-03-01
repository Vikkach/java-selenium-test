import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class LiteCartMenuPanelsWithLogging {

    EventFiringWebDriver edr;

    @Before
    public void start() throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName("chrome");
        WebDriverManager.chromedriver().setup();
        edr = new EventFiringWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
        edr.register(new Listener());
    }

    @After
    public void stop(){
        edr.quit();
    }

    @Test
    public void liteCartMenuLoggingTest(){
        edr.get("http://localhost/litecart/admin");
        edr.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='username']")).sendKeys("admin");
        edr.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='password']")).sendKeys("admin");
        edr.findElement(By.xpath("//button[@type='submit']")).click();
        int mainMenuSize = edr.findElements(By.xpath("//ul[@id='box-apps-menu']/li/a/*[not(li)][@class='name']/..")).size();
        for (int i = 1; i <= mainMenuSize; i++){
            edr.findElement(By.xpath("(//ul[@id='box-apps-menu']/li/a/*[not(li)][@class='name']/..)" + "[" + i + "]")).click();
            Assert.assertNotEquals(0, edr.findElements(By.xpath("//h1")).size());
            int subMenuSize = edr.findElements(By.xpath("//ul[@id='box-apps-menu']/li/ul/li")).size();
            if (subMenuSize > 0) {
                for (int j = 1; j <=subMenuSize; j++) {
                    edr.findElement(By.xpath("(//ul[@id='box-apps-menu']/li/ul/li/a)" + "[" + j + "]")).click();
                    Assert.assertNotEquals(0, edr.findElements(By.xpath("//h1")).size());
                }
            }
        }
        // To test exception screenshot
        edr.findElement(By.xpath("fail"));
    }
}
