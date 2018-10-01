package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "src/test/java/features", plugin = {
        "json: target/cucumber.json" }, glue = "step_definitions", tags = {"@debug"})
public class CukeRunner extends AbstractTestNGCucumberTests {
}
