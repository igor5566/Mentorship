package com.github.tests.apitests;

import github.core.api.requests.RepoRequests;
import github.core.api.requests.UserRequest;
import github.core.tools.JsonTools;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static github.core.api.EndPoints.USERS_REPOS;
import static github.core.api.EndPoints.USER_INFO;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class APITest {

    private String date;
    private String uniqueID;
    private String login = "ivolkoff5566";
    private String token = "Basic aXZvbGtvZmY1NTY2OjIwMjBTT0ZUc2VydmUvb25l";
    private UserRequest userRequest;
    private RepoRequests repoRequests;
    private JsonTools jsonTools;

    @BeforeClass
    public void settingUp() {
        Date d = new Date();
        long dateLong = d.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(dateLong);
        uniqueID = d.getTime() + "";
    }

    @Test(description = "Verify user's login is as expected.")
    public void userLoginVerify() {
        userRequest = new UserRequest();
        Response response = userRequest.userLoginVerifyRequest(token, 200, USER_INFO);
        response.then().body("login", equalTo(login));
    }

    @Test(description = "Creating new repo using API.")
    public void createNewRepoWithAPI() {
        Map<String,Object> repoInfo = new HashMap<>();
        repoInfo.put("name", ("APITest" + uniqueID));
        repoInfo.put("auto_init", true);
        repoInfo.put("private", false);
        repoInfo.put("gitignore_template", "nanoc");

        repoRequests = new RepoRequests();

        Response response = repoRequests.createRepoVerifyRequest(token, 201, USERS_REPOS, repoInfo);
        response.then().body("name", equalTo("APITest" + uniqueID));
    }
}
