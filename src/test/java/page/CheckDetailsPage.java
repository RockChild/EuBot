package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class CheckDetailsPage extends Page {

    @FindBy(id = "submit")
    private WebElement submit;

    @FindBy(xpath = "//h3[contains(text(), 'Дублирование объявления!')]")
    private WebElement lblDuplicated;

    @FindBy(xpath = "//h1[contains(text(), '504 Gateway Time-out')]")
    private WebElement lblServerError;

    @FindBy(xpath = "//body[contains(text(), 'Error: 502:004')]")
    private WebElement lblError;

    @FindBy(css = "li.current>a span")
    private WebElement lblCurrentTab;

    @FindBy()
    private WebElement btnWaitASec;

    public CheckDetailsPage() {
        super();
    }

    public boolean isPageLoaded() {
        System.out.println("Waiting for 'Check the article' page is loaded");
        try {
            waitForJs();
            getWait().until(ExpectedConditions.visibilityOf(lblCurrentTab));
            System.out.println("Page loaded: " + lblCurrentTab.getText());
            return true;
        } catch (TimeoutException ex) {
            printError(ex, "Tab wasnt opened?????");
            return false;
        }
    }

    public boolean isArticleDuplicated() {
        System.out.println("Checking whether the article is duplicated");
        try {
            getWait().until(ExpectedConditions.visibilityOf(submit));
            getMiniWait().until(ExpectedConditions.visibilityOf(lblDuplicated));
            System.out.println("Affirmative");
            return true;
        } catch (TimeoutException ex) {
            System.out.println("Negative");
            System.out.println("Article isn't duplicated - Label isn't shown");
            return false;
        }
    }

    public boolean isError() {
        System.out.println("Checking whether 502 strange error is shown");
        try {
            waitForJs();
            getMiniWait()
                .until(ExpectedConditions.visibilityOf(lblError));
            System.out.println("Affirmative");
            return true;
        } catch (TimeoutException ex) {
            System.out.println("Negative");
            return false;
        } catch (NoSuchElementException e) {
            printError(e, "Label is absent. No error!");
            return false;
        }
    }

    public boolean isServerError() {
        System.out.println("Checking whether 504 server error is thrown");
        try {
            waitForJs();
            getMiniWait()
                .until(ExpectedConditions.visibilityOf(lblServerError));
            System.out.println("Affirmative");
            return true;
        } catch (TimeoutException ex) {
            System.out.println("Negative");
            return false;
        } catch (NoSuchElementException e) {
            printError(e, "Label is absent. No error!");
            return false;
        }
    }

    public void sumbit() {
        removeDisabledAttribute(true);
        System.out.println("Submiting");
        waitSleepClick(submit);
        System.out.println("submitted");
        waitElementDisappears("#submit");
        System.out.println("Disappeared");
    }

    private void waitElementDisappears(final String id) {
        try {
            System.out.println("Waiting for invisibility of element");
            getWait().until(driver -> getDriver().findElements(By.id(id)).size()==0);
        } catch (TimeoutException ex) {
            System.out.println("Negative");
        } catch (NoSuchElementException e) {
            printError(e, "Element is not displayed!");
        }
//        } catch (TimeoutException e) {
//            printError(e, "Wait for disappearing of element failed: TimeoutException");
//            e.printStackTrace();
//        } catch (NoSuchElementException e) {
//            printError(e, "No such element is displayed on screen. Success!");
//        } catch (Exception e) {
//            printError(e, "Error.");
//        }
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
                sleep(1);
                removeDisabledAttribute(false);
            } else {
                printError(e, "js disabled attr wasn't removed after 2 turns");
            }
        }
    }
}
