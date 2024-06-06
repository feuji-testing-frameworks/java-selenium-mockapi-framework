package com.restassured.base;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.github.javafaker.Faker;
import com.restassured.payloads.BookingDates;
import com.restassured.payloads.BookingDetails;
import com.restassured.payloads.TokenCreation;

import io.restassured.RestAssured;

public class Base {
	
	public static final Logger logger = LogManager.getLogger(Base.class.getName());
	public Faker faker;
	public BookingDetails bookingDetails;
	public TokenCreation tokenCreation;
	public ExtentReports extentReports;
	public ExtentTest test;
	
	@BeforeTest
    public void setUp() {
        logger.info("Report Setup in each Test");
        ExtentSparkReporter spark = new ExtentSparkReporter("./reports/extentreport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("API_Report");
        spark.config().setReportName("Test Report");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		bookingDetails = new BookingDetails();
		bookingDetails.setFirstname(faker.name().firstName());
		bookingDetails.setLastname(faker.name().lastName());
		bookingDetails.setTotalPrice(faker.number().randomDigit());
		bookingDetails.setDepositPaid(faker.bool().bool());
		bookingDetails.setBookingDates(new BookingDates("2018-01-01", "2019-01-01"));
		bookingDetails.setAdditionalNeeds(faker.lorem().sentence(5));
		tokenCreation =  new TokenCreation();
		tokenCreation.setUsername("admin");
		tokenCreation.setPassword("password123");
	}
	
	@BeforeMethod
    public void beforeMethod() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Throwable throwable = result.getThrowable();
            StringWriter error = new StringWriter();
            throwable.printStackTrace(new PrintWriter(error));
            logger.error(error);
        }
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        logger.info("Report Result in each Test");
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getThrowable());
            test.log(Status.FAIL, "Test Case Failed");
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed Sucessfully");
        } else {
            test.log(Status.SKIP, result.getTestName());
        }
    }

    @AfterTest
    public void endReport() {
        extentReports.flush();
    }

}
