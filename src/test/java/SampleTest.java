import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.RequestHeader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import support.DefaultAuthenticator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;
import static models.AuthorizationType.V1HMAC;

public class SampleTest {
    @Test
    public void sampleTest1() throws URISyntaxException, ParseException, IOException {
        DefaultAuthenticator auth1 = new DefaultAuthenticator(V1HMAC, "14056800067895c0", "Cxcr99CJBMRkEK/vYTvbpjCZ0h6qUaEPXRJ1yjFnlhM=");
        List<RequestHeader> customHeaders = new ArrayList<RequestHeader>();
        String host = "https://eu.sandbox.api-ingenico.com";
        String path = "/v1/2709/hostedcheckouts";
        String curentTime = getHeaderDateString();
        //String curentTime = "Fri, 28 Sep 2018 18:12:09 GMT";
        String contentType = "application/json; charset=UTF-8";
        String messageId = "6480071e-039d-4dca-a966-4ce3c1bc201b";
        String requestID = "1cc6daff-a305-4d7b-94b0-c580fd5ba6b4";
        String jsonString = "{\"order\": { \"amountOfMoney\": {\"currencyCode\": \"EUR\",\t\"amount\": 100 }, \"customer\": { \"merchantCustomerId\": 103, \"billingAddress\": { \"countryCode\": \"NL\" }\t} }, \"hostedCheckoutSpecificInput\": { \"variant\": \"testVariant\", \"locale\": \"en_GB\" } }";
        customHeaders.add(new RequestHeader("content-type", contentType));
        customHeaders.add(new RequestHeader("X-GCS-MessageId", messageId));
        customHeaders.add(new RequestHeader("X-GCS-RequestId", requestID));
        customHeaders.add(new RequestHeader("Date", curentTime));
        String authorization = auth1.createSimpleAuthenticationSignature("POST", new URI(path), customHeaders);
        JsonObject amountOfMoney = new JsonObject();
        amountOfMoney.addProperty("currencyCode", "EUR");
        amountOfMoney.addProperty("amount", 100);

        JsonObject billingAddress = new JsonObject();
        billingAddress.addProperty("countryCode", "NL");

        JsonObject customer = new JsonObject();
        customer.addProperty("merchantCustomerId", 103);
        customer.add("billingAddress", billingAddress);

        JsonObject order = new JsonObject();
        order.add("amountOfMoney", amountOfMoney);
        order.add("customer", customer);

        JsonObject hostedCheckoutSpecificInput = new JsonObject();
        hostedCheckoutSpecificInput.addProperty("variant", "testVariant");
        hostedCheckoutSpecificInput.addProperty("locale", "en_GB");

        JsonObject jsonBody = new JsonObject();
        jsonBody.add("order", order);
        jsonBody.add("hostedCheckoutSpecificInput", hostedCheckoutSpecificInput);

        Response response = given()
                .contentType(ContentType.JSON)
                .headers("Content-Type", contentType, "Date", curentTime, "x-gcs-messageid", messageId, "x-gcs-requestid", requestID, "Authorization", authorization)
                .body(jsonBody.toString())
                .post(host + path);
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response.asString());
    }


    public static String getHeaderDateString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

}
