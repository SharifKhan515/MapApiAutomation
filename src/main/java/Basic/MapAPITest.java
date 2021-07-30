package Basic;

import files.HelperMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.AddPlace;
import pojo.Location;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class MapAPITest {

    String place_id;
    String key;
    String content_type;
    AddPlace addPlace;
    List<String> types;
    Location location;
    JsonPath responseJson;

    @BeforeClass
    public void init(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        key = "qaclick123";
        content_type = "application/json";
        addPlace = new AddPlace();
        types = new ArrayList<>();
        types.add("First types");
        types.add("Second types");
        types.add("Thirs Types");
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
    }



    @Test
    public void getPlaceApiTest(){




       String response  = given().queryParam("key",key).header("Content-Type",content_type )
                .body(addPlace).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();


       responseJson = HelperMethod.stringToJson(response);
       place_id = responseJson.getString("place_id");

        System.out.println(place_id);


    }
}
