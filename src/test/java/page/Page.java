package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import util.WDHub;

import java.sql.Time;
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

    protected void waitSleepClick(WebElement element) {
        try {
            try {
                waitForJs();
                wait.until((ExpectedConditions.visibilityOf(element)));
            } catch (TimeoutException e) {
                printError(e, "Timeout!!! Element wasnt found!");
            return;
            }
            if (!element.isEnabled()) {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            }
        } catch (TimeoutException e) {
            printError(e, "Timeout!!! Element is Disabled! Remove \"disabled \" attribute!");
            waitSleepClick(element);
        }
        catch (WebDriverException e) {
            printError(e, "WD Exception. Rerun");
            waitSleepClick(element);
        } catch (Exception e) {
            printError(e, "Unknown error");
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
    }

    private void printError(Exception e, String errorMessage) {
        System.out.println(x);
        System.out.println(errorMessage);
        e.printStackTrace();
    }

    void waitForJs() {
        try {
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript(
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
}
