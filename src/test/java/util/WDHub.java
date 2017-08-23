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

    public static WebDriver build() {
        System.setProperty("webdriver.chrome.driver" ,  new File("src/test/resources/chromedriver")
                .getAbsolutePath());
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
