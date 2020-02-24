package com.automation.tests.day9;
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


public class PreemptiveAuthentication {
    @BeforeAll
    public static void setup() {
        //https will not work, because of SSL certificate issues
        //this website doesn't have it
        baseURI = "http://practice.cybertekschool.com";
    }

    @Test
    @DisplayName("Normal basic authentication")
    public void test1(){
        //actually, it will make 2 calls:
        //1st: with no credentials, then will get 401,
        // to ensure that only requested server will get credentials
        //and then 2nd call will be with credentials
        given().
                auth().basic("admin", "admin").
                when().
                get("/basic_auth").prettyPeek().
                then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Preemptive authentication")
    public void test2(){
        //it sends credentials with 1st request
        //the benefit is that you load network less that with 2 calls
        given().
                auth().preemptive().basic("admin", "admin").
                when().
                get("/basic_auth").prettyPeek().
                then().assertThat().statusCode(200);
    }

}
