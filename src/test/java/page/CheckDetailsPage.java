package page;

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
            wait.until(ExpectedConditions.visibilityOf(lblCurrentTab));
            return lblCurrentTab.getText().equals("Проверка введённых данных");
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public boolean isArticleDuplicated() {
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
        waitForJs();
//        js.executeScript("document.getElementById('submit').disabled = false;");
        js.executeScript("arguments[0].removeAttribute('disabled')", submit);
    }
}
