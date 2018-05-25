package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.automationtesting.excelreport.Xl;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.codoid.products.fillo.Connection;
//import com.common.excelreport.ExcelReportGenerator;
import com.constants.GlobalConstant.FileNames;
import com.pages.C4CAppPage;
import com.pages.OpportunitiesPage;
import com.pages.Commerce_Management_Page;
import com.pages.Model_Configuration_Page;
import com.pages.Product_List_Page;
import com.pages.Transaction_Page;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class DriverTestCase {

	public static ExtentTest test;
	public static ExtentReports extent;
	protected static final int DEFAULT_WAIT_4_ELEMENT = 7;
	protected static final int DEFAULT_WAIT_4_PAGE = 40;
	protected static WebDriverWait ajaxWait;
	protected long timeout = 60;
	
	// pages object initialization
	public static OpportunitiesPage opportunityPage;
	public static Connection listPriceConnection;
	protected static Commerce_Management_Page commerceManagementPage;
	protected static Model_Configuration_Page modelConfigurationPage;
	protected static Product_List_Page productListPage;
	protected static Transaction_Page transactionPage;
	public static C4CAppPage c4cappPage;
	public static FileInputStream inputExcel;
	public static FileOutputStream outputExcel;
	public static String reportExcelPath = "\\src\\test\\resources\\ExcelReport\\JavascriptDataUpdated.xlsx";

	private static final Logger logger = LoggerFactory.getLogger(DriverTestCase.class);

	// Define objects
	protected WebDriver driver;
	// Initialize objects
	protected PropertyReader configReader = new PropertyReader(
			FileNames.TestDataRelativePath.toString() + FileNames.CONFIG.toString());
	protected PropertyReader c4cpropertyReader = new PropertyReader(FileNames.C4C_TEST_DATA.toString());
	protected PropertyReader cpqpropertyReader = new PropertyReader(FileNames.CPQ_TEST_DATA.toString());
	// Define variables
	protected String application_url = configReader.readApplicationFile("ApplicationURL");
	protected String stackValue = configReader.readApplicationFile("StackValue");
	protected String c4c_url = configReader.readApplicationFile("C4C_URL");
	protected String c4c_testurl = configReader.readApplicationFile("C4C_TestURL");
	protected String username = configReader.readApplicationFile("Username");
	protected String password = configReader.readApplicationFile("Password");
	protected String c4c_userName = configReader.readApplicationFile("C4C_Username");
	protected String c4c_Password = configReader.readApplicationFile("C4C_Password");
	protected String deal_user = configReader.readApplicationFile("DealPriceUser");
	protected String deal_user_password = configReader.readApplicationFile("DealPriceUserPassword");
	protected String salesapprover_user = configReader.readApplicationFile("SalesApproverUser");
	protected String salesapprover_password = configReader.readApplicationFile("SalesApproverPassword");
	protected String sales_user = configReader.readApplicationFile("SalesUser");
	protected String salesuser_pass = configReader.readApplicationFile("SalesUserPass");
	protected String financeapprover_user = configReader.readApplicationFile("FinanaceApproverUser");
	protected String financeapprover_password = configReader.readApplicationFile("FinanaceApproverPassword");
	protected String financeapprover_user1 = configReader.readApplicationFile("FinanaceApproverUser1");
	protected String financeapprover_password1 = configReader.readApplicationFile("FinanaceApproverPassword1");
	protected String cpq_url = configReader.readApplicationFile("ApplicationURL");

	enum DriverType {
		Firefox, IE, Chrome, HtmlUnitDriver
	}

	public enum BuildingType {
		RB_RB, RB_DC_K, RB_DC_S, DC_K_DC_K, DC_S_DC_S, DC_S_DC_K, RB, DC_C, DC_S
	}

	public enum AddOns {
		outsideBHI_Site_A, outsideBHI_Site_B, dual_Entry_Site_A, dual_Entry_Site_B, longLining_A, longLining_B, ic_Site_A, ic_Site_B, lag_Site_A, lag_Site_B, diversity, cos, pr, pam, fastTrack, sync
	}

	public enum CurrencyType {
		EUR, GBP, USD, DKK, CHF, SEK, SDG, SDR, INR, NOK, JPY, PLN, RON, HKD, AUD, CAD, CNY, KRW, CZK
	}

	public enum DiscountType {
		PERCENTAGE, AMOUNT, TARGET
	}

	@BeforeSuite
	public void before() {
		extent = new ExtentReports("target\\surefire-reports\\ExtentReport.html", true);
	}

	// @BeforeMethod
	public void setUp() throws FileNotFoundException {
		/*
		 * test = extent.startTest(this.getClass().getSimpleName(),
		 * Method.class.getName()); test.assignAuthor("360Logica");
		 * test.assignCategory(this.getClass().getSimpleName());
		 */
		String driverType = configReader.readApplicationFile("BROWSER");

		if (DriverType.Firefox.toString().equals(driverType)) {
			String fireFoxDriverPath = getPath() + File.separator + "drivers" + File.separator
					+ "geckodriver_64Bit.exe";
			System.setProperty("webdriver.gecko.driver", fireFoxDriverPath);
			driver = new FirefoxDriver();
		}

		else if (DriverType.IE.toString().equals(driverType)) {
			String path1 = getPath();
			File file = new File(path1 + File.separator + "IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			driver = new InternetExplorerDriver(capabilities);
		} else if (DriverType.Chrome.toString().equals(driverType)) {
			String chromeDriverPath = getPath() + File.separator + "drivers" + File.separator + "chromedriver.exe";

			// Set the required properties to instantiate Chrome driver. Place
			// any latest Chromedriver.exe files under Drivers folder
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("disable-infobars");
			//options.addArguments("--headless");

			driver = new ChromeDriver(options);
		} 
		
		else if(DriverType.HtmlUnitDriver.toString().equals(driverType))
		{
			driver =  new HtmlUnitDriver();
		}
			else {
			String fireFoxDriverPath = getPath() + File.separator + "drivers" + File.separator
					+ "geckodriver_32Bit.exe";
			System.setProperty("webdriver.gecko.driver", fireFoxDriverPath);
			driver = new FirefoxDriver();
		}

		// Maximize window
		// driver.manage().window().maximize();

		// Delete cookies
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_ELEMENT, TimeUnit.SECONDS);
		opportunityPage = PageFactory.initElements(getWebDriver(), OpportunitiesPage.class);
		modelConfigurationPage = PageFactory.initElements(getWebDriver(), Model_Configuration_Page.class);
		productListPage = PageFactory.initElements(getWebDriver(), Product_List_Page.class);
		transactionPage = PageFactory.initElements(getWebDriver(), Transaction_Page.class);
		commerceManagementPage = PageFactory.initElements(getWebDriver(), Commerce_Management_Page.class);
		c4cappPage = PageFactory.initElements(getWebDriver(), C4CAppPage.class);
		inputExcel = new FileInputStream(new File(System.getProperty("user.dir") + "\\ExcelFile\\JavascriptData.xlsx"));
		outputExcel = new FileOutputStream(new File(System.getProperty("user.dir") + reportExcelPath));

	}

	/* capturing screenshot */
	public void captureScreenshot(ITestResult result) throws IOException, InterruptedException {
		try {
			String screenshotName = Utilities.getFileName(result.getName());
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = Utilities.getPath();
			String screen = path + "/screenshots/" + screenshotName + ".png";
			File screenshotLocation = new File(screen);
			FileUtils.copyFile(screenshot, screenshotLocation);
			Thread.sleep(2000);
			InputStream is = new FileInputStream(screenshotLocation);
			byte[] imageBytes = IOUtils.toByteArray(is);
			Thread.sleep(2000);
			String base64 = Base64.getEncoder().encodeToString(imageBytes);
			test.log(LogStatus.FAIL,
					result.getThrowable() + test.addBase64ScreenShot("data:image/png;base64," + base64));
			// test.log(LogStatus.FAIL, "Test Case Failed is " +result.getThrowable() );
			Reporter.log(
					"<a href= '" + screen + "'target='_blank' ><img src='" + screen + "'>" + screenshotName + "</a>");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	@BeforeMethod
	public void startTest(Method method) {
		test = extent.startTest(method.getName());
		test.assignAuthor("360Logica");
		test.assignCategory(this.getClass().getCanonicalName());
		System.out.println(method.getName());
		System.out.println(test.getDescription());

	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException, InterruptedException {

		if (result.getStatus() == ITestResult.FAILURE) {
			captureScreenshot(result);
		}
		extent.endTest(test);
		System.out.println("****************************************");
	}

	public WebDriver getWebDriver() {
		return this.driver;
	}

	// Get absolute path
	public String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("/", "//");
		return path;
	}

	@AfterSuite
	public void tearDownSuite() {
		// reporter.endReport();
		// getWebDriver().quit();
		extent.flush();
		extent.close();
		try {
			reportLog("Before excel report-" + Utilities.getCurrentDateAndTime().toString());
			Xl.generateReport("D:\\Colt_Data\\Reports\\ExcelReports",
					"excel-report_" + Utilities.getCurrentDateAndTime() + ".xlsx");

			Xl.generateReport("excel-report_" + Utilities.getCurrentDateAndTime() + ".xlsx");
			/*
			 * ExcelReportGenerator.generateExcelReport(Utilities.getRandomInteger(1, 100) +
			 * "_CustomExcelReport.xls", "D:\\Colt_Data\\Reports\\ExcelReports");
			 */
			reportLog("*************Excel Report Code executed**************");

		} catch (Exception e) {
			reportLog("Exception is coming");
			reportLog(e.getMessage());
			e.printStackTrace();
		}
	}

	/* Report logs */
	public void reportLog(String message) {
		test.log(LogStatus.INFO, message);
		logger.info("Message: " + message);
		Reporter.log(message);
	}

	/**
	 * waits for page load to complete
	 * 
	 * @author himanshud
	 * @throws InterruptedException
	 */
	public void waitForAjaxRequestsToComplete() {
		Wait<WebDriver> wait = new WebDriverWait(getWebDriver(), timeout);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Scroll page down pixel
	 * 
	 * @Param pixel pixel to scroll down
	 */
	public void scrollDown(String pixel) {
		JavascriptExecutor jse = (JavascriptExecutor) getWebDriver();
		jse.executeScript("window.scrollBy(0," + pixel + ")", "");
	}
}