package com.qa.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.body.bodyPara;

import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;

import static io.restassured.matcher.RestAssuredMatchers.*;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.*;

public class testRest {
	String str_place="";
	@Test()
	public void sampleLogin() {
		/*
		 * Given application is up and running When i perform the GET request using the
		 * given url Then the status code should be 200 Ok And the response body should
		 * be in Json Format
		 * 
		 */
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Response response = RestAssured.get("https://reqres.in/api/users?page=2/");
//		String resInString = response.asString();
//		System.out.println(resInString);
//		System.out.println(response.statusCode());
//		int statusCode = response.getStatusCode();
//		System.out.println(statusCode);
		
		String Response = given().log().all().queryParam("key", "qaclick123").
		body(bodyPara.body1()).
		with().
		contentType("application/json").
		when().post("/maps/api/place/add/json").
		then().log().all().statusCode(200).body("scope",equalTo("APP")).extract().response().asString();
	
		System.out.println(Response);
		
		JsonPath js = new JsonPath(Response);
		str_place = js.get("place_id");
		System.out.println(str_place);
		
	
	
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String str = given().
		queryParam("key", "qaclick123").
		body("{\r\n"
				+ "\"place_id\":\""+str_place+"\",\r\n"
				+ "\"address\":\"70 winter walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").
		with().
		contentType("application/json").
		when().
		put("/maps/api/place/update/json").
		then().
		assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("str-----"+str);
		
		String strGet = given().
				queryParam("place_id", str_place).
				when().
				get("maps/api/place/get/json").
				then().
				assertThat().log().all().
				statusCode(200).extract().response().asString();
		System.out.println("hello--------"+strGet);
		
//		JsonPath jsNe = new JsonPath(strGet);
//		String k = jsNe.get("address");
//		System.out.println(k);
				
	}
	
	
}
