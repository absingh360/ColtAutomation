package com.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.Assert;
import com.util.BasePage;

public class OpportunitiesPage extends BasePage {

	public OpportunitiesPage(WebDriver webdriver) {
		super(webdriver);
	}

	@FindBy(xpath = "//span[contains(@id,'searchButton-inner')]")
	public WebElement opportunitySearchBtn;

	@FindBy(xpath = "//input[contains(@id,'searchField')]")
	public WebElement opportunityInputBox;

	@FindBy(xpath = "(//*[text()='Quotes'])[1]")
	public WebElement quotes;
	
	@FindBy(xpath = "//span[@title='Name']")
	public WebElement opportunityName;
	

	@FindBy(xpath = "//span[@class='sapMTabStripItemLabel' and text()='Quotes']")
	public WebElement quotesTab;

	@FindBy(xpath = "//*[text()='Add']")
	public WebElement add;

	@FindBys(@FindBy(xpath = "//table[contains(@id,'listdefintion')]/tbody/tr"))
	public List<WebElement> table;

	public static By getParticularOpportunity(int opportunityId) {
		return By.xpath("//span[@title='" + opportunityId + "']/../../following-sibling::td[1]//a");
	}
	
	/*public static By getParticularOpportunity(int opportunityId) {
		return By.xpath("//a[@title='Opp for Existing Customer']");
	}*/
	
	

	public static By getAccountNameOfOpportunity(int opportunityId) {
		return By.xpath("//span[@title='" + opportunityId + "']/../../following-sibling::td[2]//a");
	}

	public void searchOpportunity(String name) {
		waitAndClick(opportunitySearchBtn);
		sendKeys(opportunityInputBox, name);
		pressEnterKey();
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
	}
	
	public String getOpportunityName()
	{
		_waitForJStoLoad();
		String name = opportunityName.getText();
		return name;
		
	}

	public void verifyDataInOpportunityTable() {
		int count = table.size();
		Assert.assertTrue((count > 1), "No data is present in table!");
	}

	public void selectParticularOpportunity(int opportunityId) {
		waitAndClick(getWebDriver().findElement(getParticularOpportunity(opportunityId)));
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
	}

	public String getNameOfOpportunity(int opportunityId) {
		return getAttribute(getWebDriver().findElement(getParticularOpportunity(opportunityId)), "title");
	}

	public String getAccountName(int opportunityId) {
		return getAttribute(getWebDriver().findElement(getAccountNameOfOpportunity(opportunityId)), "title");
	}

	public void addNewQuoteFromOpportunity() {
		sleepExecution(5);
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		waitAndClick(quotesTab);
		// reportLog("Click on Quote tab");
		_waitForJStoLoad();

		waitAndClick(add);
		// reportLog("Click on Add button");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
	}

}
