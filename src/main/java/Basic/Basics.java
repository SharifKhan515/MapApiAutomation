package Basic;

import files.HelperMethod;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Basics {

    public static void main(String[] args) {

        //Implement add place API
        //Given-- All input details: query_parameter, header and body
        // When-- submit the API: -resource(uri), http method
        // Then-- validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.addPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
        // System.out.println(response);
        JsonPath jsonResponse = HelperMethod.stringToJson(response);
        String place_id = jsonResponse.get("place_id");
        System.out.println(place_id);

        given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json").
                body("{\n" +
                        "\"place_id\":\"" + place_id + "\",\n" +
                        "\"address\":\"70 winter walk, USA\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        String getPlace = given().log().all().queryParam("place_id", place_id).queryParam("key", "qaclick123").header("content-type", "application/json")
                .when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsonPath = HelperMethod.stringToJson(response);

        String address = jsonPath.get("address");
        System.out.println(address);

    }
}
