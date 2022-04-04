package Main_assignment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class pagination{

    private static Logger logger = LogManager.getLogger(TasksTest.class);

    // Paging limit to 2
    @Test(priority = 7)
    public void limit2() {
        RestAssured.baseURI= "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        Response response = given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC002"))
                .post("/user/login");

        String jsonString = response.getBody().asString();
        String tokenGenerated = JsonPath.from(jsonString).get("token");
        request.header("Authorization", "Bearer "+tokenGenerated)
                .header("Content-Type","application/json");
        Response limit = request.given().get("/task?limit=2");
        limit.prettyPrint();
        logger.info("Top 2 data printed successfully");
    }

    // Paging limit to 5
    @Test(priority = 8)
    public void limit5() {
        RestAssured.baseURI= "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        Response response = given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC002"))
                .post("/user/login");

        String jsonString = response.getBody().asString();
        String tokenGenerated = JsonPath.from(jsonString).get("token");
        request.header("Authorization", "Bearer "+tokenGenerated)
                .header("Content-Type","application/json");
        Response limit = request.given().get("/task?limit=5");
        limit.prettyPrint();
        logger.info("Top 5 data printed successfully");
    }

    // Paging limit to 10
    @Test(priority = 9)
    public void limit10() {
        RestAssured.baseURI= "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecification request = RestAssured.given();
        Response response = given()
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON)
                .body(GenericClass.getData("Sheet1","TC002"))
                .post("/user/login");

        String jsonString = response.getBody().asString();
        String tokenGenerated = JsonPath.from(jsonString).get("token");
        request.header("Authorization", "Bearer "+tokenGenerated)
                .header("Content-Type","application/json");
        Response limit = request.given().get("/task?limit=10");
        limit.prettyPrint();
        logger.info("Top 10 data printed successfully");
    }
}
