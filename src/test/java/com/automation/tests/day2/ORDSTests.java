package com.automation.tests.day2;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;



import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ORDSTests {

    //adress of ORDS web service, that is running no AWS ec2.
    //data is coming back from SQL Oracle database to this web service
    //during-back-end testing with sql developer and jdbc api
    //we were accesing database directly
    //now, we will access web service
    //according to OOP conventions, all instance variable should be private
    //but if we will make it public, it will not make any difference for us
    //it's just good practice, so later we will not hesitate which keyword to use when it is going to be important


    private String baseURI = "http://ec2-3-87-232-74.compute-1.amazonaws.com:1000/ords/hr";
    //we start from given()
    //then we can specify type of request like: get(), put(), delete(), post()
    //and as parameter, we enter resource location(URI)
    //then we are getting response back. that response we can put into Response object
    //from response object, we can retrieve: body, header, status code
    //it works without RestAssured.given() because of static import
    //verify that status code is 200
    //verify that status code is 200

    @Test
    public void test1(){
        Response response = given().get(baseURI +"/employees");

        //System.out.println(response.getBody().asString());

        assertEquals(200, response.getStatusCode());

        System.out.println(response.prettyPrint());
    }

    //get employee with id 100 and verify that response returns status code 200
    @Test
    public void test2(){
        //header stands for meta data
        //usually it's used to iclude cookies
        //in this example, we are specifying whta kind of response type we need
        //because we service can return json or xml
        //when we put header info "Accept", "application/json", we we are sayint that we only need a json response
        Response response = given().header("Accept", "application/json").get(baseURI +"/employees/100");
        int actualStatusCode= response.getStatusCode();
        System.out.println(response.prettyPrint());
       assertEquals(200, actualStatusCode);

       //get infromation about response content type, you can retrieve from response object
        System.out.println("What kind of content server sendy to you in this response: "+response.getHeader("Content-type"));

        }

        //#Task: perform GET request to /regions, print body and all headers.
    @Test
    public void test3(){
        Response response = given().get(baseURI +"/regions");
        assertEquals(200, response.getStatusCode());
        //to get specific header
        Header header = response.getHeaders().get("Content-type");
        //print all headers one by one
        for(Header h: response.getHeaders()){
            System.out.println(h);
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(response.prettyPrint());
    }



    }

