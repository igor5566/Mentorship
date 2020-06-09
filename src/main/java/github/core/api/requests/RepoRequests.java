package github.core.api.requests;

import github.core.utils.methods.PostRequests;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Map;

public class RepoRequests {

    PostRequests requests = new PostRequests();

    public Response createRepoVerifyRequest(String token, int responseCode, String resources, Map<String, Object> map) {
        return requests.withTokenAndBody(token, responseCode, resources, map);
    }
}
