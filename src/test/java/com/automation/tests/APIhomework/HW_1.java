package com.automation.tests.APIhomework;
import org.junit.jupiter.api.BeforeAll;

import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class HW_1 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("homework1.uri");
    }

    @Test
    public void test1() {
        given().

                when().

                get().

                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                assertThat().body("name", notNullValue()).
                assertThat().body("surname", notNullValue()).
                assertThat().body("gender", notNullValue()).
                assertThat().body("region", notNullValue()).

                log().all(true);


    }

    @Test
    public void test2() {
        given().

                //when().
                        queryParam("gender", "male").
                get().
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                assertThat().body("gender", containsString("male")).
                log().all(true);

    }

    @Test
    public void test3() {
        given().

                //when().
                        queryParam("gender", "male").
                queryParam("region", "Turkey").
                get().
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                assertThat().body("gender", containsString("male")).
                assertThat().body("region", containsString("Turkey")).
                log().all(true);


    }

    @Test
    public void test4() {
        given().
                when().
                queryParam("gender", "lesbo").
                get().
                then().
                assertThat().
                statusCode(400)
                .statusLine(containsString("Bad Request")).
                assertThat().body("error", containsString("Invalid gender")).
                log().all(true);


    }

    @Test
    public void test5() {
        given().
                when().
                queryParam("region", "Turkiye").
                get().
                then().
                assertThat().

                statusCode(400)
                .statusLine(containsString("Bad Request")).
                assertThat().body("error", containsString("Region or language not found")).
                log().all(true);


    }

    @Test
    public void test6() {

        Response response = given()
                .queryParam("region", "Turkey")
                .queryParam("amount", 5)
                .get();
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json; charset=utf-8", response.getContentType());
        List<String> actual = response.jsonPath().getList("name");
        assert new HashSet(actual).size() == actual.size();


    }

    @Test
    public void test7() {
        given()
                .queryParam("region", "Turkey")
                .queryParam("gender", "male")
                .queryParam("amount", 5)
                .get()
                .then().assertThat()
                .body("region", everyItem(equalTo("Turkey")))
                .body("gender", everyItem(equalTo("male")));


    }

    @Test
    public void test8() {
        given()
                .queryParam("amount", 5)
                .get()
                .then().assertThat()
                .body("", hasSize(5))
                .log().all();
    }
}