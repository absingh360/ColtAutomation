package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.util.BasePage;

public class Product_List_Page extends BasePage {

	public Product_List_Page(WebDriver webdriver) {
		super(webdriver);

	}

	@FindBy(xpath = "//a[contains(text(),'Oracle')]")
	public WebElement oracleQuoteToOrderManagerLink;

	@FindBy(xpath = "//a[text()='Ethernet']")
	public WebElement ethernetButton;

	@FindBy(xpath = "//a[text()='Ethernet Line']")
	public WebElement ethernetLineLink;

	@FindBy(xpath = "//a[text()='Ethernet Hub']")
	public WebElement ethernetHubLink;
	
	@FindBy(xpath = "//a[text()='Ethernet Spoke']")
	public WebElement ethernetSpokeLink;
	
	public static By getLinkButton(String text) {
		return By.xpath("//a[text()='" + text + "']");
	}

	public void AddproductType(String type) throws InterruptedException {

		waitForAjaxRequestsToComplete();
		transactionPage.clickOnAddProductBtn();
		reportLog("Click on to AddProduct button");
		_waitForJStoLoad();
		ethernetButton.click();
		reportLog("Mouse move to EtherNet link");

		if (type.equals("Ethernet")) {
			ethernetLineLink.click();
			reportLog("Click on to EnternetLine Product");

		}
		else if (type.equals("Hub")) {
			waitAndClick(ethernetHubLink);
			//ethernetHubLink.click();
			reportLog("Click on to Enternet Hub Product");
		}
		else if(type.equals("Spoke"))
		{
			waitAndClick(ethernetSpokeLink);
			//ethernetSpokeLink.click();
			reportLog("Click on to Enternet Spoke Product");
		}
		else if(type.equals("Wave"))
		{
			hoverOverElementAndClick(driver.findElement(getLinkButton("Optical")), driver.findElement(getLinkButton("Wave")));
			reportLog("Click on to Optical Wave Product");
			
		}
		
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		verifyTitle("Model Configuration");
		reportLog("Verifying the title 'Model Configuration'");

	}

	public void clickOnOrderManagerLink() {
		javascriptScrollIntoView(oracleQuoteToOrderManagerLink);
		// waitForElementVisible(oracleQuoteToOrderManagerLink);
		waitAndClick(oracleQuoteToOrderManagerLink);
		_waitForJStoLoad();
		reportLog("Click on Order Manager Link");
	}
}
