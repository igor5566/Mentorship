package github.core.api.requests;

import github.core.utils.methods.GetRequests;
import io.restassured.response.Response;


public class UserRequest {

    GetRequests requests = new GetRequests();

    public Response userLoginVerifyRequest(String token, int responseCode, String resources) {
        return requests.withToken(token, responseCode, resources);
    }
}
