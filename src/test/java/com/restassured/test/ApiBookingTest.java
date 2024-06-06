package com.restassured.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.restassured.base.Base;
import com.restassured.endpoints.BookingAPIEndPoints;

import io.restassured.response.Response;

public class ApiBookingTest extends Base {
	
	String token;
	int bookingId;
	
	@Test(priority = 1)
	public void testGetToken() {
		test = extentReports.createTest("Generate token")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = BookingAPIEndPoints.createToken(tokenCreation);
		token  = response.then()
					.log().all()
					.statusCode(200)
					.extract()
					.path("token");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2)
	public void testGettingAllBookingIds() {
		test =  extentReports.createTest("Getting All booking ids")
				 	.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = BookingAPIEndPoints.gettingAllBookingUrl();
		response.then().log().all();
		assertEquals(response.getStatusCode(),200);
				
	}
	
	@Test(priority = 3)
	public void testCreateBooking() {
		test = extentReports.createTest("Create Booking")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = BookingAPIEndPoints.createBooking(bookingDetails);
		System.out.println(response.body() + "=================");
		bookingId = response.then().log().all()
						.statusCode(200)
						.extract()
						.path("bookingid");
		System.out.println(bookingId + "=================");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 4)
	public void testGetBookingById() {
		test = extentReports.createTest("Getting booking details based on the bookingid")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response  response = BookingAPIEndPoints.getBooking(bookingId);
		response.then().log().all();
		assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority = 5)
	public void testUpdateBookingById() {
		test = extentReports.createTest("Update the booking details based on the bookingid")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		bookingDetails.setFirstname(faker.name().firstName());
		bookingDetails.setLastname(faker.name().lastName());
		Response response = BookingAPIEndPoints.updateBooking(bookingId, bookingDetails, token);
		response.then().log().all();
		assertEquals(response.statusCode() , 200);
	}
	
	@Test(priority = 6)
	public void testDeleteBooingById() {
		test = extentReports.createTest("Delete booking details based on the bookingid")
				.assignAuthor("Sai Teja").assignDevice("Windows");
		Response response = BookingAPIEndPoints.deleteBooking(bookingId, token);
		response.then().log().all();
		assertEquals(response.getStatusCode(), 201);
	}
	
	

}
