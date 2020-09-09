package com.portal.api.usertests;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class CountriesTests {
	
	@Test
	public void getAllCounties() {
		System.out.println("Get All users...");
		Response resp =given().when().get("https://restcountries.eu/rest/v2/all").then().assertThat().statusCode(200).extract().response();
		
	}

}
