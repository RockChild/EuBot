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

    @FindBy(css = "li.current>a span")
    private WebElement lblCurrentTab;

    public CheckDetailsPage() {
        super();
    }

    public boolean isPageLoaded() {
        try {
            waitForJs();
            getWait().until(ExpectedConditions.visibilityOf(lblCurrentTab));
            return true;
        } catch (TimeoutException ex) {
            printError(ex, "Tab wasnt opened?????");
            return false;
        }
    }

    public boolean isArticleDuplicated() {
        try {
            getMiniWait().until(ExpectedConditions.visibilityOf(lblDuplicated));
            return true;
        } catch (TimeoutException ex) {
            System.out.println("Article isn't duplicated - Label isn't shown");
            return false;
        }
    }

    public boolean isError() {
        try {
            waitForJs();
            getMiniWait()
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
//        sleep(5);
        waitForJs();
        try {
            if (!submit.isEnabled()) {
                System.out.println("-----------------------------------------------------");
                System.out.println("Starting removing script");
                System.out.println("-----------------------------------------------------");
                js.executeScript("document.getElementById('submit').removeAttribute('disabled');");
//        js.executeScript("document.getElementById('submit').disabled = false;");
//        js.executeScript("arguments[0].removeAttribute('disabled', disabled)", submit);
//        js.executeScript("arguments[0].removeAttribute('disabled')", submit);
                System.out.println("--------------------Disabled attr was removed---------------------");
            } else {
                System.out.println("No need to remove disabling. Element is enabled");
            }
        } catch (JavascriptException e) {
            printError(e, "Something wrong with JS");
            if (withCycle) {
                removeDisabledAttribute(false);
            } else {
                printError(e, "js disabled attr wasn't removed after 2 turns");
            }
        }
    }
}
