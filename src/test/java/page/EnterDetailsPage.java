package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EnterDetailsPage extends Page {

    private final String URL = "http://eurabota.com/post/";

    @FindBy(id = "type_id_4")
    private WebElement rbAgency;

    @FindBy(id = "category_id")
    private WebElement selCategory;

    @FindBy(id = "title")
    private WebElement txtTitle;

    @FindBy(id = "city_id")
    private WebElement selPlace;

    @FindBy(id = "company")
    private WebElement txtCompany;

    @FindBy(id = "poster_email")
    private WebElement txtEmail;

    @FindBy(id = "submit")
    private WebElement btnSubmit;

    public EnterDetailsPage() {
        super();
    }

    public EnterDetailsPage open() {
        System.out.println("Opening the page");
        driver.get(URL);
        waitForJs();
        System.out.println("Opened.");
        return this;
    }

    public EnterDetailsPage setAgency() {
        System.out.println("Enetering agency");
        waitSleepClick(rbAgency);
        return this;
    }

    public EnterDetailsPage selectCategory(String category) {
        System.out.println("Selecting category");
        selectByText(selCategory, category);
        return this;
    }

    public EnterDetailsPage enterTitle(String title) {
        System.out.println("Entering title");
        return waitSendKeys(txtTitle, title);
    }

    public EnterDetailsPage selectCity(String place) {
        System.out.println("Selecting city");
        selectByText(selPlace, place);
        return this;
    }

    public EnterDetailsPage enterCompanyName(String company) {
        System.out.println("Entering company name");
        return waitSendKeys(txtCompany, company);
    }

    public EnterDetailsPage enterEmail(String email) {
        System.out.println("Entering e-mail");
        return waitSendKeys(txtEmail, email);
    }

    public EnterDetailsPage enterArticleBody(String details) {
        System.out.println("Entering the article details");
        driver.switchTo().defaultContent(); // you are now outside both frames
        driver.switchTo().frame("imp_redactor_frame_description");
        setValueJs(details);
        driver.switchTo().defaultContent(); // you are now outside both frames
        System.out.println("Entered.");
        return this;
    }

    public void submit() {
        System.out.println("Submiting");
        waitSleepClick(btnSubmit);
    }

    private EnterDetailsPage waitSendKeys(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
        return this;
    }

}
