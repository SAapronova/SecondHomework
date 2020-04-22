import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    public static WebDriver create(DriverNames webDriverName) {
        if (webDriverName == DriverNames.CHROME) {

            ChromeOptions chromeOptions=new ChromeOptions();
            chromeOptions.addArguments("start-maximized");
            return new ChromeDriver(chromeOptions);

        } else if (webDriverName == DriverNames.FIREFOX) {
            FirefoxOptions firefoxOptions=new FirefoxOptions();
            firefoxOptions.addArguments("start-maximized");
            return new FirefoxDriver(firefoxOptions);

        }
        return null;
    }
}
