package com.PO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageObjects {
	
	public WebDriver driver;
	
	public PageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	@FindBy(xpath="//a[contains(.,'How Search works')]")
	public static WebElement lnkHowItWorks;
	
	@FindBy(xpath="//a[.='Business']")
	public static WebElement lnkBusiness;
	
	@FindBy(xpath="(//a[.='How It Works'])[1]")
	public static WebElement lnkRestaurantSpecials;
	
	
	
	public void verifyGooglePage() {
		driver.get("https://www.google.com");
		wait(5000);
        lnkHowItWorks.click();
        wait(5000);
        String url = driver.getCurrentUrl();
        System.out.println("The URL is : "+url);
	}
	
	public void verifyRestaurantPage() {
		driver.get("https://www.restaurant.com/");
        wait(5000);
        lnkRestaurantSpecials.click();
        wait(5000);
        String url = driver.getCurrentUrl();
        System.out.println("The URL is : "+url);
	}
	
	public void verifyGoogleSecondTime() {
		driver.get("https://www.google.com");
		wait(5000);
		lnkBusiness.click();
        wait(5000);
        String url = driver.getCurrentUrl();
        System.out.println("The URL is : "+url);
	}
	public void wait(int duration) {
		try {
			Thread.sleep(duration);
		}catch(Exception e) {
			
		}
	}
}
