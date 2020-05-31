package com.github.tests.APItests;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APITest {

    private String date;
    private String uniqueID;
    private String token = "Bearer 605fe9db8ae0aba46c88e9414bd17a7f9a76fab8";
    private String email = "volkoff5566@gmail.com";
    private final String baseURI = "https://api.github.com";
    private RequestSpecification requestSpec;


    @BeforeClass
    public void settingUp() {
        Date d = new Date();
        long dateLong = d.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(dateLong);
        uniqueID = d.getTime() + "";

        requestSpec = given()
                .baseUri(baseURI)
                .header("Authorization", token);
    }

    @Test(description = "Verify user's login is as expected.")
    public void userLoginVerify() {
        requestSpec.when()
                .get("/user")
                .then()
                .statusCode(200)
                .body("login", equalTo("ivolkoff5566"));
    }

    @Test(description = "Creating new repo using API.")
    public void createNewRepoWithAPI() {
        Map<String,Object> repoInfo = new HashMap<>();
        repoInfo.put("name", ("APITest" + uniqueID));
        repoInfo.put("auto_init", true);
        repoInfo.put("private", false);
        repoInfo.put("gitignore_template", "nanoc");

        requestSpec.contentType("application/json")
                .body(repoInfo)
                .post("/user/repos")
                .then()
                .statusCode(201)
                .body("name", equalTo("APITest" + uniqueID));
    }
}
