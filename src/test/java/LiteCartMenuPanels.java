import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class LiteCartMenuPanels {

    WebDriver chromeDrv;

    @Before
    public void test_setup(){
        WebDriverManager.chromedriver().setup();
        chromeDrv = new ChromeDriver();
    }

    @After
    public void test_cleanup(){
        chromeDrv.quit();
    }

    @Test
    public void liteCartMenuTest(){
        chromeDrv.get("http://localhost/litecart/admin");
        chromeDrv.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='username']")).sendKeys("admin");
        chromeDrv.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='password']")).sendKeys("admin");
        chromeDrv.findElement(By.xpath("//button[@type='submit']")).click();
        int i = 1;
        while (chromeDrv.findElements(By.xpath("(//ul[@id='box-apps-menu']//a)" + "[" + i + "]")).size() > 0) {
            chromeDrv.findElement(By.xpath("(//ul[@id='box-apps-menu']//a)" + "[" + i + "]")).click();
            Assert.assertNotEquals(0, chromeDrv.findElements(By.xpath("//h1")).size());
            i++;
        }
    }
}
