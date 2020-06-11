package github.core.api.requests;

import github.core.tools.Conditions;
import github.core.utils.methods.PostRequests;
import io.restassured.response.Response;
import org.awaitility.core.Condition;
import org.json.JSONObject;

import java.util.Map;

public class RepoRequests {

    private PostRequests requests = new PostRequests();

    public Response createRepoVerifyRequest(String token, int responseCode, String resources, Map<Object,Object> map) {
        return requests.withTokenAndBody(token, responseCode, resources, map);
    }
}
