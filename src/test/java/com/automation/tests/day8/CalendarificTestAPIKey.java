package com.automation.tests.day8;
import com.automation.pojos.Spartan;

import com.github.javafaker.Faker;

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

public class CalendarificTestAPIKey {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("calendarific.uri");
    }
    /*
    API Key is a secret that the API generates and gives to the developer
    API Key looks like long String: 63fe4bfe1f468f6d80093dff9617a59d6736bfda
    API Key can go as query parameter or inside a header, it depends on web service how you must pass API key
    How it gets created? you go to website, register and service gives you API Key
    Then you have to pass API key alongside with every request
    API Key is easy to implement for developer and client
    But not-techinal people have no idea about this
    So its mostly used by developers only
    */

    /**
     * Given accept content type as JSON
     * When user sends GET request to "/countries"
     * Then user verifies that status code is 401
     * And user verifies that status line contains "Unauthorized" message
     * And user verifies that meta.error_detail contains "Missing or invalid api credentials." message
     */
    @Test
    @DisplayName("Verify that user cannot access web service without valid API key")
    public void test1(){
        given().
                accept(ContentType.JSON).
                when().
                get("/countries").prettyPeek().
                then().assertThat().
                statusCode(401).
                statusLine(containsString("Unauthorized")).
                body("meta.error_detail", containsString("Missing or invalid api credentials."));
    }
    /**
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * When user sends GET request to "/countries"
     * Then user verifies that status code is 200
     * And user verifies that status line contains "OK" message
     * And user verifies that countries array is not empty
     */

    //https://calendarific.com/api/v2/countries?api_key=63fe4bfe1f468f6d80093dff9617a59d6736bfda

    @Test

    public void test2(){
        given().
                accept(ContentType.JSON).
                queryParam("api.key", "63fe4bfe1f468f6d80093dff9617a59d6736bfda").
         when().
                 get("/countries").prettyPeek().
           then().assertThat().statusCode(200).
                                statusLine(containsString("OK"));
    }

    /**
     *
     * Given accept content type as JSON
     * And query parameter api_key with valid API key
     * And query parameter country is equals to US
     * And query parameter type is equals to national
     * And query parameter year is equals to 2019
     * When user sends GET request to "/holidays"
     * Then user verifies that number of national holidays in US is equals to 11
     */
 //   https://calendarific.com/api/v2/holidays?api_key=63fe4bfe1f468f6d80093dff9617a59d6736bfda&country=US&type=national&year=2019
































}
