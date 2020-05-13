import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.selenium.factory.WebDriverFactory;

import java.security.Key;
import java.util.concurrent.TimeUnit;

public class SecondTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SecondTest.class);


    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) throws Exception {
        driver = DriverFactory.create(browser);
    }

    @Test
    public void testOtus() throws InterruptedException {
        Actions action=new Actions(driver);
        WebDriverWait wait=(new WebDriverWait(driver,40));

        driver.get("https://market.yandex.ru/");
        driver.findElement(By.xpath("//input[@id='header-search']")).sendKeys("Мобильные телефоны",Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement zte= driver.findElement(By.xpath("//span[contains(text(),'ZTE')]"));
        logger.info("Найден ZTE");
        WebElement xiaomi= driver.findElement(By.xpath("//span[contains(text(),'Xiaomi')]"));
        logger.info("Найден Xiaomi");
        action.moveToElement(zte);
        zte.click();
        action.moveToElement(xiaomi);
        xiaomi.click();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        WebElement  sort=driver.findElement(By.xpath("//a[@class=contains(text(),'по цене')]"));
        action.moveToElement(sort).build().perform();
        sort.click();
        logger.info("Отсортирован  список товаров по цене ");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@title,'Смартфон ZTE Blade L130')]")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//img[contains(@title,'Смартфон ZTE Blade L130')]")));
        WebElement compare_zte=driver.findElement(By.xpath("//a[contains(@title, 'ZTE')]/..//div[contains(@data-bem, 'compare')]"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", compare_zte);
        logger.info("ZTE добавлен к сравнению");
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'добавлен к сравнению') and contains(text(), 'ZTE')]")).isDisplayed());
        logger.info("Отобразилась плашка 'Товар ZTE добавлен к сравнению'");


        WebElement compare_xiaomi=driver.findElement(By.xpath("//a[contains(@title, 'Xiaomi')]/..//div[contains(@data-bem, 'compare')]"));
        executor.executeScript("arguments[0].click();", compare_xiaomi);
        logger.info("Xiaomi добавлен к сравнению");
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'добавлен к сравнению') and contains(text(), 'Xiaomi')]")).isDisplayed());
        logger.info("Отобразилась плашка 'Товар Xiaomi добавлен к сравнению'");



        WebElement compare_button=driver.findElement(By.xpath("//div[@class='popup-informer__controls']//span[contains(text(),'Сравнить')]"));
        action.moveToElement(compare_button).click(compare_button);
        action.perform();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        logger.info("Открыта страница Сравнение товаров");
        int count=driver.findElements(By.xpath("//a[@class='n-compare-head__name link']")).size();
        Assert.assertEquals(count,2);
        logger.info(" В списке товаров 2 позиции");


        driver.findElement(By.xpath("//span[@class='link n-compare-show-controls__all']")).click();
        logger.info("Открыты Все характеристики");

        WebElement operation_system=driver.findElement(By.xpath("//div[contains(text(),'Операционная система')]"));
        wait.until(ExpectedConditions.visibilityOf(operation_system));
        Assert.assertTrue(operation_system.isDisplayed());

        driver.findElement(By.xpath("//span[@class='link n-compare-show-controls__diff']")).click();
        wait.until(ExpectedConditions.invisibilityOf(operation_system));
        logger.info("Открыты различающиеся характеристики");
        Assert.assertFalse(operation_system.isDisplayed());

    }

    @AfterTest
    public void quit() {
        driver.close();
        logger.info("Browser closed");
    }
}
