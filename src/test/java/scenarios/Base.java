package scenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import util.WDHub;

public class Base {
    WebDriver driver;
    FluentWait<WebDriver> miniWait;

    @BeforeClass
    public void beforeSuite() {
        driver = WDHub.build();
        miniWait = WDHub.getWait();
    }

    @AfterClass
    public void afterSuite() {
        driver.close();
        driver.quit();
    }
}
