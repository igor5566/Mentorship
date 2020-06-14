package com.github.tests.apitests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import github.core.api.pojoFiles.UserInfo;
import github.core.api.pojoFiles.UserInfo.*;
import github.core.api.requests.RepoRequests;
import github.core.api.requests.UserRequest;
import github.core.tools.Conditions;
import github.core.tools.JsonTools;
import io.restassured.response.Response;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.core.Condition;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static github.core.api.EndPoints.USERS_REPOS;
import static github.core.api.EndPoints.USER_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class APITest {

    private String date;
    private String uniqueID;
    private String login = "ivolkoff5566";
    private String token = "Basic aXZvbGtvZmY1NTY2OjIwMjBTT0ZUc2VydmUvb25l";
    private String pathToCreateRepoFile = "src/main/resources/JSONFiles/RepoInfo.json";
    private UserRequest userRequest;
    private RepoRequests repoRequests;
    private JsonTools jsonTools = new JsonTools();
    private Gson gson = new Gson();
    private String pathToTestJSON = "src/main/resources/JSONFiles/test.json";

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
        String key = "login";

        Conditions<Response> cond = ((resp, k) -> {
//            UserInfo userInfo = gson.fromJson(resp.asString(), UserInfo.class);
//            log.info("Value is " + userInfo.login + " and key is " + k);
            JSONObject jsonObject = new JSONObject(resp.asString());
            String s = (String) jsonObject.opt(k);
            if (s.equals(login)) {
                return true;
            } else {
                return false;
            }
        });

        Response response = userRequest.userLoginVerifyRequest(token, 200, USER_INFO, cond, key, login);
        UserInfo userInfo = gson.fromJson(response.asString(), UserInfo.class);

        assertThat(userInfo.login.equals(login)).as("The user login is not as expected.").isTrue();
        assertThat(userInfo.plan.name.equals("free")).as("The user plan is not as expected.").isTrue();
    }

    @Test(description = "Creating new repo using API.")
    public void createNewRepoWithAPI() {
        Map<Object,Object> repoInfo = jsonTools.getMapFromJSONFile(pathToCreateRepoFile, gson);
        repoInfo.replace("name", "blog", ("APITest" + uniqueID));

        repoRequests = new RepoRequests();

        Response response = repoRequests.createRepoVerifyRequest(token, 201, USERS_REPOS, repoInfo);
        response.then().body("name", equalTo("APITest" + uniqueID));
    }

    @Test(description = "Verify user's login is as expected.")
    public void userLogin() {
        userRequest = new UserRequest();
        String key = "login1";
        String key2 = "private";
        String key3 = "id1";
        String key4 = "login3";
        String key5 = "name2";
        String key6 = "wrong key";

        JSONObject jsonObject = jsonTools.getJSONObjectFromFile(pathToTestJSON);

        log.info("Value " + key + " is - " + jsonTools.getJSNONValueByKey(jsonObject, key));
        log.info("Value " + key2 + " is - " + jsonTools.getJSNONValueByKey(jsonObject, key2));
        log.info("Value " + key3 + " is - " + jsonTools.getJSNONValueByKey(jsonObject, key3));
        log.info("Value " + key4 + " is - " + jsonTools.getJSNONValueByKey(jsonObject, key4));
        log.info("Value " + key5 + " is - " + jsonTools.getJSNONValueByKey(jsonObject, key5));
        log.info("Value " + key6 + " is - " + jsonTools.getJSNONValueByKey(jsonObject, key6));
    }
}
