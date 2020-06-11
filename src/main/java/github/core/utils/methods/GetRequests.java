package github.core.utils.methods;

import github.core.api.EndPoints;
import github.core.tools.ApiWaiter;
import github.core.tools.Conditions;
import github.core.tools.ResponseWaiter;
import github.core.tools.ValidateResponseWaiter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;

@Slf4j
public class GetRequests {

    private ApiWaiter apiWaiter = new ApiWaiter();

    public Response withToken(String token, int responseCode, String resources) {
        ValidateResponseWaiter responseWaiter = () -> {
            RestAssured.baseURI = EndPoints.BASE_URI;
            return given()
                    .header("Authorization", token)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(resources)
                    .then();
        };
        ValidatableResponse validatableResponse = apiWaiter.waitForResponse(responseWaiter, responseCode);
        Response resp = validatableResponse.contentType(ContentType.JSON).extract().response();
        log.info("Response returned - {}", resp.asString());
        assertThat(resp.getStatusCode()).as("Response error occurred - {}", resp.getStatusLine()).isEqualTo(responseCode);
        log.info("{} return successfully ", resp.getStatusCode());

        return resp;
    }

    public Response withTokenAndExpectedCond(String token, int responseCode, String resources, Conditions<Response> conditions, String jsonKey, String expectedJsonValue) {
        ResponseWaiter responseWaiter = () -> {
            RestAssured.baseURI = EndPoints.BASE_URI;
            return given()
                    .header("Authorization", token)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(resources)
                    .then()
                    .extract()
                    .response();
        };
        Response resp = apiWaiter.waitForResponse(responseWaiter, conditions, jsonKey, expectedJsonValue);
        log.info("Response returned - {}", resp.asString());
        assertThat(resp.getStatusCode()).as("Response error occurred - {}", resp.getStatusLine()).isEqualTo(responseCode);
        log.info("{} return successfully ", resp.getStatusCode());

        return resp;
    }
}
