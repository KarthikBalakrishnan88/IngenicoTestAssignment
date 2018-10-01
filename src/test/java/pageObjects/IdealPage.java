package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class IdealPage {
    private final WebDriver driver;

    public IdealPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(how = How.CSS, using = "select[name=\"issuerId\"]")
    public WebElement issuerList;

    @FindBy(how = How.CSS, using = "button[id=\"primaryButton\"]")
    public WebElement payButton;

    @FindBy(how = How.CSS, using = "button[name=\"cancelPayment\"]")
    public WebElement cancelButton;

    public void selectIdealPayment(String value) {
        Select issuers = new Select(issuerList);
        issuers.selectByVisibleText(value);
    }

    public void clickPayButton(){
        payButton.click();
    }

    public void clickCancelButton(){
        cancelButton.click();
    }
}
