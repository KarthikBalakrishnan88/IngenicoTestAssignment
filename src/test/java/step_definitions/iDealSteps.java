package step_definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import listener.Browser;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.Page;

public class iDealSteps {
    private WebDriver driver = Browser.getDriver();
    private Page page = new Page();

    @Given("^I navigate to merchant payment page$")
    public void iNavigateToMerchantPaymentPage() {
        driver.navigate().to(hostedCheckOutAPISteps.redirectURL);
        Hooks.attachScreenShot();
    }

    @And("^I select the idealPayment Method$")
    public void iSelectTheIdealPaymentMethod() {
        page.overviewPage.clickOnIdealButton();
        Hooks.attachScreenShot();
    }

    @When("^I select the bank as \"([^\"]*)\"$")
    public void iSelectTheBankAs(String bankName) {
        page.iDealPage.selectIdealPayment(bankName);
        Hooks.attachScreenShot();
    }

    @And("^Click on Pay button$")
    public void clickOnPayButton() {
        page.iDealPage.clickPayButton();
        Hooks.attachScreenShot();
    }

    @And("^I confirm my transacion$")
    public void iConfirmMyTransacion() {
        Hooks.attachScreenShot();
        page.iDealConfirmPage.clickConfirmButton();

    }

    @Then("^I should see the confirmation message$")
    public void iShouldSeeTheConfirmationMessage() {
        Assert.assertTrue(page.overviewPage.paymentSuccessful.isDisplayed());
        Hooks.attachScreenShot();
    }

    @Then("^I should see error message for PaymentFailure$")
    public void iShouldSeeErrorMessageForPaymentFailure() {
        Hooks.attachScreenShot();
        Assert.assertTrue(page.overviewPage.paymentFailed.isDisplayed());
    }

    @And("^I should be able to retry my payment with \"([^\"]*)\"$")
    public void iShouldBeAbleToRetryMyPaymentWith(String bankName) {
        page.overviewPage.clickOnTryAgain();
        page.iDealPage.selectIdealPayment(bankName);
        page.iDealPage.clickPayButton();
        Hooks.attachScreenShot();
    }

    @And("^I should be able to choose another method for payment$")
    public void iShouldBeAbleToChooseAnotherMethodForPayment()  {
        Assert.assertTrue(page.overviewPage.tryOtherMethod.isDisplayed());
        page.overviewPage.clickOnTryOtherMethod();
        Hooks.attachScreenShot();
        Assert.assertTrue(page.overviewPage.iDealButton.isDisplayed());
    }
}
