import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class OpenLinkInNewWindow {
    WebDriver chromeDrv;
    WebDriverWait wait;

    @Before
    public void test_setup(){
        WebDriverManager.chromedriver().setup();
        chromeDrv = new ChromeDriver();
        wait = new WebDriverWait(chromeDrv, 10);

        chromeDrv.get("http://localhost/litecart/admin");
        chromeDrv.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='username']")).sendKeys("admin");
        chromeDrv.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='password']")).sendKeys("admin");
        chromeDrv.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @After
    public void test_cleanup(){
        chromeDrv.quit();
    }

    @Test
    public void openLinkInNewWindowTest() {

        //Select country to edit
        chromeDrv.findElement(By.xpath("//span[@class='name'][contains(.,'Countries')]")).click();
        chromeDrv.findElement(By.xpath("//form[@name='countries_form']//tr//td/a")).click();
        int numberOfLinks = chromeDrv.findElements(By.xpath("//i[@class='fa fa-external-link']")).size();

        //Click on every external link
        for (int i=1; i<numberOfLinks; i++) {
            String originalW = chromeDrv.getWindowHandle();
            Set<String> existWs = chromeDrv.getWindowHandles();
            chromeDrv.findElement(By.xpath("(//i[@class='fa fa-external-link'])[1]")).click();
            String newW = wait.until(anyWindowOtherThen(existWs));
            chromeDrv.switchTo().window(newW);
            chromeDrv.close();
            chromeDrv.switchTo().window(originalW);
        }
    }

    public ExpectedCondition<String> anyWindowOtherThen(Set <String>oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }
}
