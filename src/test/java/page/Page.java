package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import util.WDHub;

public class Page {

    WebDriver driver;
    FluentWait<WebDriver> wait;

    public Page() {
        driver = WDHub.getWD();
        wait = WDHub.getWait();
        PageFactory.initElements(WDHub.getWD(), this);
    }

    protected void waitSleepClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        element.click();
    }

    protected void setValueJs(String str) {
        try {
            String jScript = "document.getElementsByTagName('body')[0].innerHTML+='%s';";
            ((JavascriptExecutor) driver).executeScript(String.format(jScript, str));
        } catch (JavascriptException e) {
            e.printStackTrace();
            setValueJs(str);
        }
    }

    protected void selectByText(WebElement select, String text) {
        new Select(select).selectByVisibleText(text);
    }
}
