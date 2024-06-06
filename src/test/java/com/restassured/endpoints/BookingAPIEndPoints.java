package com.restassured.endpoints;


import com.restassured.payloads.BookingDetails;
import com.restassured.payloads.TokenCreation;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class BookingAPIEndPoints {

	public static Response createToken(TokenCreation tokenCreation) {
		return given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(tokenCreation)
				.when()
				.post(Routes.authUrl);
	}
	
	public static Response gettingAllBookingUrl() {
		return given()
				.get(Routes.getAllUrl);
	}
	
	public static Response createBooking(BookingDetails bookingDetails) {
		return given()
				.log().all()
				.contentType(ContentType.JSON)
				.body(bookingDetails)
				.when()
				.post("https://restful-booker.herokuapp.com/booking");
	}
	
	public static Response getBooking(int bookingid) {
		return given()
				.pathParam("id",bookingid)
				.log().all()
				.when()
				.get(Routes.getBookingUrl);
	}
	
	public static Response updateBooking(int bookingid,BookingDetails bookingDetails,String token) {
		return given()
				.contentType(ContentType.JSON)
				.header("Cookie" , "token="+token)
				.pathParam("id", bookingid)
				.body(bookingDetails)
				.log().all()
				.when()
				.put(Routes.updateBookingUrl);
	}
	
	public static Response deleteBooking(int bookingid,String token) {
		return given()
				.header("Cookie" , "token="+token)
				.pathParam("id", bookingid)
				.when()
				.delete(Routes.deleteBookingUrl);
	}
}
