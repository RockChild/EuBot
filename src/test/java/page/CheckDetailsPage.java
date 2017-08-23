package page;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class CheckDetailsPage extends Page {

    @FindBy(id = "submit")
    private WebElement submit;

    @FindBy(xpath = "//h3[contains(text(), 'Дублирование объявления!')]")
    private WebElement lblDuplicated;

    @FindBy(xpath = "//body[contains(text(), 'Error: 502:004')]")
    private WebElement lblError;

    @FindBy(xpath = "//li[@class='current']/a/span")
    private WebElement lblCurrentTab;

    public CheckDetailsPage() {
        super();
    }

    public boolean isPageLoaded() {
        try {
            waitForJs();
            wait.withTimeout(60, TimeUnit.SECONDS).
                    until(ExpectedConditions.visibilityOf(lblCurrentTab));
            return true;
        } catch (TimeoutException ex) {
            printError(ex, "Tab wasnt opened?????");
            return false;
        }
    }

    public boolean isArticleDuplicated() {
        sleep(5);
        if (isPageLoaded()) {
            try {
                wait.until(ExpectedConditions.visibilityOf(lblDuplicated));
                return true;
            } catch (TimeoutException ex) {
                System.out.println("Article isn't duplicated - Label isn't shown");
                return false;
            }
        }
        return false;
    }

    public boolean isError() {
        try {
            waitForJs();
            wait.withTimeout(2, TimeUnit.SECONDS)
                .until(ExpectedConditions.visibilityOf(lblError));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void sumbit() {
        waitSleepClick(submit);
    }

    public void removeDisableAttribute() {
        removeDisabledAttribute(true);
    }

    public void removeDisabledAttribute(boolean withCycle) {
        sleep(5);
        waitForJs();
        try {
            if (!submit.isEnabled()) {
                System.out.println("-----------------------------------------------------");
                System.out.println("Starting removing");
                System.out.println("-----------------------------------------------------");
//        js.executeScript("document.getElementById('submit').disabled = false;");
                js.executeScript("document.getElementById('submit').removeAttribute('disabled');");
//        js.executeScript("arguments[0].removeAttribute('disabled', disabled)", submit);
//        js.executeScript("arguments[0].removeAttribute('disabled')", submit);
                System.out.println("--------------------I tried everything!!!---------------------");
            } else {
                System.out.println("No need to remove disabling");
            }
        } catch (JavascriptException e) {
            printError(e, "Something wrong with JS");
            if (withCycle) {
                removeDisabledAttribute(false);
            } else {
                printError(e, "it didn't worked, js disabled attr");
            }
        }
        sleep(5);

    }
}
