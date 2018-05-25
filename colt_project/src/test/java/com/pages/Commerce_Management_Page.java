package com.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.util.BasePage;

public class Commerce_Management_Page extends BasePage {

	public Commerce_Management_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "new_transaction")
	public WebElement newTransactionButton;

	@FindBy(id = "copy")
	public WebElement copyButton;

	@FindBy(xpath = "//img[@title='Home']")
	public WebElement homePageButton;

	@FindBy(id = "refresh")
	public WebElement refreshButton;

	@FindBy(id = "search")
	public WebElement searchButton;
	
	@FindBy(xpath = "//td[@title='[Default]']/preceding-sibling::td/a")
	public WebElement defaultView;
	

	@FindBy(xpath = "//div[@class='tabular-data-container']//table//tr")
	public List<WebElement> quoteRows;
	
	

	@FindBy(xpath = "//div[@class='tabular-data-container']//table//tr//td[3]//div//span")
	public List<WebElement> columnValue;

	public static By getQuoteNameFromQuoteTable(int i) {
		return By.xpath("//div[@class='tabular-data-container']//table//tr[" + i + "]//td[3]");
	}

	public static By getQuoteNameFromQuoteTableApproverView(int i) {
		return By.xpath("//div[@class='tabular-data-container']//table//tr[" + i + "]//td[4]");
	}
	
	public static By getQuoteStatusFromQuoteTable(int i) {
		return By.xpath("//div[@class='tabular-data-container']//table//tr[" + i + "]//td[4]/div/span");
	}
	
	public static By getQuoteStatusFromQuoteTableApproverView(int i) {
		return By.xpath("//div[@class='tabular-data-container']//table//tr[" + i + "]//td[8]/div/span");
	}

	@FindBy(xpath = "(//*[text()='Select'])[2]")
	public WebElement selectButton;
	
	public void clickOnAddTransactionButton() {
		waitAndClick(newTransactionButton);
		_waitForJStoLoad();
	}

	public void openQuoteForReview(String quoteName) {
		
		int rowCount = quoteRows.size();

		for (int i = 2; i < rowCount; i++) {
			String value = driver.findElement(getQuoteNameFromQuoteTable(i)).getText();
			if (value.equals(quoteName)) {
				driver.findElement(By.xpath("//div[@class='tabular-data-container']//table//tr["+i+"]//td[2]"))
						.click();
				break;
			}
		}
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();

	}
	
public void openQuoteForReviewApproverView(String quoteName) {
		
		int rowCount = quoteRows.size();

		for (int i = 2; i < rowCount; i++) {
			String value = driver.findElement(getQuoteNameFromQuoteTableApproverView(i)).getText();
			if (value.equals(quoteName)) {
				driver.findElement(By.xpath("//div[@class='tabular-data-container']//table//tr["+i+"]//td[3]"))
						.click();
				break;
			}
		}
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();

	}

	public void clickOnDefaultView()
	{
		javascriptButtonClick(defaultView);
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		
	}
	
	public void verifyQuoteStatusInApproverView(String quoteName, String status)
	{
		int rowCount = quoteRows.size();
		String text;
		String value;
		for (int i = 2; i < rowCount; i++) {
			reportLog("test");
			value = driver.findElement(getQuoteNameFromQuoteTableApproverView(i)).getText();			
			if (value.equals(quoteName)) {
				text = driver.findElement(getQuoteStatusFromQuoteTableApproverView(i)).getText();
				Assert.assertTrue(text.equals(status),
						"Quote named [" + quoteName + "] status is Not as Expected " + text + "");
				reportLog("Quote named [" + quoteName + "] status is " + status + "");
				break;
			}
		}

	}
	
	public void verifyQuoteStatus(String quoteName, String status) {
		int rowCount = quoteRows.size();
		String text;
		String value;
		for (int i = 2; i < rowCount; i++) {
			reportLog("test");
			value = driver.findElement(getQuoteNameFromQuoteTable(i)).getText();			
			if (value.equals(quoteName)) {
				text = driver.findElement(getQuoteStatusFromQuoteTable(i)).getText();
				Assert.assertTrue(text.equals(status),
						"Quote named [" + quoteName + "] status is Not as Expected " + text + "");
				reportLog("Quote named [" + quoteName + "] status is " + status + "");
				break;
			}
		}
	}

}
