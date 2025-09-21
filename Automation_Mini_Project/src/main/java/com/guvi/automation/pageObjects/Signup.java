package com.guvi.automation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import appComponents.SeleniumUtils;

public class Signup {
    WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;

    public Signup(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }
    
    @FindBy(id="signup")
    WebElement signup;
    
    @FindBy(id = "firstName")
	WebElement firstName;
    
    @FindBy(id = "lastName")
	WebElement lastName;
    
    @FindBy(id = "email")
	WebElement emailField;

    @FindBy(id = "password")
	WebElement passwordField;
    
    @FindBy(id = "submit")
	WebElement submit;
    
    @FindBy(id = "logout")
	WebElement logout;
    
    public void registerUser(String email, String password) {
    	
    	try {
    	System.out.println("Register User");
    	test.info("Clicking signup button");
		SeleniumUtils.clickElement(signup);
		test.info("Signup form displayed");
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
       
       System.out.println(email );
       System.out.println(password);
       
        SeleniumUtils.slowType(firstName, "Auto", 30);
        test.info("Entered first name");
        SeleniumUtils.slowType(lastName, "Generated", 30);
        test.info("Entered last name");

        SeleniumUtils.slowType(emailField, email, 30);
        test.info("Entered email: " + email);

        SeleniumUtils.slowType(passwordField, password, 30);
        test.info("Entered password");
        SeleniumUtils.clickElement(submit);
        test.info("Clicked submit button");
        wait.until(ExpectedConditions.urlContains("/contactList"));
        test.pass("✅ Signup successful and user logged in automatically");
        System.out.println("✅ Signup successful and user logged in automatically.");
        SeleniumUtils.clickElement(logout);
        
    	}catch(Exception e) {
    		 test.fail("Signup failed: " + e.getMessage());
    	}
    }
}
