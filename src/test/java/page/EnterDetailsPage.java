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
        driver.get(URL);
        return this;
    }

    public EnterDetailsPage setAgency() {
        waitSleepClick(rbAgency);
        return this;
    }

    public EnterDetailsPage selectCategory(String category) {
        selectByText(selCategory, category);
        return this;
    }

    public EnterDetailsPage enterTitle(String title) {
        txtTitle.clear();
        txtTitle.sendKeys(title);
        return this;
    }

    public EnterDetailsPage selectCity(String place) {
        selectByText(selPlace, place);
        return this;
    }

    public EnterDetailsPage enterCompanyName(String company) {
        txtCompany.clear();
        txtCompany.sendKeys(company);
        return this;
    }

    public EnterDetailsPage enterEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
        return this;
    }

    public EnterDetailsPage enterArticleBody(String details) {
        driver.switchTo().defaultContent(); // you are now outside both frames
        driver.switchTo().frame("imp_redactor_frame_description");
        setValueJs(details);
        driver.switchTo().defaultContent(); // you are now outside both frames
        return this;
    }

    public void submit() {
        waitSleepClick(btnSubmit);
    }

}
