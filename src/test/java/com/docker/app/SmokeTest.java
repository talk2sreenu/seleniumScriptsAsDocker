package com.docker.app;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.PO.PageObjects;

public class SmokeTest 
{
	public static WebDriver driver;
	
    @BeforeMethod
	public void beforeTest() throws MalformedURLException {
		System.out.println("********** Initializing the chrome driver **************");
		DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName("chrome");
        String osName = System.getProperty("os.name");
        System.out.println("Running in the : "+osName+" operating system");
      
        if(osName.toLowerCase().equals("linux"))
        {
        	System.setProperty("webdriver.chrome.driver", "/app/bin/chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--window-size=1920,1080");
        	driver = new ChromeDriver(chromeOptions);
        	
        }
        else {
        	driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
        	driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	driver.manage().deleteAllCookies();
	}
    
    @Test
    public void googleTest() throws InterruptedException
    {
        System.out.println("Starting the actual test");
        PageObjects po = new PageObjects(driver);
        po.verifyGooglePage();
    }
    
    @Test
    public void verifyResturants() throws InterruptedException
    {
    	PageObjects po = new PageObjects(driver);
        po.verifyGoogleSecondTime();
    }
    
    @AfterMethod
    public void afterTest() {
    	System.out.println("Closing out all browsers");
    	driver.close();
    }
}
