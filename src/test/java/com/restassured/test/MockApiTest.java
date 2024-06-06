package com.restassured.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.restassured.base.Base;
import com.restassured.mockserver.MockServer;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class MockApiTest extends Base {
	
	private MockServer mockServer;
	String token;
	int id;
	
	@BeforeClass
	public void setup() {
		mockServer = new MockServer();
		mockServer.startServer();
		RestAssured.baseURI = "http://localhost:8082";
	}
	
	@Test(priority = 1)
	public void testCreateToken() {
		test = extentReports.createTest("Generate mock api token")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = RestAssured.given().contentType("application/json")
					.body(tokenCreation)
					.when().post("/auth");
		token = response.then()
				.log().all()
				.statusCode(200)
				.extract()
				.path("token");
		assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority = 2)
	public void testGetAllBookingIds() {
		test = extentReports.createTest("Getting all booking ids")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = RestAssured.given().when().get("/booking");
		response.then().log().all();
		assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority = 3)
	public void testCreateBooking() {
		test = extentReports.createTest("Create booking")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = RestAssured.given().contentType("application/json")
				.body(bookingDetails).when().post("/booking");
		
		id = response.then()
				.log().all()
				.statusCode(200)
				.extract()
				.path("bookingid");
		assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority = 4) 
	public void testGetBookingById() {
		test = extentReports.createTest("Get Booking details based on booking id")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = RestAssured.given().contentType("application/json")
				.pathParam("id", id).when().get("/booking/{id}");
		response.then().log().all();
		assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority = 5)
	public void testUpdateBookingDetails() {
		test = extentReports.createTest("Update booking details based on booking id")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response  response = RestAssured.given().contentType("application/json")
				.pathParam("id", id).when().put("/booking/{id}");
		response.then().log().all();
		assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority = 6)
	public void testDeleteBookingDetails() {
		test = extentReports.createTest("Delete booking details based on booking id")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = RestAssured.given().contentType("appilcation/json")
				.pathParam("id", id).delete("booking/{id}");
		response.then().log().all();
		assertEquals(response.statusCode(), 201);
	}
	
	@AfterClass
	public void tearDown() {
		mockServer.stopServer();
	}

}
