import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import ru.stqa.selenium.factory.WebDriverFactory;

public class SecondTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SecondTest.class);


    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) throws Exception {
        driver = DriverFactory.create(browser);
    }

    @Test
    public void testOtus() {
        driver.get("https://otus.ru/");
        System.out.println(driver.getTitle() + "===");
    }

    @AfterTest
    public void quit() {
        driver.close();
        logger.info("Browser closed");
    }
}
