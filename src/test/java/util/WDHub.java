package util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class WDHub {
    static WebDriver driver;
    static FluentWait<WebDriver> wait;
    static int waitTimeOut = 120;
    static String os;

    public static WebDriver build() {
        ChromeOptions options = new ChromeOptions();
        os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("mac")>=0) {
            System.setProperty("webdriver.chrome.driver" ,  new File("src/test/resources/chromedriver_mac")
                .getAbsolutePath());
            options.addArguments("--kiosk");
        } else if (os.indexOf("win")>=0) {
            System.setProperty("webdriver.chrome.driver" ,  new File("src/test/resources/chromedriver.exe")
                    .getAbsolutePath());
            options.addArguments("--start-maximized");
        } else {
            System.setProperty("webdriver.chrome.driver" ,  new File("src/test/resources/chromedriver_linux")
                    .getAbsolutePath());
        }
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        wait = new WebDriverWait(driver, new SystemClock(), Sleeper.SYSTEM_SLEEPER, 300, 250)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        return driver;
    }

    public static WebDriver getWD() {
        return driver;
    }

    public static FluentWait<WebDriver> getWait() {
        return wait;
    }

}
