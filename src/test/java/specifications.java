import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class specifications {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeTest
    public void setUp(){
//        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
//        requestSpecBuilder.setBaseUri("https://jsonplaceholder.typicode.com").
//                addHeader("Content-Type","application.json");
        requestSpecification = RestAssured.with().
                baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type","application.json");

        ResponseSpecBuilder responseSpecBuilder =new ResponseSpecBuilder().
                expectContentType(ContentType.JSON);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test(priority = 1)
    public void callGet(){
        Response response = requestSpecification.get("/posts");
        assertThat(response.statusCode(),equalTo(200));
        responseSpecification.body("userId[39]",equalTo(4));
    }

    @Test(priority = 2)
    public void callPut(){
        File file = new File("src//test//resources//data.json");
        requestSpecification = RestAssured.with().
                baseUri("https://reqres.in/api").
                header("Content-Type","application.json").body(file);
        Response response = requestSpecification.post("/users");
        assertThat(response.statusCode(),equalTo(201));
        responseSpecification.body("name",equalTo("Arun")).body("job",equalTo("Manager"));
    }
}
