package page;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class CheckDetailsPage extends Page {

    @FindBy(css = "#submit[value='Опубликовать']")
    private WebElement submit;

    @FindBy(xpath = "//h3[contains(text(), 'Дублирование объявления!')]")
    private WebElement lblDuplicated;

    @FindBy(xpath = "//body[contains(text(), 'Error: 502:004')]")
    private WebElement lblError;

    public CheckDetailsPage() {
        super();
    }

    public boolean isPageLoaded() {
        try {
            waitForJs();
            wait.until(ExpectedConditions.visibilityOf(submit));
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public boolean isArticleDuplicated() {
        if (isPageLoaded()) {
            try {
                waitForJs();
                wait.until(ExpectedConditions.visibilityOf(lblDuplicated));
                wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(submit)));
                return true;
            } catch (TimeoutException ex) {
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
        js.executeScript("document.getElementById('submit').disabled = false;");
    }
}
