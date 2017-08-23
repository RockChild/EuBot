package scenarios;

import model.Article;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import page.CheckDetailsPage;
import page.ConfirmationPage;
import page.EnterDetailsPage;
import util.DataParser;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertTrue;

public class RunBot extends Base {

private EnterDetailsPage enterDetailsPage;
private CheckDetailsPage checkDetailsPage;
private ConfirmationPage confirmationPage;

    @BeforeClass
    public void beforeTest() {
        enterDetailsPage = new EnterDetailsPage();
        checkDetailsPage = new CheckDetailsPage();
        confirmationPage = new ConfirmationPage();
    }


    @Test
    public void createArticle() {
        for (Article article : DataParser.parse())
            try {
                justDoIt(article);
            } catch (Exception e) {
                System.out.println("Some error. Just call the Police!");
                e.printStackTrace();
            }
    }

    private void justDoIt(Article article) {
        enterDetailsPage
                .open()
                .enterTitle(article.getTitle())
                .setAgency()
                .selectCategory(article.getCategory())
                .selectCity(article.getPlace())
                .enterArticleBody(article.getArticleBody())
                .enterCompanyName(article.getCompany())
                .enterEmail(article.getEmail())
                .submit();

        //Alert in case the article text is too short
        handleAlertIfAppears(article.getArticleBody());

        if  (checkDetailsPage.isError()) {
            System.out.println("Error: 502:004. Rerun.");
            justDoIt(article);
        }
        //todo: need refactor
        checkDetailsPage.removeDisableAttribute();
        if (checkDetailsPage.isArticleDuplicated()) {
            System.out.println("Warning: Duplicated!");
            checkDetailsPage.removeDisableAttribute();
            checkDetailsPage.sumbit();
        } else {
            checkDetailsPage.sumbit();
            System.out.println("Success: Published!");
        }
        assertTrue("Confirmation page wasn't loaded", confirmationPage.isPageLoaded());
        System.out.println("Finish");
    }

    private void handleAlertIfAppears(String articleBody) {
        try {
            Alert alert = wait.withTimeout(2, TimeUnit.SECONDS)
                    .until(ExpectedConditions.alertIsPresent());
            alert.accept();
            enterDetailsPage
                    .enterArticleBody("<br/>" + articleBody)
                    .submit();
            handleAlertIfAppears(articleBody);
        } catch (TimeoutException e) {
            //do nothing, it's ok
        }
    }
}