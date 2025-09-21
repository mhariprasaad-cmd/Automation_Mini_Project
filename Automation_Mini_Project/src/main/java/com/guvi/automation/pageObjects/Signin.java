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

public class Signin {
    WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;

    public Signin(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="email")
    WebElement emailField;
    
    @FindBy(id="password")
    WebElement passwordField;
    
    @FindBy(id="submit")
    WebElement submit;
    

    public void loginUser(String email, String password) { 
    	try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        SeleniumUtils.slowType(emailField, email, 30); 
        test.info("Entered emailField");
        SeleniumUtils.slowType(passwordField, password, 30);
        test.info("Entered passwordField");
        SeleniumUtils.clickElement(submit);
        test.info("Clicked submit button");
        wait.until(ExpectedConditions.urlContains("/contactList"));
        System.out.println("✅ Login successful.");
    	}catch(Exception e) {
   		 test.fail("SignIn failed: " + e.getMessage());
   	}
    }
}
