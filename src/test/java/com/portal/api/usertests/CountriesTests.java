package com.portal.api.usertests;

import org.testng.annotations.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.portalRest.connHeaders.JsonRequired;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.util.ReflectionUtils;
public class CountriesTests {
	
private String bodyUser = "{\"firstName\":\"Koti\",\"email\":\"koteswararao.tekkem@broadcom.com\",\"locale\":\"en\",\"status\":\"REGISTRATION_INIT\",\"access\":[{\"roleUuid\":\""
		+ "00000001-0001-0001-0001-000000000001"
		+ "\"}],\"lastName\":\"test\",\"uuid\":\"{{GENERATED_GUID}}\"}";



public static String getJsonString(Object object) throws IOException {
    return getGsonAsObject().toJson(object);

  }

public static <T> T getListOfObjectFromJsonString(String json, Type typeOfT) throws IllegalAccessException {
	    Gson gson = getGsonAsObject();
	    //Just make sure that all required fields was exists in JSON and also populated in pojo.
	    T pojo = gson.fromJson(json, typeOfT);
	    return pojo;
	  }


public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
    fields.addAll(Arrays.asList(type.getDeclaredFields()));

    if (type.getSuperclass() != null) {
      getAllFields(fields, type.getSuperclass());
    }
    return fields;
  }

/**
 * Validating Pojo based on JsonRequired annotation. If any fields annotated with JsonRequired
 * then it will check the value is set for that field or not. If annotation is set with
 * "RequiredIf" value then it fetch the data of the field mentioned in RequiredIf and then check
 * is it true or not if true then it will validate otherwise not
 */
public static <T> void validateJsonResponse(T pojo) throws IllegalAccessException {
  List<Field> fields = new ArrayList<>();
  getAllFields(fields, pojo.getClass());

  Boolean isRequired = true;
  for (Field f : fields) {
    JsonRequired annotation = f.getAnnotation(JsonRequired.class);
    if (annotation != null) {
      f.setAccessible(true);
      //Checking is JsonRequired annotated with requiredIf value
      if (!annotation.requiredIf().isEmpty()) {
        try {
          //getting the field which is mentioned in RequiredIf value
          Field requiredIfField = ReflectionUtils
              .findField(pojo.getClass(), annotation.requiredIf());

          if (requiredIfField == null) {
            throw new NoSuchFieldException("filed:" + annotation.requiredIf() + "not found");
          }

          //If that field is boolean then we will do check the value of it otherwise we will consider
          //that field as required and validate it.
          if (requiredIfField.getType().equals(boolean.class) || requiredIfField.getType()
              .equals(Boolean.class)) {
            requiredIfField.setAccessible(true);
            isRequired = (Boolean) requiredIfField.get(pojo);
          }

        } catch (NoSuchFieldException e) {
          e.printStackTrace();
        }
      }

      if (isRequired && f.get(pojo) == null) {
        throw new JsonParseException("Missing field in JSON: " + f.getName());
      }
    }
  }
}

public static Response createEntityReturnRawResponse(String payLoad)
	      throws IOException {
	useRelaxedHTTPSValidation();
	    return given().contentType(ContentType.JSON)
	        .header("Authorization", "bearer "+"c44df87d-de15-4f7d-9af4-dcdcd5a8ff2a")
	        .body(payLoad)
	        .when()
	        .post("https://portal-kk640244d14-ssg.app.rancher.lvn.broadcom.net:443/0-portal-kk640244b14/Organizations");
	  }


public static Gson getGsonAsObject() {
	return new GsonBuilder().create();
}

	/*
	 * public static String tPayloadAsJson(Object object, T type) { return new
	 * GsonBuilder().create().fromJson(object, type); }
	 */

@Test
public void getUser() {
	useRelaxedHTTPSValidation();
	Response resp = given().header("Authorization", "bearer "+"8f06f96a-1da0-4d0c-8465-6d4dc6c3c465").contentType(ContentType.JSON).when()
	.get("https://portal-kk640244a10-ssg.app.rancher.lvn.broadcom.net:443/0-portal-kk640244a10/v2/users").then()
	.assertThat().statusCode(200).extract().response();
	
	resp.prettyPrint();
	
	JsonPath jospath = new JsonPath(resp.asString());
	
	int totoalUsers = jospath.getInt("list.size()");
	System.out.println(totoalUsers);
	
	
}
	
	@Test
	public void getAllCounties() {
		System.out.println("Get All users...");
		useRelaxedHTTPSValidation();
		
		Response resp = given().header("Authorization", "bearer "+"8f06f96a-1da0-4d0c-8465-6d4dc6c3c465").contentType(ContentType.JSON).when()
		.get("https://portal-kk640244a10-ssg.app.rancher.lvn.broadcom.net:443/0-portal-kk640244a10/v2/users").then()
		.assertThat().statusCode(200).extract().response();
		resp.prettyPeek();
		
		JsonPath jsonPath = new JsonPath(resp.asString());
		int allUsers = jsonPath.getInt("list.size()");
		System.out.println("users count ::"+ allUsers);
		
		for (int i = 0; i < allUsers; i++) {
			System.out.println("username  :: ");
			
			System.out.println(jsonPath.getString("list[" + i + "].username"));
			
		}
	}
	
	@Test
	public void createUsers() {
		useRelaxedHTTPSValidation();
		
		Response resp =	given().header("Authorization", "bearer "+"9d995554-4103-4203-9da5-eb0cbfe41887").contentType(ContentType.JSON).body(bodyUser).when()
		.post("https://portal-kk640244a10-ssg.app.rancher.lvn.broadcom.net:443/0-portal-kk640244a10/v2/users").then().assertThat().statusCode(200).extract().response();
		
		resp.prettyPrint();
		
	}
	
	@Test
	public void getAllCountries() {
		System.out.println("Get All users...");
		Response resp =given().when().get("https://restcountries.eu/rest/v2/all").then().assertThat().statusCode(200).extract().response();
		
	}

}
