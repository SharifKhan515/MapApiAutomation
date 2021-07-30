package Basic;

import files.HelperMethod;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    String place_id;
    String key;
    String content_type;
    AddPlace addPlace;
    List<String> types;
    Location location;
    JsonPath responseJson;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void init() {
        key = "qaclick123";
        content_type = "application/json";
        addPlace = new AddPlace();
        types = new ArrayList<>();
        types.add("First types");
        types.add("Second types");
        types.add("Third Types");
        location = new Location();
        location.setLat(-50.2369974);
        location.setLng(100.24455445);
        addPlace.setAccuracy(5);
        addPlace.setAddress("Bangladesh,dhaka");
        addPlace.setLanguage("Bangla");
        addPlace.setName("Shahjhanpur");
        addPlace.setPhone_number("1236588789865");
        addPlace.setTypes(types);
        addPlace.setLocation(location);
        requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", key)
                .setContentType(ContentType.JSON).build();
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    }


    @Test
    public void getPlaceApiTest() {

        RequestSpecification request = given().spec(requestSpecification).body(addPlace);
        Response response = request.when().post("maps/api/place/add/json").then().spec(responseSpecification).extract().response();

        responseJson = HelperMethod.stringToJson(response.asString());
        place_id = responseJson.getString("place_id");
        System.out.println(place_id);

    }
}
