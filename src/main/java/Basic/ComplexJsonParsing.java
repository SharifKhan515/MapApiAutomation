package Basic;


import files.HelperMethod;
import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class ComplexJsonParsing {

    public JsonPath responseJson;

    @BeforeClass()
    public void initialize() {
        String response = payload.course();
        responseJson = HelperMethod.stringToJson(response);
    }

    //1. Print No of courses returned by API
    @Test()
    public void NoOfCourses() {

        int courseCount = responseJson.getInt("courses.size()");
        ArrayList<String> arrayList = responseJson.get("courses");

        System.out.println(arrayList.size());
        System.out.println(courseCount);

    }

    //2.Print Purchase Amount
    @Test()
    public void purchaseAmountOfCourses() {

        System.out.println(responseJson.getInt("dashboard.purchaseAmount"));

    }

    // 3. Print Title of the first course
    @Test()
    public void titleOfFirstCourse() {

        ArrayList<String> titles = responseJson.get("courses.title");
        System.out.println(titles.get(0));


    }

    //4. Print All course titles and their respective Prices3. Print Title of the first course
    @Test()
    public void coursesPrice() {

       /* ArrayList<HashMap<String, String>> courses = responseJson.get("courses");

        for(HashMap<String, String> course: courses ){

            //String price = course.get("price").toString();
            System.out.println("Title : "+course.get("title"));
            System.out.println("Price : "+  );

        }*/

        int courseCount = responseJson.getInt("courses.size()");
        for (int i = 0; i < courseCount; i++) {
            String title = responseJson.get("courses[" + i + "].title");
            String price = responseJson.getString("courses[" + i + "].price");
            System.out.println("Title " + title);
            System.out.println("Price " + price);
        }


    }

    //5. Print no of copies sold by RPA Course
    @Test()
    public void soldCountOfRPA() {
        int courseCount = responseJson.getInt("courses.size()");
        for (int i = 0; i < courseCount; i++) {
            String title = responseJson.get("courses[" + i + "].title");
            if (title.equalsIgnoreCase("RPA")) {
                String copies = responseJson.getString("courses[" + i + "].copies");
                System.out.println("Title " + title);
                System.out.println("Copies " + copies);
                break;
            }
        }
    }

    //6. Verify if Sum of all Course prices matches with Purchase Amount
    @Test()
    public void validateSoldAmount() {
        int totalPurchaseAmount = responseJson.getInt("dashboard.purchaseAmount");
        int totalAmount = 0;
        int courseCount = responseJson.getInt("courses.size()");
        for (int i = 0; i < courseCount; i++) {

            totalAmount += responseJson.getInt("courses[" + i + "].copies") * responseJson.getInt("courses[" + i + "].price");

        }

        System.out.println("Total Purchase amount: " + totalPurchaseAmount);
        System.out.println("Total Amount: " + totalAmount);
        Assert.assertEquals(totalAmount, totalPurchaseAmount);

    }
}
