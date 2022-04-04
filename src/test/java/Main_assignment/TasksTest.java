package Main_assignment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static io.restassured.RestAssured.given;

public class TasksTest extends extentReport.report {

    private static Logger logger = LogManager.getLogger(TasksTest.class);

    // Adding 20 tasks
    @Test(priority = 6)
    public void addTasks() throws IOException {
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

        File file1 = new File(".\\src\\test\\resources\\Tasks.xls");
        FileInputStream inputStream = new FileInputStream(file1);
        HSSFWorkbook wb=new HSSFWorkbook(inputStream);
        HSSFSheet sheet1=wb.getSheetAt(0);
        int rowCount = sheet1.getLastRowNum();
        for(int i =0;i<=rowCount;i++) {
            HSSFRow row1 = sheet1.getRow(i);
            HSSFCell cell1 = row1.getCell(0);
            String task = cell1.getStringCellValue();
            Response addUser = request.body(task).post("/task");
            String resultString = addUser.getBody().asString();
            logger.info("Task added successfully");
        }
    }
}
