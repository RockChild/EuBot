package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.WDHub;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

public class Page {

    WebDriver driver;
    FluentWait<WebDriver> wait;
    JavascriptExecutor js;

    String x = "================\n================\n====ReadMe====\n================\n================";

    public Page() {
        driver = WDHub.getWD();
        wait = WDHub.getWait();
        PageFactory.initElements(WDHub.getWD(), this);
        js = (JavascriptExecutor) driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public FluentWait getWait() {
        return wait.withTimeout(300, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
    }

    public FluentWait getMiniWait() {
        return new WebDriverWait(driver, 10)
                .withTimeout(2, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
    }

    protected void waitSleepClick(WebElement element) {
        System.out.println("========waitSleepClick======");
        try {
            while (!isElementAccessable(element));
            clickJs(element);
            waitForJs();
        } catch (Exception e) {
            printError(e, "Unknown error");
            waitSleepClick(element);
            return;
        }
        System.out.println("========waitSleepClick finished======");
    }

    private boolean isElementAccessable(WebElement element) {
        System.out.println("========isElementAccessable=========");
        try {
            waitForElementVisible(element);
            System.out.println("==Info: waiting for element clickable==");
            getWait().until(ExpectedConditions.elementToBeClickable(element));
            System.out.println("==Info: OK!==");
            return true;
        } catch (WebDriverException e) {
            System.out.println("==Error: waiting for element to be visible and clickable generated an exception==");
            e.printStackTrace();
            return false;
        }
    }

    protected void sleep(int secs) {
        System.out.println("==Sleep==");
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            printError(e, "Couldnt sleep");
        }
    }

    protected void printError(Exception e, String errorMessage) {
        System.out.println(x);
        System.out.println(errorMessage);
        e.printStackTrace();
    }

    void waitForJs() {
        try {
            getWait().until(driver -> (js).executeScript(
                    "return document.readyState").equals("complete"));
            System.out.println("==Info: Javascript state is COMPLETE.==");
        } catch (Exception e) {
            printError(e, "==Warn: Javascript waiter caught an exception. Repeat.==");
            waitForJs();
        }
    }


    protected void setValueJs(String str) {
        try {
            String jScript = "document.getElementsByTagName('body')[0].innerHTML+='%s';";
            js.executeScript(String.format(jScript, str));
        } catch (JavascriptException e) {
            printError(e, "==Error: Couldnt set value by JS==");
            setValueJs(str);
        }
    }

    protected void selectByText(WebElement select, String text) {
        new Select(select).selectByVisibleText(text);
    }

    public void waitForElementVisible(WebElement element) {
        try {
            waitForJs();
            System.out.println("==Info: Wait for visible element==");
            getWait().until((ExpectedConditions.visibilityOf(element)));
            System.out.println("==Info: OK!==");
        } catch (TimeoutException e) {
            printError(e, "==Error: Timeout!!! Element wasnt found!==");
        }

    }

    public void clickJs(WebElement element) {
        System.out.println("=======clickJs()========");
        try {
            waitForJs();
            sleep(1);
            System.out.println("==Info: trying to click by js==");
            js.executeScript("arguments[0].click();", element);
            System.out.println("==Info: OK!==");
        } catch (JavascriptException e) {
            System.out.println("Error: JS error while clicking");
            clickJs(element);
        }
        System.out.println("=======clickJs() finished========");
    }
}
