package github.core.utils.methods;

import github.core.api.EndPoints;
import github.core.tools.ApiWaiter;
import github.core.tools.ValidateResponseWaiter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PostRequests {

    private ApiWaiter apiWaiter = new ApiWaiter();

    public Response withTokenAndBody(String token, int responseCode, String resources, Map<?, ?> map) {
        ValidateResponseWaiter responseWaiter = () -> {
            RestAssured.baseURI = EndPoints.BASE_URI;
            return given()
                    .header("Authorization", token)
                    .body(map)
                    .contentType(ContentType.JSON)
                    .when()
                    .post(resources)
                    .then();
        };
        ValidatableResponse validatableResponse = apiWaiter.waitForResponse(responseWaiter, responseCode);
        Response resp = validatableResponse.contentType(ContentType.JSON).extract().response();
        log.info("Response returned - {}", resp.asString());
        assertThat(resp.getStatusCode()).as("Response error occurred - {}", resp.getStatusLine()).isEqualTo(responseCode);
        log.info("{} return successfully ", resp.getStatusCode());

        return resp;
    }

}
