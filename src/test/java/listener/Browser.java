package listener;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import support.TestProperties;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Browser {

    private static WebDriver driver;
    private static String browser = TestProperties.loadProperty().getProperty("browser.name");
    private static Capabilities capabilities;
    private static String remoteURL = TestProperties.loadProperty().getProperty("selenium.url");

    public static void initBrowser() {
        try {
            switch (browser.toLowerCase()) {
                case "local":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "chrome":
                    capabilities = DesiredCapabilities.chrome();
                    break;
                case "firefox":
                    capabilities = DesiredCapabilities.firefox();
                    break;
            }
            if (!browser.toLowerCase().equals("local")) {
                driver = new RemoteWebDriver(new URL(remoteURL), capabilities);
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initBrowser();
        }
        return driver;
    }

    public static void closeBrowser() {
        driver.quit();
    }
}

