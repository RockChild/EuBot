package util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class WDHub {
    static WebDriver driver;
    static FluentWait<WebDriver> wait;
    static int waitTimeOut = 60;
    static String os;

    public static WebDriver build() {
        os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("mac")>=0) {
        System.setProperty("webdriver.chrome.driver" ,  new File("src/test/resources/chromedriver_mac")
                .getAbsolutePath());
        } else if (os.indexOf("win")>=0) {
            System.setProperty("webdriver.chrome.driver" ,  new File("src/test/resources/chromedriver.exe")
                    .getAbsolutePath());
        } else {
            System.setProperty("webdriver.chrome.driver" ,  new File("src/test/resources/chromedriver_linux")
                    .getAbsolutePath());
        }
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, waitTimeOut)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(250, TimeUnit.MILLISECONDS);
        return driver;
    }

    public static WebDriver getWD() {
        return driver;
    }

    public static FluentWait<WebDriver> getWait() {
        return wait;
    }

}
