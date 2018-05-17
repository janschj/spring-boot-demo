package org.baeldung.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.baeldung.MediaAgencyServerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MediaAgencyServerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class OAuth2SwaggerIntegrationTest {

    private static final String URL_PREFIX = "http://localhost:8082/spring-security-oauth-resource";
    private String tokenValue = null;

    //@Before
    public void obtainAccessToken() {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", "oneclient");
        params.put("username", "user@me.com");
        params.put("password", "user");
        final Response response = RestAssured.given()
            .auth()
            .preemptive()
            .basic("oneclient", "onesecret")
            .and()
            .with()
            .params(params)
            .when()
            .post("http://localhost:9090/oauth/token");

        tokenValue = response.jsonPath()
            .getString("access_token");
    }

   @Test
   public void whenVerifySwaggerDocIsWorking_thenOKx() {
       assertEquals("x", "x");
   }
    public void whenVerifySwaggerDocIsWorking_thenOK() {
        Response response = RestAssured.get(URL_PREFIX + "/users/extra");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());

        response = RestAssured.given()
            .header("Authorization", "Bearer " + tokenValue)
            .get(URL_PREFIX + "/users/extra");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

 

}
