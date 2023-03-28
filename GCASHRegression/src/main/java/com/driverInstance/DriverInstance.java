package com.driverInstance;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.SkipException;

import com.propertyfilereader.PropertyFileReader;
import com.utility.Utilities;
//import com.zee5.ApplicasterPages.AMDOnboardingScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverInstance extends Drivertools {

	public static ThreadLocal<RemoteWebDriver> tlWebDriver = new ThreadLocal<>();

	@SuppressWarnings("static-access")
	public DriverInstance(String Application, String deviceName, String portno) {
		super(Application, deviceName, portno);
		try {
			switch (getPlatform()) {
			case "Android":
				DriverManager.setAppiumDriver((AppiumDriver<WebElement>) new AndroidDriver<WebElement>(
						new URL(getremoteUrl()), this.generateAndroidCapabilities(Application, deviceName, portno)));
				Instant endTime = Instant.now();
				timeElapsed = Duration.between(startTime, endTime);
				logger.info("Time taken to launch the App (millisec)" + timeElapsed.toMillis());

				break;

			case "MPWAiOSSafari":
				DriverManager
						.setAppiumDriver((AppiumDriver<WebElement>) new IOSDriver<WebElement>(new URL(getremoteUrl()),
								this.generateiOSCapabilities(Application, deviceName, portno)));
				DriverManager.getAppiumDriver().get(getURL());
				break;

			case "Web":
				LaunchBrowser(getBrowserType());
				break;
				
			case "iOS":
				tlDriver.set((AppiumDriver<WebElement>) new IOSDriver<WebElement>(new URL(getremoteUrl()), this.generateiOSCapabilities(Application, deviceName,portno)));
			
				break;

			default:
				throw new SkipException("Incorrect Platform...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SkipException("Device not connected OR Appium Studio service is down...");
		}
		Utilities.initDriver();
	}

	/**
	 * @param application
	 * @return Android capabilities
	 * @throws Exception
	 */
	protected DesiredCapabilities generateAndroidCapabilities(String application, String deviceName, String portno) {
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.UDID, deviceName);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("autoAcceptAlerts", true);
		if (getPlatform().equals("MPWA")) {
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			return capabilities;
		}
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getAppPackage());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getappActivity());
		if (Utilities.relaunch) {
			removePermisson(getAppPackage());
		}
		startTime = Instant.now();
		return capabilities;
	}

	/**
	 * @param application
	 * @return iOS capabilities
	 * @throws Exception
	 */
	protected DesiredCapabilities generateiOSCapabilities(String application,String udid,String portno) {
	DesiredCapabilities iOScapabilities = new DesiredCapabilities();
		
		if(getPlatform().equals("MPWA")){
			iOScapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iOS");
			iOScapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
			iOScapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			//	capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
			iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.mobilesafari");
			iOScapabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
		}else{
			iOScapabilities.setCapability("deviceName", "iOS");
			iOScapabilities.setCapability("udid", udid);
			iOScapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
			
			
			}if(application.equalsIgnoreCase("gcash")){
				iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.globetel.gcash");
				logger.info("Tapping on GCASH app");
			}

		iOScapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		return iOScapabilities;
	}

	/**
	 * Function to Launch Web Browsers
	 * 
	 * @param browserName
	 * @throws MalformedURLException
	 */
	public synchronized void LaunchBrowserGrid(String browserName) throws MalformedURLException {
		setHandler(new PropertyFileReader("properties/AppPackageActivity.properties"));
		if (browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().browserVersion("0.27.0").setup();
			tlWebDriver.set(new FirefoxDriver());
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-notifications");
			try {
				// DriverManager.setDriver(new RemoteWebDriver(new
				// URL("http://3.7.152.250:4444/"), options));
				DriverManager.setDriver(new RemoteWebDriver(new URL("http://192.168.0.191:4444/"), options));

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (browserName.equalsIgnoreCase("IE")) {
			tlWebDriver.set(new InternetExplorerDriver());
		} else if (browserName.equalsIgnoreCase("MSEdge")) {
			tlWebDriver.set(new EdgeDriver());
		}
		DriverManager.getDriver().get(getURL());
	}

	/**
	 * Function to Launch Web Browsers
	 * 	
	 * @param browserName
	 */
	public void LaunchBrowser(String browserName) {
		setHandler(new PropertyFileReader("properties/AppPackageActivity.properties"));
		if (browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().browserVersion("0.27.0").setup();
			tlWebDriver.set(new FirefoxDriver());
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().browserVersion(getDriverVersion()).setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-gpu");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			tlWebDriver.set(new ChromeDriver(options));
		}else if (browserName.equalsIgnoreCase("ChromeNormal")) {
			WebDriverManager.chromedriver().browserVersion(getDriverVersion()).setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			tlWebDriver.set(new ChromeDriver(options));
		} 
		else if (browserName.equalsIgnoreCase("IE")) {
			tlWebDriver.set(new InternetExplorerDriver());
		}
		else if (browserName.equalsIgnoreCase("MSEdge")) {
			tlWebDriver.set(new EdgeDriver());
		}
		tlWebDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		tlWebDriver.get().get(getURL());
		tlWebDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}


	/**
	 * To Remove the permission of an application
	 * 
	 * @param packagename
	 */
	public static void removePermisson(String packagename) {
		logger.info("****Clearing the App Data****");
		String cmd2 = "adb -s " + getDeviceList() + " shell pm clear " + packagename;
		try {
			Runtime.getRuntime().exec(cmd2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}