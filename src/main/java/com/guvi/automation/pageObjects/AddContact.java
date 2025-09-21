package com.guvi.automation.pageObjects;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import appComponents.SeleniumUtils;

public class AddContact {
    WebDriver driver;
    WebDriverWait wait;
    ExtentTest test;

    public AddContact(WebDriver driver, WebDriverWait wait,ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "lastName")
	WebElement lastname;
    
    @FindBy(id = "birthdate")
	WebElement birthdate;
    
    @FindBy(id = "email")
	WebElement email;
    
    @FindBy(id = "phone")
	WebElement phone;
   
    @FindBy(id = "street1")
	WebElement street;
   
    @FindBy(id = "city")
	WebElement city;
    
    @FindBy(id = "stateProvince")
    WebElement stateProvince;
    
    @FindBy(id = "postalCode")
	WebElement postalCode;
    
    @FindBy(id = "country")
	WebElement country;
    
    @FindBy(id = "submit")
	WebElement submit;

    
    public void addNewContact(HashMap<String, String> map) {
    	try {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add a New Contact']"))).click();
        wait.until(ExpectedConditions.urlContains("/addContact"));
        System.out.println("✅ Navigated to Add Contact page.");

        SeleniumUtils.slowType(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName"))), map.get("firstName"));
        SeleniumUtils.slowType(lastname, map.get("lastName"));
        SeleniumUtils.slowType(birthdate, map.get("birthdate"));
        SeleniumUtils.slowType(email, map.get("email"));
        SeleniumUtils.slowType(phone, map.get("phone"));
        SeleniumUtils.slowType(street, map.get("street"));
        SeleniumUtils.slowType(city, map.get("city"));
        SeleniumUtils.slowType(stateProvince, map.get("state"));
        SeleniumUtils.slowType(postalCode, map.get("postalCode"));
        SeleniumUtils.slowType(country, map.get("country"));
        SeleniumUtils.clickElement(submit);
        test.info("Contact added successfully");
        System.out.println("✅ Contact added successfully.");
    	}catch(Exception e) {
      		 test.fail("AddNewContact failed: " + e.getMessage());
      	}
    }
}
