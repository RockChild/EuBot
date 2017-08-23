package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConfirmationPage extends Page {

    @FindBy(className = "posted-ok")
    private WebElement lblPostedOk;

    public ConfirmationPage() {
        super();
    }

    public boolean isPageLoaded() {
        try {
            waitForJs();
            wait.until(ExpectedConditions.visibilityOf(lblPostedOk));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
