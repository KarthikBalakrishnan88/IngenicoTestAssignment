package step_definitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import listener.Browser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    public static Scenario testcase;

    @Before
    public void before(Scenario scenario)
    {
        this.testcase = scenario;
        Browser.initBrowser();
    }

    @After
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            scenario.embed(((TakesScreenshot)Browser.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
        }
        Browser.closeBrowser();
    }

    public static void attachScreenShot(){
        testcase.embed(((TakesScreenshot)Browser.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
    }

    public static void attachMessage(String message){
        testcase.write(message);
    }
}
