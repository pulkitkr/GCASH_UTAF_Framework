package com.business.gcash;

import com.driverInstance.CommandBase;
import com.driverInstance.DriverInstance;
import com.driverInstance.DriverManager;
import com.driverInstance.Drivertools;
import com.gcash.pages.*;
import com.propertyfilereader.PropertyFileReader;
import com.utility.ExtentReporter;
import com.utility.LoggingUtils;
import com.utility.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static com.utility.Utilities.*;

public class GCashBusinessLogic {

	static LoggingUtils logger = new LoggingUtils();
	private int timeout;
	private int retryCount;

	ExtentReporter extent = new ExtentReporter();
	public static SoftAssert softAssert = new SoftAssert();

//	public static PropertyFileReader prop = new PropertyFileReader(".\\properties\\testdata.properties");

	public GCashBusinessLogic(String Application, String deviceName, String portno) throws InterruptedException {
		new CommandBase(Application, deviceName, portno);
		init();
	}

	public void init() {
		PropertyFileReader handler = new PropertyFileReader("properties/Execution.properties");
		setTimeout(Integer.parseInt(handler.getproperty("TIMEOUT")));
		setRetryCount(Integer.parseInt(handler.getproperty("RETRY_COUNT")));
		logger.info(
				"Loaded the following properties" + " TimeOut :" + getTimeout() + " RetryCount :" + getRetryCount());
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void tearDown() {
		softAssert.assertAll();
		logger.info("Session ID: " + ((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		ExtentReporter.extentLogger("",
				"Session ID: " + ((RemoteWebDriver) DriverManager.getAppiumDriver()).getSessionId());
		logger.info("Session is quit");
		ExtentReporter.extentLogger("", "Session is quit");
		setScreenshotSource();
		DriverManager.getAppiumDriver().quit();
	}

	/**
	 * @author Pulkit
	 * @handle Popups method
	 * @param userType
	 * @throws Exception
	 */
	public void GGivesAppLaunch() throws Exception {

		ExtentReporter.HeaderChildNode("Request Permission");
		if (getPlatform().equals("Android")) {
//		Utilities.explicitWaitVisibility(GGivesLoginPage.objRequestPermissionPopup, 10);
//			if (Utilities.verifyElementPresent(GGivesLoginPage.objRequestPermissionPopup, "Request Permission Popup")) {
//				Utilities.click(GGivesLoginPage.objRequestPermissionOkBtn, "Ok Button");
//
//				if (Utilities.verifyElementPresent(GGivesLoginPage.objPicturePopup, "GCash Picture Popup")) {
//					Utilities.click(GGivesLoginPage.objPictureDontAllowBtn, "Don't Allow Button");
//				} else {
//					logger.info("No Picture allow Popup is displayed");
//					ExtentReporter.extentLoggerPass("Popup", "No Picture allow Popup is displayed");
//				}
//
//				if (Utilities.verifyElementPresent(GGivesLoginPage.objLocationPopup, "GCash Location access Popup")) {
//					Utilities.click(GGivesLoginPage.objLocationDontAllowBtn, "Don't Allow Button");
//				} else {
//					logger.info("No location allow Popup is displayed");
//					ExtentReporter.extentLoggerPass("Popup", "No location allow Popup is displayed");
//				}
//				if (Utilities.verifyElementPresent(GGivesLoginPage.objContactPopup, "GCash contact access Popup")) {
//					Utilities.click(GGivesLoginPage.objContactDontAllowBtn, "Don't Allow Button");
//				} else {
//					logger.info("No contact allow Popup is displayed");
//					ExtentReporter.extentLoggerPass("Popup", "No contact allow Popup is displayed");
//				}
//
//				if (Utilities.verifyElementPresent(GGivesLoginPage.objPhotoPopup, "GCash Photo media access Popup")) {
//					Utilities.click(GGivesLoginPage.objPhotoDontAllowBtn, "Don't Allow Button");
//				} else {
//					logger.info("No photo media allow Popup is displayed");
//					ExtentReporter.extentLoggerPass("Popup", "No photo media allow Popup is displayed");
//				}
//
//				if (Utilities.verifyElementPresent(GGivesLoginPage.objWelcomePageVerify, "Welcome to GCash Page")) {
//					logger.info("Navigated to Welcome to GCash Page");
//					ExtentReporter.extentLoggerPass("Welcome Page", "Navigated to Welcome to GCash Page");
//				} else {
//					logger.info("Failed to Navigate to Welcome to GCash Page");
//					ExtentReporter.extentLoggerFail("Welcome Page", "Failed to Navigate to Welcome to GCash Page");
//				}
//
//				switchPlatformToWeb("https://www.jiocinema.com");
//				waitTime(5000);
//				closeWebBrowser();
//				switchPlatformToAndroid();
////				loginToGCash("9893845043");
//
//			} else {
//				logger.info("Navigated to Welcome to GCash Page");
//				ExtentReporter.extentLoggerPass("Welcome Page", "Navigated to Welcome to GCash Page");
//			}
		} else if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("iOS")) {
			if (Utilities.verifyElementPresent(GGivesLoginPage.objRegisterBtn, "Welcome to GCash Page")) {
				Utilities.click(GGivesLoginPage.objLoginBtn, "Login Button");
			} else {
				logger.info("Failed to Navigate to Welcome to GCash Page");
				ExtentReporter.extentLoggerFail("Welcome Page", "Failed to Navigate to Welcome to GCash Page");
			}
			
		}
	}
}

//	/**
//	 * @login to application method
//	 * @param InvalidMobileNo
//	 * @param vailidNo
//	 * @throws Exception
//	 */
//	public void loginToGCash(String InvalidMobileNo) throws Exception {
//		ExtentReporter.HeaderChildNode("Login");
//		String welcometoGCashPage = getText(GGivesLoginPage.objWelcomePageVerify);
//		if (Utilities.verifyElementPresent(GGivesLoginPage.objWelcomePageVerify, welcometoGCashPage)) {
//			Assert.assertEquals("Welcome to GCash", welcometoGCashPage);
//			Utilities.click(GGivesLoginPage.objLoginBtn, "Login Button");
//			logger.info("Navigated to GCash  Login Page");
//			ExtentReporter.extentLoggerPass("Login Page", "Navigated to GCash  Login Page");
//		} else {
//			logger.info("Failed to Navigate to GCash  Login Page");
//			ExtentReporter.extentLoggerFail("Welcome Page", "Failed to Navigate to GCash  Login Page");
//		}
//
//		if (Utilities.verifyElementDisplayed(GGivesLoginPage.objGCashLogo)) {
//			Utilities.type(GGivesLoginPage.objMobileNumberField, InvalidMobileNo, "Mobile Number text Field");
//			Utilities.click(GGivesLoginPage.objNextBtn, "Next Button");
//
//			logger.info("Navigated to OTP Authentication Page");
//			ExtentReporter.extentLoggerPass("OTP Authentication Page", "Navigated to GCash OTP Authentication Page");
//		} else {
//			logger.info("Failed to Navigate to OTP Authentication Page");
//			ExtentReporter.extentLoggerFail("OTP Authentication Page",
//					"Failed to Navigate to GCash OTP Authentication Page");
//		}
//	}
//
//	/**
//	 * GSave Login method
//	 * 
//	 * @param validNo
//	 * @throws Exception
//	 */
//	public void GsaveLogin(String validNo, String GSaveValidOTP) throws Exception {
//		extent.HeaderChildNode("GSave");
//		Utilities.Wait(10000);
//		String MPINPage = Utilities.getText(GGivesLoginPage.objMpinPageVerify);
//		if (Utilities.verifyElementPresent(GGivesLoginPage.objMpinPageVerify, MPINPage + " Page")) {
//			Assert.assertEquals("Enter your MPIN", MPINPage);
//
//			Utilities.click(GGivesLoginPage.objChangeNumber, "Change Number");
//			Utilities.verifyElementPresent(GGivesLoginPage.objOopsPopup, "Oops! popup");
//			Utilities.click(GGivesLoginPage.objYesBtn, "Yes Button");
//			logger.info("Navigated to Enter Mobile Number Page");
//			extent.extentLoggerPass("Mobile Number Page", "Navigated to Enter Mobile Number Page");
//
//		} else {
//			logger.info("Failed to Navigate to Enter Mobile Number Page");
//			extent.extentLogger("Mobile Number Page", "Failed to Navigate to Enter Mobile Number Page");
//		}
//
//		if (Utilities.verifyElementDisplayed(GGivesLoginPage.objGCashLogo)) {
//			Utilities.type(GGivesLoginPage.objMobileNumberField, validNo, "Mobile Number text Field");
//			String profileNo = getText(GGivesLoginPage.objMobileNumberField);
//
//			Utilities.click(GGivesLoginPage.objNextBtn, "Next Button");
//			logger.info("Navigated to OTP Authentication Page");
//			extent.extentLoggerPass("OTP Authentication Page", "Navigated to GCash OTP Authentication Page");
//		} else {
//			logger.info("Failed to Navigate to OTP Authentication Page");
//			extent.extentLoggerFail("OTP Authentication Page", "Failed to Navigate to GCash OTP Authentication Page");
//		}
//
//		Utilities.explicitWaitVisibility(GGivesLoginPage.objAuthenticationPageVerify, 20);
//		if (Utilities.verifyElementPresent(GGivesLoginPage.objAuthenticationPageVerify, "OTP Authentication Page")) {
//			Utilities.type(GGivesLoginPage.objOTPField, GSaveValidOTP, "OTP Field");
//			Utilities.click(GGivesLoginPage.objSubmitBtn, "Submit Button");
//			logger.info("Navigated to MPIN Page");
//			extent.extentLoggerPass("MPIN Page", "Navigated to MPIN Page");
//		} else {
//			logger.info("Failed to Navigate to MPIN Page");
//			extent.extentLogger("MPIN Page", "Failed to Navigate to MPIN Page");
//		}
//
//		if (Utilities.verifyElementPresent(GGivesLoginPage.objMpinPageVerify, MPINPage + " Page")) {
//			Assert.assertEquals("Enter your MPIN", MPINPage);
//			Utilities.click(GGivesLoginPage.objOneBtn, "1");
//			Utilities.click(GGivesLoginPage.objTwoBtn, "2");
//			Utilities.click(GGivesLoginPage.objOneBtn, "1");
//			Utilities.click(GGivesLoginPage.objTwoBtn, "2");
//		}
//		if (Utilities.verifyElementPresent(GGivesHomePage.objDiscoverPopup, "Discover popup")) {
//			logger.info("Navigated to Home Page");
//			extent.extentLoggerPass("HomePage", "Navigated to Home Page");
//			Utilities.click(GGivesHomePage.objDiscoverBtn, "Discover Button");
//
////			suggestionTapMidScreen(GGivesHomePage.objTourMsg);
//
//			String homeHeaderText = getText(GGivesHomePage.objHomePageHeader);
//			Assert.assertEquals(homeHeaderText, "Hello!");
//		} else {
//			logger.info("Failed to Navigate to Home Page");
//			extent.extentLoggerFail("HomePage", "Failed to Navigate to Home Page");
//		}
//	}
//
//}
