package com.restassured.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetails {
	
	private String  firstname;
	private String lastname;
	@JsonProperty("totalprice")
	private int totalPrice;
	@JsonProperty("depositpaid")
	private boolean depositPaid;
	@JsonProperty("bookingdates")
	private BookingDates bookingDates;
	@JsonProperty("additionalneeds")
	private String additionalNeeds;

}
