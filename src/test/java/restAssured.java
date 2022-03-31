import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class restAssured {
    @Test
    public void test_get_call(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
        when().
                get("/posts").
        then().
                statusCode(200).
                body("userId",hasItem(4),
                        "[40].id",is(equalTo(5)));
    }
    @Test
    public void test_post_call(){
        File file = new File("src//test//resources//data.json");
        given().
                baseUri("https://reqres.in/api").
                body(file).
        when().
                post("/users").
        then().
                statusCode(201).
                body("name",hasToString("Arun"),
                "job",hasToString("Manager"));
    }

}
