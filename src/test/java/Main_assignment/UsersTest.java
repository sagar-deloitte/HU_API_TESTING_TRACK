package Main_assignment;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class UsersTest extends extentReport.report {

    private static Logger logger = LogManager.getLogger(UsersTest.class);
    ResponseSpecification responseSpecification;

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI= "https://api-nodejs-todolist.herokuapp.com";

        ResponseSpecBuilder responseSpecBuilder =new ResponseSpecBuilder().
                expectContentType(ContentType.JSON);
        responseSpecification = responseSpecBuilder.build();
    }

    //Create an user
    @Test(priority = 1)
    public  void createUser() throws IOException {
        Response response = given()
                .auth()
                .basic("email","password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC001"))
                .post("/user/register");
        assertThat(response.statusCode(),equalTo(201));
        logger.error("User already created");
    }

    // User login
    @Test(priority = 2)
    public void login(){
        Response response = given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC002"))
                .post("/user/login");
        assertThat(response.statusCode(),equalTo(200));
        logger.info("User logged-in successfully");
    }

    // Trying to register exiting user
    @Test(priority = 3)
    public void existingUserRegister(){
        Response response = given()
                .auth()
                .basic("email","password")
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC001"))
                .post("/user/register");
        String jsonString = response.getBody().asString();

        assertThat(jsonString,jsonString.contains("E11000 duplicate key error collection"));
        assertThat(response.statusCode(),equalTo(400));
        logger.error("User already exists");
    }

    // Trying to login non-registered user
    @Test(priority = 4)
    public void nonExistingUserLogin(){
        Response response = given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC003"))
                .post("/user/login");
        String jsonString = response.getBody().asString();
        assertThat(jsonString,equalTo("\"Unable to login\""));
        assertThat(response.statusCode(),equalTo(400));
        logger.error("User does not exists");
    }

    // Input with wrong format
    @Test(priority = 5)
    public void wrongRequestBody(){
        Response response = given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC004"))
                .post("/user/login");
        String jsonString = response.getBody().asString();
        assertThat(response.statusCode(),equalTo(400));
        logger.error("Wrong format of request body");
    }
}
