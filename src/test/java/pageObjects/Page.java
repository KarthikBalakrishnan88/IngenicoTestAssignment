package pageObjects;

import listener.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Page {
    private WebDriver driver = Browser.getDriver();
    public OverviewPage overviewPage = PageFactory.initElements(driver,OverviewPage.class);
    public IdealPage iDealPage = PageFactory.initElements(driver,IdealPage.class);
    public IdealConfirmPage iDealConfirmPage = PageFactory.initElements(driver,IdealConfirmPage.class);
}
