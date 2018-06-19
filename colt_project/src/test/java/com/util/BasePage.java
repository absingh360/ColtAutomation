package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public abstract class BasePage extends DriverTestCase {
	// Define objects
	protected WebDriver driver;

	protected SoftAssert softAssert = new SoftAssert();

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	// Return web driver object
	public WebDriver getWebDriver() {
		return driver;
	}

	// Print message on screen
	public void log(String logMsg) {
		System.out.println(logMsg);
	}

	// Handle locator type
	public By byLocator(String locator) {
		By result = null;

		if (locator.startsWith("//")) {
			result = By.xpath(locator);
		} else if (locator.startsWith("css=")) {
			result = By.cssSelector(locator.replace("css=", ""));
		} else if (locator.startsWith("#")) {
			result = By.name(locator.replace("#", ""));
		} else if (locator.startsWith("link=")) {
			result = By.linkText(locator.replace("link=", ""));
		} else {
			result = By.id(locator.replace("id=", ""));
		}
		return result;
	}

	public void scrollDownToBottom() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	// Assert element present
	public Boolean isElementPresent(String locator) {
		Boolean result = false;
		try {
			// getWebDriver().findElement(byLocator(locator));
			fluentWait(locator);
			result = true;
		} catch (Exception ex) {
		}
		return result;
	}

	public Boolean isElementPresent(By locator) {
		Boolean result = false;
		try {
			// getWebDriver().findElement(locator);
			fluentWait(locator);
			result = true;
		} catch (Exception ex) {
		}
		return result;
	}

	// Wait for element present
	public void waitForElementPresent(String locator, int timeout) {
		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator)) {
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Click action performed and then wait
	 */
	public void waitAndClick(WebElement element) {
		waitForElement(element);
		element.click();
	}

	public void acceptPopup()
	{
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Accepted the alert successfully.");
		} catch (Throwable e) {
			System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
		}
	}
	
	// Handle click action
	public void clickOn(String locator) {
		// this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.click();
	}

	public void clickOn(By locator) {
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(locator);
		el.click();
	}

	public void clickOn(WebElement locator) {
		// this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		locator.click();
	}
	
	
	public void waitAndClickOn(WebElement locator) {
		 this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		locator.click();
	}

	// Click on button
	public void click(WebElement locator) {
		try {
			clickOn(locator);
			_waitForJStoLoad();
		} catch (Exception e) {
			Assert.fail("Locator: " + locator + "is not clickable due to </br> " + e.getMessage());
			// reportLog(e.getMessage());
		}
	}

	// Handle send keys action
	public void sendKeys(String locator, String str) {
		// this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.clear();
		el.sendKeys(str);
	}

	// Handle send keys action
	public void sendKeys(WebElement locator, String str) {
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		locator.clear();
		locator.sendKeys(str);
	}

	// Store text from a locator
	public String getText(String locator) {
		// waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		String text = getWebDriver().findElement(byLocator(locator)).getText();
		return text;
	}

	// Get attribute value
	public String getAttribute(String locator, String attribute) {
		// waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		String text = getWebDriver().findElement(byLocator(locator)).getAttribute(attribute);
		return text;
	}

	// Get attribute value
	public String getAttribute(WebElement locator, String attribute) {
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		String text = locator.getAttribute(attribute);
		return text;
	}

	public Integer getXpathCount(String locator) {
		// waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		int a = driver.findElements(By.xpath(locator)).size();
		return a;
	}

	public void waitForWorkAroundTime(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void verifyTitle(String title) {
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		sleepExecution(5);
		String actualTitle = getWebDriver().getTitle();
		Assert.assertTrue(actualTitle.contains(title));
		// reportLog("Expected Title: " + title + "" + " <br /> Actual Title: " +
		// actualTitle);
	}

	public void mouseOver(String locator) {
		this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		Actions action = new Actions(getWebDriver());
		action.moveToElement(el).build().perform();
	}

	public void selectByText(String locator, String text) {
		this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		Select select = new Select(el);
		select.selectByVisibleText(text);
	}

	/**
	 * Select drop down by value
	 * 
	 * @author himanshud
	 * @param locator
	 * @param value
	 */
	public void selectByValue(String locator, String value) {
		this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		Select select = new Select(el);
		select.selectByValue(value);
	}

	public void typeKeys(String locator, Keys key) {
		this.waitForElementPresent(locator, DEFAULT_WAIT_4_ELEMENT);
		Assert.assertTrue(isElementPresent(locator), "Element Locator :" + locator + " Not found");
		WebElement el = getWebDriver().findElement(byLocator(locator));
		el.sendKeys(key);
	}

	public void pressTabKey() {
		Actions builder = new Actions(getWebDriver());
		builder.sendKeys(Keys.TAB).perform();
	}

	public void pressEnterKey() {
		Actions builder = new Actions(getWebDriver());
		builder.sendKeys(Keys.ENTER).perform();
	}

	public void pressDownArrowKey() {
		Actions builder = new Actions(getWebDriver());
		builder.sendKeys(Keys.ARROW_DOWN).perform();
	}

	public void pressUpArrowKey() {
		Actions builder = new Actions(getWebDriver());
		builder.sendKeys(Keys.ARROW_UP).perform();
	}

	public void waitForElement(String locator) {
		// logger.info("waitForElement");
		WebDriverWait wait = new WebDriverWait(getWebDriver(), DEFAULT_WAIT_4_ELEMENT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator(locator)));
	}

	public void javascriptButtonClick(String locator) {
		WebElement element = getWebDriver().findElement(byLocator(locator));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void javascriptButtonClick(WebElement locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", locator);
		_waitForJStoLoad();
	}
	
	public void javascriptClick(WebElement locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", locator);
	}

	public void javascriptSendKeys(String locator, String data) {
		WebElement element = getWebDriver().findElement(byLocator(locator));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='" + data + "'", element);
	}

	public void javascriptSendKeys(WebElement locator, String data) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value='" + data + "'", locator);
	}

	public String timestamp() {
	    return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}
	
	
	public void screenShot() throws IOException, InterruptedException
	{
	    File scr=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

	    File dest= new File("D://MyWorkRepo//MyTest//MyTest//Web//colt_project_swapnil//screenshots//"+timestamp()+".png");
	    FileUtils.copyFile(scr, dest);
	}
	
	public void alertOK() throws IOException, InterruptedException {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			waitForAjaxRequestsToComplete();
			scrollDownToBottom();
			screenShot();

		} catch (NoAlertPresentException e) {
			System.out.println("No alert is present. Please check.");
		}
		waitForAjaxRequestsToComplete();
	}
	
	public void javascriptScrollIntoView(String locator) {
		WebElement element = getWebDriver().findElement(byLocator(locator));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
	}

	public void javascriptScrollIntoView(WebElement locator) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", locator);
	}

	public WebElement fluentWait(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

		return foo;
	};

	public WebElement fluentWait(final String locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(byLocator(locator));
			}
		});

		return foo;
	};

	public WebElement SafeFindElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_4_ELEMENT);

		// wait.IgnoreExceptionTypes(typeof());
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (TimeoutException e) {
			String error = "Unable to locate element: {\"method\":\"" + locator.getClass() + "\",\"selector\":\""
					+ locator.getClass() + "\"}";
			reportLog(e.getMessage());
		}
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			reportLog(e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
	}

	public void waitForElement(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_4_ELEMENT);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			reportLog(element.toString() + " is not present on page or not clickable");
		}

	}

	public void waitForElementVisible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_4_ELEMENT);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

	public void sleepExecution(int sec) {
		sec = sec * 1000;
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean _waitForJStoLoad() {
		// wait for jQuery to load
		sleepExecution(3);
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for JavaScript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				Object rsltJs = ((JavascriptExecutor) driver).executeScript("return document.readyState");
				if (rsltJs == null) {
					rsltJs = "";
				}
				return rsltJs.toString().equals("complete") || rsltJs.toString().equals("loaded");
			}
		};

		WebDriverWait wait = new WebDriverWait(getWebDriver(), DEFAULT_WAIT_4_PAGE);
		boolean waitDone = wait.until(jQueryLoad) && wait.until(jsLoad);
		return waitDone;
	}

	public Boolean isElementPresent(WebElement element) {
		try {
			waitForElementVisible(element);
			element.isDisplayed();
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public Boolean isElementDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public void verifyElementPresent(WebElement element) {
		Assert.assertTrue(isElementPresent(element), element.toString() + " is not present");
	}

	public void verifyElementNotPresent(WebElement element) {
		_waitForJStoLoad();
		Assert.assertFalse(isElementPresent(element), element.toString() + " is present");
	}

	public void verifyElementText(WebElement element, String text) {
		_waitForJStoLoad();
		Assert.assertTrue(isElementPresent(element), element.toString() + " is not present");
		Assert.assertEquals(element.getText(), text);
	}

	public void verifyElementValue(WebElement element, String text) {
		_waitForJStoLoad();
		Assert.assertTrue(isElementPresent(element), element.toString() + " is not present");
		Assert.assertEquals(element.getAttribute("value"), text);
	}

	public WebDriver hoverOverElementAndClick(WebElement toBeHovered, WebElement toBeClicked) {
		Actions builder = new Actions(driver);
		builder.moveToElement(toBeHovered).build().perform();
		waitForElementPresent(toBeClicked, DEFAULT_WAIT_4_ELEMENT);
		toBeClicked.click();
		_waitForJStoLoad();
		return driver;
	}

	public WebElement waitForElementPresent(WebElement webElement, int timeOutInSeconds) {
		WebElement element;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.visibilityOf(webElement));
			return element;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public WebDriver mouseClick(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).perform();
		return driver;
	}

	/*
	 * Select element by visible text
	 * 
	 * @Param element
	 * 
	 * @Patram targetValue: visible text
	 */
	public void selectDropDownByText(WebElement element, String targetValue) {
		waitForElement(element);
		String tmp = new Select(element).getFirstSelectedOption().getText();
		if (!(tmp.equals(targetValue)))
			new Select(element).selectByVisibleText(targetValue);
	}

	/*
	 * Select element by Index
	 * 
	 * @Param element
	 * 
	 * @Patram index
	 */
	public void selectDropDownByIndex(WebElement element, int index) {
		waitForElement(element);
		new Select(element).selectByIndex(index);
	}

	/*
	 * Select element by value
	 * 
	 * @Param element
	 * 
	 * @Patram targetValue: value
	 */
	public void selectDropDownByValue(WebElement element, String targetValue) {
		waitForElement(element);
		new Select(element).selectByValue(targetValue);
	}

	public void switchWindow(String title) {
		//waitForAjaxRequestsToComplete();
		for (String windowhandle : getWebDriver().getWindowHandles()) {
			getWebDriver().switchTo().window(windowhandle);
			if (getWebDriver().getTitle().contains(title)) {
				break;
			}
		}
		sleepExecution(5);
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
	}

	public WebDriver doubleClick(WebElement element) {
		Actions builder = new Actions(driver);
		builder.doubleClick(element).build().perform();
		return driver;
	}

	public void logOutFromCPQ() {
		javascriptButtonClick(transactionPage.logOutBtn);
		reportLog("Log out from the application");
	}

	public String removeDecimalValues(String data) {

		data = data.indexOf(".") < 0 ? data : data.replaceAll("0*$", "").replaceAll("\\.$", "");
		return data;
	}
	
	//*[@id="chargesServiceLevel"]/table/tbody/tr[2]/td[3]
	public void copydata(WebElement locator) throws IOException
	{
	 
		String text = driver.findElement((By) locator).getText().toString();
		//String text1 = driver.findElement((By) locator1).getText();
		FileWriter fr=new FileWriter("C:/Users/himanshud/Desktopcoltpad.txt");
		BufferedWriter br=new BufferedWriter(fr);

		br.write(text);
		//br.write(text1);
		br.newLine();
		br.close();
		
	}
	
	
}