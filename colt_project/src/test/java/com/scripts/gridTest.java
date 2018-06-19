package com.scripts;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class gridTest {

	static WebDriver nodeDriver;
	static WebDriver hubDriver;
	static String nodeUrl;

	@BeforeTest
	public void setup() throws MalformedURLException {
		nodeUrl = "http://192.168.8.43:2317/wd/hub";
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\abhishekbs\\Downloads\\chromedriver.exe");
		capabilities.setBrowserName("chrome");
		capabilities.setPlatform(Platform.WINDOWS);
		nodeDriver = new RemoteWebDriver(new URL(nodeUrl), capabilities);
		hubDriver = new ChromeDriver();
	}

	@Test
	public void simpleTest() {
		nodeDriver.manage().window().maximize();
		nodeDriver.get("https://www.gmail.com");
		hubDriver.manage().window().maximize();
		hubDriver.get("https://www.gmail.com");
		

		
	}

	/*
	 * @AfterTest public void afterTest() { driver.quit(); }
	 */

}
