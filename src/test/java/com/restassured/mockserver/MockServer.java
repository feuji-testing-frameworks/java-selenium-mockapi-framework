package com.restassured.mockserver;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockServer {
	
	private WireMockServer wireMockServer;
	
	public void startServer() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8082));
		wireMockServer.start();
		
		//Mock endpoint for token generation
		wireMockServer.stubFor(post(urlEqualTo("/auth"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody("{ \"token\": \"dummy_token\" }")));
		
		//Mock endpoint for getting all bookingIds
		wireMockServer.stubFor(get(urlEqualTo("/booking"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody("[{\"bookingid\": 1}, {\"bookingid\": 2}, {\"bookingid\": 3}]")));
		
		//Mock endpoint for creating a booking
		wireMockServer.stubFor(post(urlEqualTo("/booking"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody("{\"bookingid\": 233}")));
		
		//Mock endpoint for get booking details based on id
		wireMockServer.stubFor(get(urlPathMatching("/booking/\\d+"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody("{\"bookingid\":223, \"firstname\": \"Harry\" ,"
								+ "\"lastname\": \"Potter\"}")));
		
		//Mock endpoint for update the booking details based on id
		wireMockServer.stubFor(put(urlPathMatching("/booking/\\d+"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody("{\"bookingid\":223, \"firstname\": \"Jhon\" ,"
								+ "\"lastname\": \"Wick\"}")));
		
		//Mock endpoint for deleting the booking details based on id
		wireMockServer.stubFor(delete(urlPathMatching("/booking/\\d+"))
				.willReturn(aResponse()
						.withStatus(201)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"message\":\"Delete the booking details successfully\"}")));
		
	}
	
	
	public void stopServer() {
		if(wireMockServer != null) {
			wireMockServer.stop();
		}
	}

}
