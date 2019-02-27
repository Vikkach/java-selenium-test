import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;


public class ProductsAndCartOperations {
    WebDriver chromeDrv;
    WebDriverWait wait;

    @Before
    public void test_setup(){
        WebDriverManager.chromedriver().setup();
        chromeDrv = new ChromeDriver();
        wait = new WebDriverWait(chromeDrv, 10);
    }

    @After
    public void test_cleanup(){
        chromeDrv.quit();
    }

    @Test
    public void createProductTest() {
        chromeDrv.get("http://localhost/litecart/admin");
        chromeDrv.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='username']")).sendKeys("admin");
        chromeDrv.findElement(By.xpath("//span[@class='input-wrapper']//input[@name='password']")).sendKeys("admin");
        chromeDrv.findElement(By.xpath("//button[@type='submit']")).click();

        //Create product
        chromeDrv.findElement(By.xpath("//span[@class='name'][contains(.,'Catalog')]")).click();
        chromeDrv.findElement(By.xpath("//a[@class='button'][contains(@href, 'edit_product')]")).click();
        String productName = "Little Pony" + System.currentTimeMillis();
        fillAllGeneralFields(productName);
        chromeDrv.findElement(By.xpath("//a[@href='#tab-information']")).click();
        fillAllInformationFields();
        chromeDrv.findElement(By.xpath("//a[@href='#tab-prices']")).click();
        fillAllPricesFields();
        chromeDrv.findElement(By.xpath("//button[@name='save']")).click();

        //Check product created
        chromeDrv.findElement(By.xpath("//span[@class='name'][contains(.,'Catalog')]")).click();
        Assert.assertNotEquals(0, chromeDrv.findElements(By.xpath("//a[contains(.,'" + productName + "')]")).size());
    }

    @Test
    public void addAndDeleteProductFromCart() {
        chromeDrv.get("http://localhost/litecart/");

        //Add product to cart
        for (int i=0; i<3; i++) {
            chromeDrv.findElement(By.xpath("//div[@id='box-most-popular']//li[1]")).click();
            if (chromeDrv.findElements(By.xpath("//select[@name='options[Size]']")).size() > 0) {
                chromeDrv.findElement(By.xpath("//select[@name='options[Size]']")).click();
                chromeDrv.findElement(By.xpath("//select[@name='options[Size]']/option[@value='Small']")).click();
            }
            chromeDrv.findElement(By.xpath("//button[@name='add_cart_product']")).click();
            int numberOfProducts = Integer.parseInt(chromeDrv.findElement(By.xpath("//span[@class='quantity']")).getText());
            String currentNumberOfProducts = Integer.toString(numberOfProducts + 1);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='quantity']"), currentNumberOfProducts));
            chromeDrv.findElement(By.xpath("//a[@href='/litecart/']")).click();
        }

        //Delete product from cart
        chromeDrv.findElement(By.xpath("//div[@id='cart']/a[@class='link']")).click();
        while (chromeDrv.findElements(By.xpath("//div[@id='checkout-cart-wrapper']//em")).size() == 0) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='remove_cart_item']")));
            int numberOfElements = chromeDrv.findElements(By.xpath("//div[@id='order_confirmation-wrapper']//tr/td[@class='item']")).size();
            chromeDrv.findElement(By.xpath("//button[@name='remove_cart_item']")).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@id='order_confirmation-wrapper']//tr/td[@class='item']"), numberOfElements-1));
        }
        chromeDrv.findElement(By.xpath("//i[@class='fa fa-home']")).click();
        Assert.assertEquals("0", chromeDrv.findElement(By.xpath("//span[@class='quantity']")).getText());
    }

    public void fillAllGeneralFields(String productName){
        chromeDrv.findElement(By.xpath("//input[@value='1'][@type='radio']")).click();
        chromeDrv.findElement(By.xpath("//input[@name='name[en]'][@type='text']")).sendKeys(productName);
        chromeDrv.findElement(By.xpath("//input[@name='code']")).sendKeys("123");
        chromeDrv.findElement(By.xpath("//input[@name='product_groups[]'][@value='1-3']")).click();
        chromeDrv.findElement(By.xpath("//input[@name='quantity']")).sendKeys("100");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.jpg").getFile());
        chromeDrv.findElement(By.xpath("//input[@name='new_images[]']")).sendKeys(file.getAbsolutePath());
        chromeDrv.findElement(By.xpath("//input[@name='date_valid_from']")).sendKeys("01/01/2000");
        chromeDrv.findElement(By.xpath("//input[@name='date_valid_to']")).sendKeys("01/01/2020");

    }

    public void fillAllInformationFields(){
        chromeDrv.findElement(By.xpath("//select[@name='manufacturer_id']")).click();
        chromeDrv.findElement(By.xpath("//select[@name='manufacturer_id']/option[@value='1']")).click();
        chromeDrv.findElement(By.xpath("//input[@name='keywords']")).sendKeys("pony");
        chromeDrv.findElement(By.xpath("//input[@name='short_description[en]']")).sendKeys("Friendship Is Magic");
        chromeDrv.findElement(By.xpath("//div[@class='trumbowyg-editor']")).sendKeys("Discover the magic of friendship with the My Little Pony: The Movie My Magical Princess Twilight Sparkle figure, a pony with Pegasus wings and a light-up unicorn horn!");
        chromeDrv.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("My Little Pony");
        chromeDrv.findElement(By.xpath("//input[@name='meta_description[en]']")).sendKeys("My Little Pony");

    }

    public void fillAllPricesFields(){
        chromeDrv.findElement(By.xpath("//input[@name='purchase_price']")).sendKeys("10");
        chromeDrv.findElement(By.xpath("//select[@name='purchase_price_currency_code']")).click();
        chromeDrv.findElement(By.xpath("//select[@name='purchase_price_currency_code']/option[@value='USD']")).click();
        chromeDrv.findElement(By.xpath("//input[@name='prices[USD]']")).sendKeys("10");
        chromeDrv.findElement(By.xpath("//input[@name='prices[EUR]']")).sendKeys("20");
    }

}
