package github.core.api.requests;

import github.core.tools.Conditions;
import github.core.utils.methods.GetRequests;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRequest {

    GetRequests requests = new GetRequests();

    public Response userLoginVerifyRequest(String token, int responseCode, String resources) {
        return requests.withToken(token, responseCode, resources);
    }

    public Response userLoginVerifyRequest(String token, int responseCode, String resource, Conditions<Response> conditions, String jsonKey, String expectedJsonValue) {
        return requests.withTokenAndExpectedCond(token, responseCode, resource, conditions, jsonKey, expectedJsonValue);
    }
}
