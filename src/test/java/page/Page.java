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
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
    }

    protected void waitSleepClick(WebElement element) {
        try {
            try {
                waitForJs();
                getWait().until((ExpectedConditions.visibilityOf(element)));
            } catch (TimeoutException e) {
                printError(e, "Timeout!!! Element wasnt found!");
            return;
            }
            if (!element.isEnabled()) {
                getWait().until(ExpectedConditions.elementToBeClickable(element));
            }
        } catch (TimeoutException e) {
            printError(e, "Timeout!!! Element is Disabled! Remove \"disabled \" attribute!");
            return;
        }
        catch (WebDriverException e) {
            printError(e, "WD Exception. Rerun");
            waitSleepClick(element);
        } catch (Exception e) {
            printError(e, "Unknown error");
            return;
        }
        sleep(1);
        element.click();
    }

    protected void sleep(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            System.out.println("Javascript waiter caught an exception. Repeat.");
            e.printStackTrace();
            waitForJs();
        }
    }


    protected void setValueJs(String str) {
        try {
            String jScript = "document.getElementsByTagName('body')[0].innerHTML+='%s';";
            js.executeScript(String.format(jScript, str));
        } catch (JavascriptException e) {
            e.printStackTrace();
            setValueJs(str);
        }
    }

    protected void selectByText(WebElement select, String text) {
        new Select(select).selectByVisibleText(text);
    }

    public void waitForElementVisible(WebElement element) {
        try {
            waitForJs();
            getWait().until((ExpectedConditions.visibilityOf(element)));
        } catch (TimeoutException e) {
            printError(e, "Timeout!!! Element wasnt found!");
        }

    }
}
