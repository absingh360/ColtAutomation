package com.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.util.BasePage;

public class C4CAppPage extends BasePage {

	public C4CAppPage(WebDriver webdriver) {
		super(webdriver);
	}

	@FindBy(id = "username")
	public WebElement username;

	@FindBy(id = "password")
	public WebElement password;

	@FindBy(xpath = "//*[text()='Login']")
	public WebElement logInBtn;
	
	
	@FindBy(id = "__button0-content")
	public WebElement yesPopUp;
	

	@FindBy(xpath = "//*[text()='Customers']")
	public WebElement customers;

	@FindBy(xpath = "//*[text()='Sales']")
	public WebElement sales;

	@FindBy(xpath = "//*[text()='Opportunities']")
	public WebElement opportunities;

	@FindBy(xpath = "//*[text()='Accounts']")
	public WebElement accounts;

	@FindBy(css = "#panevariantm8HSa5dpNqg1z8nY0idosG_9-topRow>div>div>div:nth-child(3)>button>span>span")
	public WebElement myAccount1;
	
	@FindBy(xpath = "//*[@title='Select Variant']")
	public WebElement myAccount;

	@FindBy(xpath = "//button[@title='Create']/span")
	public WebElement addButton;
	
	@FindBy(xpath = "//span[text()='Save']")
	public WebElement saveButton;
	
	@FindBy(xpath = "//button[@title='Edit']")
	public WebElement editButton;
	
	
	
	
	@FindBy(xpath = "//*[text()='All']")
	public WebElement all;

	@FindBy(xpath = "//*[text()='TestJapanOCN']")
	public WebElement testJapanOCN;
	
	@FindBy(xpath = "//*[text()='Peap']")
	public WebElement opportunity_tmp;

	@FindBy(xpath = "(//*[text()='Quotes'])[1]")
	public WebElement quotes;

	@FindBy(xpath = "//*[text()='OVERVIEW']")
	public WebElement overview;
	
	@FindBy(xpath = "//label[text()='Sales Unit']/parent::div/div/div/div/span")
	public WebElement salesUnit;
	
	
	
	@FindBy(xpath = "//label[text()=' / 4']/preceding-sibling::div/following-sibling::span[1]")
	public WebElement salesUnitNext;
	
	@FindBy(xpath = "//label[text()=' / 4']/preceding-sibling::div/following-sibling::span[1]//following-sibling::span")
	public WebElement salesUnitNextPage;
	

	@FindBy(xpath = "//span[text()='Wholesale']")
	public WebElement salesUnitValue;
	
	
	
	
	
	
	
	
	@FindBy(xpath = "//span[text()='More']")
	public List<WebElement> moreButton;

	@FindBy(xpath = "//*[text()='Add']")
	public WebElement add;

	@FindBy(id = "add_product")
	public WebElement addproduct;
	
	@FindBy(name = "username")
	public WebElement usernameCPQ;
	
	@FindBy(id = "psword")
	public WebElement pwdCPQ;
	
	@FindBy(id = "log_in")
	public WebElement loginBtnCPQ;
	
	public static By getLinkElement(String text)
	{
		return By.xpath("//a[text()='"+text+"']");
	}
	
	public static By getElementToAdd(String text)
	{
		return By.xpath("//span[text()='"+text+"']//preceding-sibling::span");
	}
	
	public static By getElementDropDown(String text)
	{
		return By.xpath("//span[text()='"+text+"']//parent::span");
	}
	
	public static By getElementDropDownValue(String text)
	{
		return By.xpath("//li[text()='"+text+"']");
	}
	
	
	public static By getTechincalComplexity(String text)
	{
		return By.xpath("//label[text()='"+text+"']/parent::div/div/div/span[1]");
	}
	
	public static By getGoApproval(String text)
	{
		return By.xpath("//label[text()='"+text+"']/parent::div/div/div/div/div/div[2]/span");
	}
	
	
	
	
	
	
	
	public static By getElementToEnter(String text)
	{
		return By.xpath("//label[text()='"+text+"']//following-sibling::div/div/input");
	}
	

	public void loginInToC4CApplication(String name, String pwd) {
		reportLog("Login to C4C Application");
		username.sendKeys(name);
		reportLog("Enter Username: "+name);
		password.sendKeys(pwd);
		logInBtn.click();
		reportLog("Click on Login Button");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		sleepExecution(10);
		try {
			if(yesPopUp.isDisplayed())
				click(yesPopUp);
				_waitForJStoLoad();
				waitForAjaxRequestsToComplete();
		}
		catch(Exception ex)
		{
			System.out.println("Pop up didn't show up");
		}
		

	}
	
	public void loginInToCPQApplication(String name, String pwd) {
		sendKeys(usernameCPQ, name);
		//reportLog("Enter Username: "+name);
		sendKeys(pwdCPQ, pwd);
		//reportLog("Enter Password: "+pwd);
		loginBtnCPQ.click();
		//reportLog("Click on Login Button");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();

	}

	public void goToOpportunityPage()
	{
		waitAndClick(sales);
		//reportLog("Click on Sales Link");

		waitAndClick(opportunities);
		//reportLog("Click on Opportunity Link");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		sleepExecution(5);
		waitAndClick(myAccount);
		//reportLog("Click on downarrow button");
		_waitForJStoLoad();
		
		waitAndClick(all);
		//reportLog("Select all from dialog");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
	}
	
	public void navigateToHomeTabAndAddNew(String account,String oppName)
	{
	reportLog("Creat a New Opportunity");
	waitAndClick(driver.findElement(getLinkElement("Home")));
	_waitForJStoLoad();
	waitAndClick(addButton);
	waitForAjaxRequestsToComplete();
	clickOn(driver.findElement(getElementToAdd("Opportunity")));
	waitForAjaxRequestsToComplete();
	sleepExecution(3);
	sendKeys(driver.findElement(getElementToEnter("Name")), oppName);
	sleepExecution(2);
	sendKeys(driver.findElement(getElementToEnter("Account")), account);
	sleepExecution(4);
	_waitForJStoLoad();
	pressDownArrowKey();
	pressEnterKey();
	waitForAjaxRequestsToComplete();
	sleepExecution(4);
	/*sendKeys(driver.findElement(getElementToEnter("Contract Length In Months")), "24");*/
	List<WebElement> ele = driver.findElements(getElementDropDown("Arrow Down"));
	ele.get(0).click();
	waitForAjaxRequestsToComplete();
	sleepExecution(2);
	driver.findElement(getElementDropDownValue("New Business")).click();
	waitForAjaxRequestsToComplete();
	ele.get(4).click();
	waitForAjaxRequestsToComplete();
	driver.findElement(getElementDropDownValue("Euro")).click();
	waitForAjaxRequestsToComplete();
	saveButton.click();
	c4cappPage.goToOpportunityPage();
	opportunityPage.searchOpportunity(oppName);
	driver.findElement(getLinkElement(oppName)).click();
	waitForAjaxRequestsToComplete();
	String opportunityName = opportunityPage.getOpportunityName();
	clickOn(moreButton.get(1));
	_waitForJStoLoad();
	clickOn(editButton);
	waitForAjaxRequestsToComplete();
	clickOn(salesUnit);
	_waitForJStoLoad();
	//clickOn(salesUnitNextPage);
	clickOn(salesUnitNext);
	_waitForJStoLoad();
	waitForAjaxRequestsToComplete();
	clickOn(salesUnitValue);
	_waitForJStoLoad();
	driver.findElement(getTechincalComplexity("Legal complexity")).click();
	sleepExecution(2);
	clickOn(driver.findElement(getElementDropDownValue("Standard")));
	_waitForJStoLoad();
	waitForAjaxRequestsToComplete();
	driver.findElement(getTechincalComplexity("Technical complexity")).click();
	sleepExecution(3);
	clickOn(driver.findElement(getElementDropDownValue("1")));
	_waitForJStoLoad();
	waitForAjaxRequestsToComplete();
	driver.findElement(getTechincalComplexity("SPR Required")).click();
	sleepExecution(2);
	clickOn(driver.findElement(getElementDropDownValue("Within DoA and No SPR required")));
	waitForAjaxRequestsToComplete();
	
	click(driver.findElement(getGoApproval("Approved Sales")));
	waitForAjaxRequestsToComplete();
	
	click(driver.findElement(getGoApproval("Approved Solution")));
	waitForAjaxRequestsToComplete();
	
	click(driver.findElement(getGoApproval("Approved Commercial")));
	waitForAjaxRequestsToComplete();
	
	List<WebElement> save = driver.findElements(By.xpath("//span[text()='Save']"));
 	clickOn(save.get(1));
	
	waitForAjaxRequestsToComplete();
	sleepExecution(3);
	System.out.println(opportunityPage.getOpportunityName());
	sleepExecution(3);
	}
	
	public void navigateFromSalesToProduct() throws Exception {
		goToOpportunityPage();

		waitAndClick(opportunity_tmp);
		reportLog("Select opportunity_tmp from account list");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();

		quotes.click();
		reportLog("Click on Quote tab");
		_waitForJStoLoad();

		waitAndClick(add);
		reportLog("Click on Add button");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		switchWindow("Transaction");
		reportLog("Switching on CQP tab");
		sleepExecution(2);
	}
	
	public void verifySalesMenuSubLinks(String menu, String[] sublinks) {
		String locator = "//*[text()='" + menu + "']/../following-sibling::div//a";
		String tmp = null;
		int count = getXpathCount(locator);
		int expectedCount = sublinks.length;

		Assert.assertTrue((count == expectedCount),
				"Number of Sublink present in the menu are not equal <br/> Expected: " + expectedCount + "+ Actual: "
						+ count);
		for (int i = 1; i < expectedCount; i++) {
			tmp = getText("//*[text()='" + menu + "']/../following-sibling::div/div[" + i + "]/a");
			Assert.assertTrue(tmp.equals(sublinks[i]),
					"Expected Sublink: " + sublinks[i] + "<br />Actual Sublink: " + tmp + " is not present/equal");
		}

	}
}
