package com.guvi.automation.selenium_testing;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guvi.automation.pageObjects.Signup;
import com.guvi.automation.pageObjects.AddContact;
import com.guvi.automation.pageObjects.Signin;

public class MainTest  extends BaseTest{
	   private ExtentReports extent;
	    private ExtentTest test;
	    private String registeredEmail;
	    private String registeredPassword;
	    
	@BeforeTest
	public void initalize() throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String reportName = System.getProperty("user.dir") + "/test-output/ExtentReporter_" + timestamp + ".html";
		// Initialize ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter(reportName);
        extent = new ExtentReports();
        extent.attachReporter(spark);
		
	}
	@BeforeMethod
	public void setupApp() throws IOException {
		System.out.println("Launch Application");
		launchApp();
	}
	
	@AfterMethod
	public void closeDriver() {
		driver.close();
	}
	@AfterTest
	public void teardown() {
		System.out.println("Execution Completed");
		driver.quit();
		  // Flush the ExtentReports
        if (extent != null) {
            extent.flush();
        }
	}
	
	@Test(dataProvider = "getData", priority = 1)
	public void registerUser (HashMap<String, String> map) throws InterruptedException, IOException {
		test = extent.createTest("Register User Test: " + map.get("email"));
		 // store for login test
        this.registeredEmail = map.get("email");
        this.registeredPassword = map.get("password");
        try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		 // Signup and logout	
        Signup signup = new Signup(driver, wait,test);
        test.info("Filling signup form with email and password");
        signup.registerUser(registeredEmail, registeredPassword);
        test.pass("User registration completed successfully");
        
		}catch(Exception e) {
			test.fail("Signup failed: " + e.getMessage());
		}
        
	}
	
	@Test(priority = 2, dependsOnMethods = "registerUser",dataProvider = "getData")
	public void loginUser(HashMap<String, String> map) throws InterruptedException, IOException { 
		test = extent.createTest("Login and AddContact Test for Email : " + registeredEmail);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Signin signin = new Signin(driver, wait,test);
			signin.loginUser(registeredEmail, registeredPassword);
			
			AddContact addContact = new AddContact(driver, wait,test);
			addContact.addNewContact(map);
		}catch(Exception e) {
			test.fail("Login and AddContact Test Failed: " + e.getMessage());
		}
	}
	
	
	@DataProvider
	public Object[][] getData(Method method) throws IOException{
		
		String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/resources/testData.json";
     // Read JSON into List<HashMap<String, String>>
        FileReader reader = new FileReader(filePath);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<List<HashMap<String, String>>>() {}.getType();
        List<HashMap<String, String>> data = gson.fromJson(reader, type);
        reader.close();
        
     // Filter JSON objects matching the current test name
        List<HashMap<String, String>> filtered = new ArrayList<>();
        for (HashMap<String, String> map : data) {
            if (map.get("testName").equalsIgnoreCase(method.getName())) {
                // dynamic email if empty
                if (map.get("email") == null || map.get("email").isEmpty()) {
                    String dynamicEmail = "user_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "@test.com";
                    map.put("email", dynamicEmail);
                }
                filtered.add(map);
            }
        }

        Object[][] returnData = new Object[filtered.size()][1];
        for (int i = 0; i < filtered.size(); i++) {
            returnData[i][0] = filtered.get(i);
        }
        return returnData;
	}
}
