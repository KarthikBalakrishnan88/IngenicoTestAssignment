# Ingenico Test Assignment

Java based test automation framework using official [Selenium 3.0](https://www.seleniumhq.org/), [cucumber-js](https://github.com/cucumber/cucumber-js "view cucumber js documentation") and [Rest Assured](https://github.com/rest-assured/rest-assured/wiki/Usage).

**Table of Contents**

* [Prerequisite](#prerequisite)
* [Usage](#usage)
  * [Options](#options)
    * [Property file](#property-file)
  * [Feature files](#feature-files)
  * [Step definitions](#step-definitions)
  * [Page objects](#page-objects)
  * [Reports](#reports)
* [How to add to pipeline](#How-to-add-to-pipeline)
* [Parallel Execution](#parallel-execution)
* [Troubleshooting](#troubleshooting)
  * [IntelliJ Cucumber Plugin](#intellij-cucumber-plugin)

## Installation

To run this tests make sure the below items are already configured in your system(host)
1. [Java 1.8 or higher](https://www.java.com/en/download/help/version_manual.xml) 
2. Google Chrome.


## Usage

The framework uses [Maven](https://maven.apache.org/guides/) to execute the tests, to run the tests use the below commands
```bash
mvn clean
mvn verify -P parallel
```

### Options

#### Property file

Configuration options can be set using properties from the 'pom.xlm' file. The properties are written into a property file using the maven property plugin and used as configuration options for Selenium tests:

```xml
<properties>
        <application.apiKey>yyyyyyyyyyyyy</application.apiKey>
        <application.sharedKey>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</application.sharedKey>
        <application.host>https://eu.sandbox.api-ingenico.com</application.host>
        <browser.name>local</browser.name>
        <selenium.url>http://localhost:4444/wd/hub</selenium.url>
        <cucumber.feature>src/test/java/features</cucumber.feature>
        <cucumber.tag>@Regression</cucumber.tag>
        <browser.count>2</browser.count>
    </properties>
```
by default the browser name is configured as 'local' and this will automatically start the selemium web driver and executes the tests in chrome(headless)

### Feature files

A feature file is a [Business Readable, Domain Specific Language](http://martinfowler.com/bliki/BusinessReadableDSL.html) file that lets you describe software’s behavior without detailing how that behavior is implemented. Feature files are written using the [Gherkin syntax](https://github.com/cucumber/cucumber/wiki/Gherkin) and must live in a folder named **features** within the root of your project.

```gherkin
# src/test/features/demoTest.feature

Feature: Ideal Payment
  As a user
  In order to make payment with iDeal
  I should be able to use the redirect URL from the merchant
  @Regression
    Scenario: I should be able to make a successful iDeal payment
      Given I navigate to merchant payment page
```

The browser automatically closes after each scenarios to ensure the next scenario uses a fresh browser environment.

### Step definitions

Step definitions act as the glue between features files and the actual system under test.

_To avoid confusion **always** return a JavaScript promise your step definition in order to let cucumber know when your task has completed._

```java
// src/test/step_definitions/iDealSteps.java
public class iDealSteps {
    private WebDriver driver = Browser.getDriver();
    private Page page = new Page();

    @Given("^I navigate to merchant payment page$")
    public void iNavigateToMerchantPaymentPage() {
        driver.navigate().to(hostedCheckOutAPISteps.redirectURL);
        Hooks.attachScreenShot();
    }
}
```

| Variable    | Description |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| `client`    | an instance of nightwatchJS (_the browser_)
| `page`      | collection of **page** objects 
| `assert`    | instance of junit assertion

### Page objects

Page objects are accessible via a global ```page``` object.

To learn more about page objects visit nighwatchJS official API documentation for [Page Objects](https://www.seleniumhq.org/docs/06_test_design_considerations.jsp#page-object-design-pattern)

### Reports

HTML and JSON reports are automatically generated and stored in the default `./target` folder. This location can be changed by providing a new path in pom.xml

##Parallel Execution
The Framework executes all the tests in parallel using maven-surefire plugin. The number instances can be changes in the pom.xml

```
<browser.count>2</browser.count>
```  
The parallelization is done on feature level, so it is advised to use the count more than 1 when there are multiple feature files.

## How to add to pipeline
The tests can be executed part of jenkins exisitng pipeline by adding the maven plugin and the options can be configured using the properties from pom.xml.

To publish the results, you can use the default jnuit report or publish the '.\target\HTMLReport'.
 
## Troubleshooting

### IntelliJ Cucumber Plugin

IntelliJ based IDE's have a plugin that allows the tester to control click on a `Given`, `When`, `Then` statement within a Cucumber feature file and have the user taken to the associated step definition. This plugin relies on your project having the following folder structure:

```bash
.
└── features
   │   google-search.feature
   └── step_definitions
   │   └── google-search-steps.js
   └── page_objects
       └── google-search.js
   
```
The framework contains a simple cucumber Runner, which can be used for the debug purposes.
