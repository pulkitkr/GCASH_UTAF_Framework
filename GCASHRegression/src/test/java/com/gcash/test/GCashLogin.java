package com.gcash.test;



import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.business.gcash.GCashBusinessLogic;
import com.deviceDetails.DeviceDetails;
import com.utility.ExtentReporter;
import com.utility.Utilities;

public class GCashLogin {

    public static String deviceName;
    public static String portno;
    public  static com.business.gcash.GCashBusinessLogic GCashBusinessLogic;


    //@BeforeSuite(groups = { "All" })
    @Parameters({"deviceName","portno"})
    @BeforeClass
    public void before(String deviceName,String portno) throws Exception {
        GCashLogin.deviceName=deviceName;
        GCashLogin.portno= portno;
        GCashBusinessLogic = new GCashBusinessLogic("gcash",deviceName,portno);
    }

    
    //===================================buy eload=======================================
    
    @Test(priority = 0)
	public void appLaunch() throws Exception {
    	GCashBusinessLogic.GGivesAppLaunch();
   	
	}
//    @Test(priority = 1)
//    public void appLaunch1() throws Exception {
//    	GCashBusinessLogic.loginToGCash("9999999999");
//	}
//  
//    
//    @Test(priority = 2)
//    public void gSvaeHome() throws Exception {
//    	GCashBusinessLogic.GsaveLogin("9010000143","000000");
//    }
//    
}
