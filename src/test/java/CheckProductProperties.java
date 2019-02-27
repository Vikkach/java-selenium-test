import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class CheckProductProperties {

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
    public void productPropertiesTest(){
        chromeDrv.get("http://localhost/litecart");
        WebElement productNameMainPage = chromeDrv.findElement(By.xpath("//div[@id='box-campaigns']/div//li//div[@class='name']"));
        WebElement productRegularPriceMainPage = chromeDrv.findElement(By.xpath("//div[@id='box-campaigns']//s[@class='regular-price']"));
        WebElement productDiscountPriceMainPage = chromeDrv.findElement(By.xpath("//div[@id='box-campaigns']//strong[@class='campaign-price']"));

        String productNameMainPageText = productNameMainPage.getText();
        String productRegularPriceMainPageText = productRegularPriceMainPage.getText();
        String productDiscountPriceMainPageText = productDiscountPriceMainPage.getText();

        Assert.assertEquals(productRegularPriceMainPage.getCssValue("color"), "rgba(119, 119, 119, 1)");
        Assert.assertEquals(productRegularPriceMainPage.getCssValue("text-decoration"), "line-through solid rgb(119, 119, 119)");
        Assert.assertEquals(productDiscountPriceMainPage.getCssValue("color"), "rgba(204, 0, 0, 1)");
        Assert.assertEquals(productDiscountPriceMainPage.getCssValue("font-weight"), "700");


        chromeDrv.findElement(By.xpath("(//div[@id='box-campaigns']/div//li)[1]")).click();
        WebElement productNameItemPage = chromeDrv.findElement(By.xpath("//div[@id='box-product']//h1"));
        WebElement productRegularPriceItemPage = chromeDrv.findElement(By.xpath("//s[@class='regular-price']"));
        WebElement productDiscountPriceItemPage = chromeDrv.findElement(By.xpath("//strong[@class='campaign-price']"));

        Assert.assertEquals(productRegularPriceItemPage.getCssValue("color"), "rgba(102, 102, 102, 1)");
        Assert.assertEquals(productRegularPriceItemPage.getCssValue("text-decoration"), "line-through solid rgb(102, 102, 102)");
        Assert.assertEquals(productDiscountPriceItemPage.getCssValue("color"), "rgba(204, 0, 0, 1)");
        Assert.assertEquals(productDiscountPriceItemPage.getCssValue("font-weight"), "700");

        Assert.assertEquals(productNameItemPage.getText(), productNameMainPageText);
        Assert.assertEquals(productRegularPriceItemPage.getText(), productRegularPriceMainPageText);
        Assert.assertEquals(productDiscountPriceItemPage.getText(), productDiscountPriceMainPageText);
    }
}