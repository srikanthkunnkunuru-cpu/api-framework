package com.automation;

import io.restassured.RestAssured;
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
        FileInputStream file = new FileInputStream(
                "src/test/resources/config.properties"
        );
        config.load(file);

        RestAssured.baseURI = config.getProperty("baseURI");

        reqSpec = new RequestSpecBuilder()
                .addHeader("x-api-key", config.getProperty("apiKey"))
                .addHeader("Content-Type", "application/json")
                .build();
    }
}