package step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.RequestHeader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import support.DefaultAuthenticator;
import support.HeaderDateString;
import support.TestProperties;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static models.AuthorizationType.V1HMAC;

public class hostedCheckOutAPISteps {
    public static String redirectURL;
    private RequestSpecification request;
    private Response response;
    private JsonObject order = new JsonObject();
    private JsonObject hostedCheckoutSpecificInput = new JsonObject();
    private JsonObject jsonBody = new JsonObject();
    private String host = TestProperties.loadProperty().getProperty("application.host");
    private String path = "/v1/" + TestProperties.loadProperty().getProperty("application.merchantID")+"/hostedcheckouts";

    @Given("^I generate the authorisation headers$")
    public void iGenerateTheAuthorisationHeaders(DataTable headersValues) throws URISyntaxException {
        List<Map<String,String>> data = headersValues.asMaps(String.class,String.class);
        DefaultAuthenticator auth1 = new DefaultAuthenticator(V1HMAC, TestProperties.loadProperty().getProperty("application.apiKey"), TestProperties.loadProperty().getProperty("application.sharedKey"));
        List<RequestHeader> customHeaders = new ArrayList<RequestHeader>();
        path = data.get(0).get("path");
        String curentTime = HeaderDateString.getHeaderDateString();
        customHeaders.add(new RequestHeader("content-type", data.get(0).get("contentType")));
        customHeaders.add(new RequestHeader("X-GCS-MessageId", data.get(0).get("messageId")));
        customHeaders.add(new RequestHeader("X-GCS-RequestId", data.get(0).get("requestID")));
        customHeaders.add(new RequestHeader("Date", curentTime));
        String authorization = auth1.createSimpleAuthenticationSignature("POST", new URI(path), customHeaders);
        request = given()
                .contentType(ContentType.JSON)
                .headers("Content-Type", data.get(0).get("contentType"), "Date", curentTime, "x-gcs-messageid", data.get(0).get("messageId"), "x-gcs-requestid", data.get(0).get("requestID"), "Authorization", authorization);
    }

    @And("^I set the order details as$")
    public void iSendTheOrderDetailsAs(DataTable orderDetails) {
        List<Map<String,String>> data = orderDetails.asMaps(String.class,String.class);

        JsonObject amountOfMoney = new JsonObject();
        amountOfMoney.addProperty("currencyCode", data.get(0).get("currencyCode"));
        amountOfMoney.addProperty("amount", data.get(0).get("amount"));

        JsonObject billingAddress = new JsonObject();
        billingAddress.addProperty("countryCode", data.get(0).get("countryCode"));

        JsonObject customer = new JsonObject();
        customer.addProperty("merchantCustomerId", data.get(0).get("merchantCustomerId"));
        customer.add("billingAddress", billingAddress);

        order.add("amountOfMoney", amountOfMoney);
        order.add("customer", customer);
    }

    @And("^I set the hostedCheckoutSpecificInput as$")
    public void iSendTheHostedCheckoutSpecificInputAs(DataTable inputs) {
        List<Map<String,String>> data = inputs.asMaps(String.class,String.class);
        hostedCheckoutSpecificInput.addProperty("variant", data.get(0).get("variant"));
        hostedCheckoutSpecificInput.addProperty("locale", data.get(0).get("locale"));
    }

    @When("^I post the  request to hostedCheckOutAPI$")
    public void iPostTheRequestToHostedCheckOutAPI() throws Throwable {
        jsonBody.add("order", order);
        jsonBody.add("hostedCheckoutSpecificInput", hostedCheckoutSpecificInput);
        request.body(jsonBody.toString());
        Hooks.attachMessage(jsonBody.toString());
        response = request.post(host + path);
    }

    @Then("^I should be have the valid redirect URL$")
    public void iShouldBeHaveTheValidRedirectURL() throws ParseException {
        JSONParser parser = new JSONParser();
        Hooks.attachMessage(response.asString());
        Assert.assertTrue(response.getStatusCode()<= 201);
        JSONObject jsonResponse = (JSONObject) parser.parse(response.asString());
        redirectURL = "https://payment."+jsonResponse.get("partialRedirectUrl").toString();

    }

    @Then("^I should get the response code as \"([^\"]*)\"$")
    public void iShouldGetTheResponseCodeAs(String responseCode) {
        Assert.assertTrue(response.statusCode()== Integer.parseInt(responseCode));
    }
}
