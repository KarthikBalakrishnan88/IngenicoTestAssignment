package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class OverviewPage {
    private final WebDriver driver;

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
    }
    @FindBy(how = How.XPATH,using = "(//button[@name=\"paymentProductId\"])[18]")
    public WebElement iDealButton;

    @FindBy(how = How.XPATH,using = "//p[contains(text(),\"Your payment is successful.\")]")
    public WebElement paymentSuccessful;

    @FindBy(how = How.XPATH,using = "//h2[contains(text(),\"Your payment failed\")]")
    public WebElement paymentFailed;

    @FindBy(how = How.CSS,using = "button[id=\"selectotherbutton\"]")
    public WebElement tryOtherMethod;

    @FindBy(how = How.CSS,using = "button[id=\"tryagainbutton\"]")
    public WebElement tryAgain;

    public void clickOnIdealButton(){
        iDealButton.click();
    }

    public void clickOnTryAgain(){tryAgain.click();}

    public void clickOnTryOtherMethod(){tryOtherMethod.click();}

}
