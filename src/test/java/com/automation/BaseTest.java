package com.automation;

import io.restassured.RestAssured;
import java.io.FileNotFoundException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected static Properties config = new Properties();
    protected static RequestSpecification reqSpec;

    @BeforeClass
    public void setup() throws IOException {
        try {
            FileInputStream file = new FileInputStream(
                    "src/test/resources/config.properties"
            );
            config.load(file);
        } catch (FileNotFoundException e) {
            System.out.println("config.properties not found — using defaults");
        }

        String baseURI = config.getProperty("baseURI",
                "https://reqres.in/api");
        String apiKey = config.getProperty("apiKey",
                "free_user_3FZwwpetGPR2mWvFS9nOEwZK2rp");

        RestAssured.baseURI = baseURI;

        reqSpec = new RequestSpecBuilder()
                .addHeader("x-api-key", apiKey)
                .addHeader("Content-Type", "application/json")
                .build();
    }
}