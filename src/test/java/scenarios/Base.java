package scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import util.WDHub;

public class Base {
    WebDriver driver;
    FluentWait<WebDriver> wait;

    @BeforeSuite
    public void beforeSuite() {
        driver = WDHub.build();
        wait = WDHub.getWait();
    }

    @AfterSuite
    public void afterSuite() {
        driver.close();
        driver.quit();
    }
}
