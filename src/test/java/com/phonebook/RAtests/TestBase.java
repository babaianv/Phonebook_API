package com.phonebook.RAtests;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String token =
            "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYmFicWExMjNAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MTEwMTE3MTAsImlhdCI6MTcxMDQxMTcxMH0.cwR5L47lQaqnNYvY_iQV8Y4UzRvULtmv_2S3BUG_4OQ";

    public static final String AUTH = "Authorization";


    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}

