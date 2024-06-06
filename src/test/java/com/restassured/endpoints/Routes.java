package com.restassured.endpoints;

public class Routes {

	public static String bookingBaseUrl = "https://restful-booker.herokuapp.com";
	
	public static String authUrl = bookingBaseUrl + "/auth";
	public static String getAllUrl = bookingBaseUrl + "/booking";
	public static String getBookingUrl = bookingBaseUrl + "/booking/{id}";
	public static String createBookingUrl = bookingBaseUrl + "/booking";
	public static String updateBookingUrl = bookingBaseUrl + "/booking/{id}";
	public static String partialUpdateBookingUrl = bookingBaseUrl + "/booking/{id}";
	public static String deleteBookingUrl = bookingBaseUrl + "/booking/{id}";
}
