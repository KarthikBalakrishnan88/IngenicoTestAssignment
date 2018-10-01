package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class IdealConfirmPage {
    private final WebDriver driver;

    public IdealConfirmPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(how = How.CSS, using = "input[value=\"Confirm Transaction\"]")
    public WebElement confirmButton;

    public void clickConfirmButton(){
        confirmButton.click();
    }


}
