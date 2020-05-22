import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class SecondTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SecondTest.class);
    private WebDriverWait wait;
    private Actions action;
    private JavascriptExecutor executor;


    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) throws Exception {
        driver = DriverFactory.create(browser);
        wait = (new WebDriverWait(driver, 40));
        driver.get("https://market.yandex.ru/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        action = new Actions(driver);
        executor = (JavascriptExecutor) driver;
    }


    @Test
    public void testOtus() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='header-search']")).sendKeys("Мобильные телефоны", Keys.ENTER);
        WebElement zte = driver.findElement(By.xpath("//span[contains(text(),'ZTE')]/../../div[@class='LhMupC0dLR']"));
        logger.info("Найден ZTE");
        WebElement xiaomi = driver.findElement(By.xpath("//span[contains(text(),'Xiaomi')]/../../div[@class='LhMupC0dLR']"));
        logger.info("Найден Xiaomi");
        ClickElement(zte);
        ClickElement(xiaomi);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement sort = driver.findElement(By.xpath("//a[@class=contains(text(),'по цене')]"));
        ClickElement(sort);
        logger.info("Отсортирован  список товаров по цене ");

        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        WebElement image_zte = driver.findElement(By.xpath("//img[contains(@title,'Смартфон ZTE Blade L130')]"));
        WaitOfVisibility(image_zte);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement compare_zte = ChooseLocator("//a[contains(@title, 'ZTE')]/..//div[contains(@data-bem, 'compare')]",
                "//img[contains(@title,'ZTE')]/../..//div[@tabindex='0'][1]");
        ClickElement(compare_zte);
        logger.info("ZTE добавлен к сравнению");
        Assert.assertTrue(IsElementDisplayed(driver.findElement(By.xpath("//div[contains(text(), 'добавлен к сравнению') " +
                "and contains(text(), 'ZTE')]"))));
        logger.info("Отобразилась плашка 'Товар ZTE добавлен к сравнению'");

        WebElement compare_xiaomi = ChooseLocator("//a[contains(@title, 'Xiaomi')]/..//div[contains(@data-bem, 'compare')]",
                "//img[contains(@title,'Xiaomi')]/../..//div[@tabindex='0'][1]");
        ClickElement(compare_xiaomi);
        logger.info("Xiaomi добавлен к сравнению");
        Assert.assertTrue(IsElementDisplayed(driver.findElement(By.xpath("//div[contains(text(), 'добавлен к сравнению') " +
                "and contains(text(), 'Xiaomi')]"))));
        logger.info("Отобразилась плашка 'Товар Xiaomi добавлен к сравнению'");
        WebElement compare_button = driver.findElement(By.xpath("//span[contains(text(),'Сравнение')]"));
        ClickElement(compare_button);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        logger.info("Открыта страница Сравнение товаров");
        int count = driver.findElements(By.xpath("//a[@class='n-compare-head__name link']")).size();
        Assert.assertEquals(count, 2);
        logger.info(" В списке товаров 2 позиции");


        driver.findElement(By.xpath("//span[@class='link n-compare-show-controls__all']")).click();
        logger.info("Открыты Все характеристики");

        WebElement operation_system = driver.findElement(By.xpath("//div[contains(text(),'Операционная система')]"));
        WaitOfVisibility(operation_system);
        Assert.assertTrue(IsElementDisplayed(operation_system));

        driver.findElement(By.xpath("//span[@class='link n-compare-show-controls__diff']")).click();
        WaitOfInvisibility(operation_system);
        logger.info("Открыты различающиеся характеристики");
        Assert.assertFalse(IsElementDisplayed(operation_system));

    }

    @AfterTest
    public void quit() {
        if (driver != null) {
            driver.close();
            logger.info("Browser closed");
        }
    }

    public void WaitOfVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void WaitOfInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void ClickElement(WebElement element) {
        try {
            executor.executeScript("arguments[0].click();", element);
        } catch (StaleElementReferenceException e) {
            action.moveToElement(element).build().perform();
            action.click(element).build().perform();
        }
    }

    public WebElement ChooseLocator(String locator1, String locator2) {
        try {
            return driver.findElement(By.xpath(locator1));

        } catch (org.openqa.selenium.NoSuchElementException e) {
            return driver.findElement(By.xpath(locator2));

        }

    }

    public boolean IsElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }
}
