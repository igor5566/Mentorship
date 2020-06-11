package github.core.tools;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ApiWaiter {
    public ValidatableResponse waitForResponse(ValidateResponseWaiter validateResponseWaiter, int responseCode) {
        final ValidatableResponse[] validatableResponse = new ValidatableResponse[1];
        try {
            Awaitility.await().atMost(100, TimeUnit.SECONDS)
                    .with()
                    .until(() -> {
                        validatableResponse[0] = validateResponseWaiter.validatableResponse();
                        return validatableResponse[0].extract().statusCode() == responseCode;
                    });
            return validatableResponse[0];
        } catch (ConditionTimeoutException e) {
            ValidatableResponse assertResponse = validateResponseWaiter.validatableResponse();
            throw new AssertionError("[ERROR::] Timeout exception with status code :  "
                    .concat(String.valueOf(assertResponse.extract().statusCode()))
                    .concat(" but expected ")
                    .concat(String.valueOf(responseCode))
                    .concat("\n[ERROR::] With error response : \n")
                    .concat(assertResponse.extract().response().asString()));
        }
    }

    public Response waitForResponse(ResponseWaiter responseWaiter, Conditions<Response> condition, String jsonKey, String expectedJsonValue) {
        final Response[] response = new Response[1];
        try {
            Awaitility.await().atMost(100, TimeUnit.SECONDS)
                    .with()
                    .until(() -> {
                        response[0] = responseWaiter.responseWaiter();
                        boolean statement = false;
                        try {
                            log.info("Waiter got response code " + response[0].getStatusCode() + "\r\nTrying to find \""
                                    + expectedJsonValue + "\" by key \"" + jsonKey + "\" into response \r\n"
                                    + response[0].asString());
                            statement = condition.condition(response[0], jsonKey);
                        } catch (Exception e) {
                            log.error("!!!!Waiter did not found \"" + expectedJsonValue + "\"");
                        }
                        return statement;
                    });
            return response[0];
        } catch (ConditionTimeoutException e) {
            Response assertResponse = responseWaiter.responseWaiter();
            throw new AssertionError("[ERROR::] Timeout exception with condition :  "
                    //.concat("\"" + (String) new UtilHelper().setJsonObject(assertResponse.asString()).getJsonValueByKey(jsonKey) + "\"")
                    .concat(" but expected ")
                    .concat("\"" + expectedJsonValue + "\"")
                    .concat("\nWith response : \n")
                    .concat(assertResponse.asString()));
        }
    }
}
