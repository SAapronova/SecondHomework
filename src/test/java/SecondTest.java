import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

public class SecondTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SecondTest.class);
//    private DriverNames chrome = DriverNames.CHROME;
//    private DriverNames fireFox = DriverNames.FIREFOX;

    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) throws Exception {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = DriverFactory.create(DriverNames.CHROME);
            logger.info("Started with chrome");
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = DriverFactory.create(DriverNames.FIREFOX);
            logger.info("Started with firefox");
        } else {

            throw new Exception("Browser is not correct");
        }
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
