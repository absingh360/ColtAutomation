package com.scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.ElementNotVisibleException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.util.DataModelCPQ;
import com.util.DataProviderRepository;
import com.util.DriverTestCase;
import com.util.ExcelDataBaseConnector;
import com.util.ExcelReader;
import com.util.Utilities;

public class SampleTest extends DriverTestCase {

	private String oppID = "261425";
	// ENTS - private String oppID = "261511";
	// WHSS - 261425
	private String oppName;
	int rowNumber = 2;
	FileInputStream fsIP;
	ExcelReader ex = new ExcelReader();
	FileOutputStream output_file;

	@BeforeClass
	public void doLogin() throws Exception {

		ex.deleteFile();
		ex.createExcelFile();

		setUp();
		fsIP = new FileInputStream(new File(getPath() + "\\ExcelFile\\" + "JavascriptData.xlsx"));

		// oppID = c4cpropertyReader.readApplicationData("opportutnityID");
		// listPriceConnection =
		// ExcelDataBaseConnector.createConnection("List_Price_Small");
		// listPriceConnection =
		// ExcelDataBaseConnector.createConnection("SheetPriceWHSS");
		// c4cappPage.loginInToCPQApplication(username, password);
		// test_01_Navigate_From_C4C_To_CPQ();

	}

	@Test(priority = 1)
	public void test_Navigate_From_C4C_To_CPQ() throws InterruptedException, IOException {

		// getWebDriver().navigate().to(c4c_url);
		getWebDriver().navigate().to(c4c_testurl);
		reportLog("Naviagte to the Application Url");
		//c4cappPage._waitForJStoLoad();
		//c4cappPage.waitForAjaxRequestsToComplete();
		c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		c4cappPage.verifyTitle("SAP Hybris Cloud for Customer");
		c4cappPage.goToOpportunityPage();
		opportunityPage.searchOpportunity(oppID);
		opportunityPage.selectParticularOpportunity(Integer.parseInt(oppID));
		oppName = opportunityPage.getOpportunityName();
		opportunityPage.switchWindow(oppName);
		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote having Ethernet Line product");

		opportunityPage.switchWindow("Transaction");
		reportLog("Switching on CQP tab");

		String Description = "Quote_Desc_" + Utilities.getRandomInteger(1, 9999);

		transactionPage.sendKeys(transactionPage.quoteDescription, Description);
		reportLog("Enter description: " + Description);

		productListPage.AddproductType("Ethernet");


	}

	@Test(dataProviderClass = DataProviderRepository.class, dataProvider = "javascriptInjection", priority = 2)
	public void testPrices(Object obj) throws InterruptedException, Exception {

		DataModelCPQ cpqModel = (DataModelCPQ) obj;

		String pricingSegement = cpqModel.getSegment();
		if (pricingSegement.equals("ENTS")) {
			listPriceConnection = ExcelDataBaseConnector.createConnection("SheetPriceENTS");
		} else if (pricingSegement.equals("WHSS")) {
			listPriceConnection = ExcelDataBaseConnector.createConnection("SheetPriceWHSS");
		}

		String currencyType = "EUR";
		String expectedMrcPrice = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Zone 1 MRC")
				.replaceAll(",", "");
		// String _mrc_Net_Price =
		// Utilities.mrcPriceAsPerContractTerm(cpqModel.getContract_Term(),
		// mrc_Net_Price);
		System.out.println(expectedMrcPrice);

		String expectedNrcPrice = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Zone 1 NRC")
				.replaceAll(",", "");
		// String _nrc_Net_Price =
		// Utilities.nrcPriceAsPerContractTerm(cpqModel.getContract_Term(),
		// nrc_Net_Price);
		System.out.println(expectedNrcPrice);

		long startTime = System.currentTimeMillis();

		String currency = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Currency");
		System.out.println(currency);

		String NetMRC_Price = Utilities.currencyConvertor(expectedMrcPrice, currency, currencyType).replaceAll(",", "");
		reportLog("MRC_Net Price after conversion: " + NetMRC_Price);
		System.out.println(NetMRC_Price);
		String NetNRC_Price = Utilities.currencyConvertor(expectedNrcPrice, currency, currencyType).replaceAll(",", "");
		reportLog("MRC_Net Price after conversion: " + NetNRC_Price);
		System.out.println(NetNRC_Price);

		reportLog("Expected NRC Prices is : " + NetNRC_Price + " And MRC Price is : " + NetMRC_Price);

		modelConfigurationPage.enterAddresses(cpqModel.getSite_A_Add(), cpqModel.getSite_B_Add());

		modelConfigurationPage.click(modelConfigurationPage.update);
		reportLog("Click on to Update button");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.navigateToSiteDetailPage();

		modelConfigurationPage.configureProduct(cpqModel);

		// String buildingType = modelConfigurationPage.getBuildingType();
		
		List<String>  buildings = new ArrayList<>();
		String buildingType = modelConfigurationPage.actualBuildingType.getText();
		buildings.add( modelConfigurationPage.buildingTypes.get(0).getText());
		buildings.add(modelConfigurationPage.buildingTypes.get(1).getText());
		
		String expectedCoverage = cpqModel.getCoverage();

		String actualCoverage = modelConfigurationPage.siteCoverage.getText();

		String NrcPrice = null;
		String MrcPrice = null;
		try
		{
		String actualNrcPrice = modelConfigurationPage.nrcPrice.getText();

		 NrcPrice = modelConfigurationPage.getActualPrice(actualNrcPrice);
		String actualMrcPrice = modelConfigurationPage.mrcPrice.getText();

		 MrcPrice = modelConfigurationPage.getActualPrice(actualMrcPrice);

		}
		 catch (ElementNotVisibleException e) {
				System.out.println("CheckConnectivity button not available");
			}
			catch(NoSuchElementException e)
			{
				System.out.println("CheckConnectivity button not available");
			} 
			catch (Exception e) {
	            // TODO Auto-generated catch block
				//modelConfigurationPage.manuallyEnterHub.click();
				System.out.println("CheckConnectivity button not available ");
	        }
		
		modelConfigurationPage.navigateToSiteAddress();

		System.out.println("Actual NRC Price is : " + NrcPrice + " And MRC Price is : " + MrcPrice);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);

		ex.writeData(cpqModel.getSegment(), rowNumber, 0);
		ex.writeData(cpqModel.getSite_A_Add(), rowNumber, 1);
		ex.writeData(cpqModel.getSite_B_Add(), rowNumber, 2);
		ex.writeData(NetMRC_Price, rowNumber, 3);
		ex.writeData(NetNRC_Price, rowNumber, 4);
		ex.writeData(MrcPrice, rowNumber, 5);
		ex.writeData(NrcPrice, rowNumber, 6);
		ex.writeData(cpqModel.getBandWidth(), rowNumber, 7);
		ex.writeData(cpqModel.getResiliency(), rowNumber, 8);
		ex.writeData(expectedCoverage, rowNumber, 9);
		ex.writeData(actualCoverage, rowNumber, 10);
		ex.writeData(buildings.get(0), rowNumber, 11);
		ex.writeData(buildings.get(0), rowNumber, 12);
		ex.writeData(Long.toString(totalTime), rowNumber, 13);
		
		// ex.writeData(cpqModel.getBuilding_Type(), rowNumber, 8);

		rowNumber++;
		
		try {
			ExcelDataBaseConnector.CloseTheConnection(listPriceConnection);
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @Test public void test_01_Navigate_From_C4C_To_CPQ() throws
	 * InterruptedException { //getWebDriver().navigate().to(cpq_url);
	 * //getWebDriver().navigate().to(c4c_url);
	 * //c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password); //
	 * reportLog("Navigating C4C Application URl: " + c4c_url);
	 * test_Navigate_From_C4C_To_CPQ(); opportunityPage.switchWindow(oppName);
	 * opportunityPage.addNewQuoteFromOpportunity();
	 * reportLog("Add new Quote having Ethernet Hub and Spoke");
	 * 
	 * opportunityPage.switchWindow("Transaction");
	 * reportLog("Switching on CQP tab");
	 * 
	 * String Description = "Quote_Desc_" + Utilities.getRandomInteger(1, 9999);
	 * 
	 * transactionPage.sendKeys(transactionPage.quoteDescription, Description);
	 * reportLog("Enter description: " + Description);
	 * 
	 * productListPage.AddproductType("Ethernet");
	 * 
	 * modelConfigurationPage.checkFirstProduct();
	 * 
	 * 
	 * 
	 * c4cappPage._waitForJStoLoad(); c4cappPage.loginInToCPQApplication(username,
	 * password);
	 * 
	 * c4cappPage.click(productListPage.oracleQuoteToOrderManagerLink);
	 * c4cappPage.click(commerceManagementPage.newTransactionButton);
	 * c4cappPage.click(commerceManagementPage.selectButton);
	 * 
	 * //c4cappPage.verifyTitle("Home - SAP Hybris Cloud for Customer");
	 * c4cappPage.goToOpportunityPage();
	 * opportunityPage.searchOpportunity("261415");
	 * opportunityPage.selectParticularOpportunity(Integer.parseInt("261415"));
	 * opportunityPage.switchWindow("SAP Hybris Cloud for Customer");
	 * opportunityPage.addNewQuoteFromOpportunity();
	 * opportunityPage.switchWindow("Transaction");
	 * productListPage.AddproductType("Ethernet");
	 * modelConfigurationPage.selectBandwidth("1 Gbps"); modelConfigurationPage.
	 * enterAddresses("11, Museumstraat, Antwerp, Belgium, 2000"
	 * ,"3, Schalienstraat, Antwerp, Belgium, 2000");
	 * modelConfigurationPage.click(modelConfigurationPage.update);
	 * modelConfigurationPage.click(modelConfigurationPage.checkConnectivityButton);
	 * modelConfigurationPage.enterProductResiliency("Protected"); }
	 */

	/*
	 * @Test(dataProviderClass = DataProviderRepository.class, dataProvider =
	 * "javascriptInjection") public void test_02_checkJavascript(Object obj) throws
	 * InterruptedException, Exception {
	 * 
	 * //test_01_Navigate_From_C4C_To_CPQ(); DataModelCPQ cpqModel = (DataModelCPQ)
	 * obj;
	 * 
	 * String mrc_Net_Price =
	 * ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel,
	 * "Zone 1 MRC"); String _mrc_Net_Price =
	 * Utilities.mrcPriceAsPerContractTerm(cpqModel.getContract_Term(),
	 * mrc_Net_Price); System.out.println(_mrc_Net_Price);
	 * 
	 * String nrc_Net_Price =
	  ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel,
	 * "Zone 1 NRC"); String _nrc_Net_Price =
	 * Utilities.nrcPriceAsPerContractTerm(cpqModel.getContract_Term(),
	 * nrc_Net_Price); System.out.println(_nrc_Net_Price);
	 * 
	 * long startTime = System.currentTimeMillis();
	 * 
	 * 
	 * modelConfigurationPage.enterSiteADetailByJavascript(cpqModel);
	 * 
	 * modelConfigurationPage.enterSiteBDetailByJavascript(cpqModel);
	 * 
	 * modelConfigurationPage.click(modelConfigurationPage.update);
	 * 
	 * modelConfigurationPage.checkConnectivity();
	 * reportLog("Click on Update button then CheckConnectivity button");
	 * 
	 * modelConfigurationPage.navigateToSiteAddress();
	 * 
	 * long endTime = System.currentTimeMillis(); long totalTime = endTime -
	 * startTime; System.out.println(totalTime);
	 * 
	 * System.out.println(modelConfigurationPage.nrcPrice.getText());
	 * System.out.println(modelConfigurationPage.mrcPrice.getText());
	 * 
	 * //modelConfigurationPage.enterWriteProductPrices(); //updateDataInExcel();
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	/*
	 * @AfterMethod public void afterMethod(ITestResult result) throws IOException,
	 * InterruptedException {
	 * 
	 * if (result.getStatus() == ITestResult.FAILURE) { captureScreenshot(result); }
	 * getWebDriver().close(); extent.endTest(test); }
	 */

	/*
	 * public void updateDataInExcel() throws IOException { //FileInputStream fsIP=
	 * new FileInputStream(new
	 * File("D:\\swapnil\\colt_project\\ExcelFile\\JavascriptDataUpdated.xlsx"));
	 * //Read the spreadsheet that needs to be updated if(rowNumber>2) fsIP=new
	 * FileInputStream(new File("D:\\JavascriptDataUpdated.xlsx")); XSSFWorkbook wb
	 * = new XSSFWorkbook(fsIP); //Access the workbook
	 * 
	 * XSSFSheet worksheet = wb.getSheet("Sheet5"); //Access the worksheet, so that
	 * we can update / modify it.
	 * 
	 * Cell cell = null; // declare a Cell object
	 * 
	 * cell = worksheet.getRow(rowNumber).getCell(27); // Access the second cell in
	 * second row to update the value
	 * 
	 * cell.setCellValue("OverRide Last Name 27"); // Get current cell value value
	 * and overwrite the value
	 * 
	 * cell = worksheet.getRow(rowNumber).getCell(26);
	 * cell.setCellValue("OverRide Last Name 26");
	 * 
	 * fsIP.close(); //Close the InputStream
	 * 
	 * FileOutputStream output_file =new FileOutputStream(new
	 * File("D:\\JavascriptDataUpdated.xlsx")); //Open FileOutputStream to write
	 * updates
	 * 
	 * wb.write(output_file); //write changes
	 * 
	 * output_file.close(); // rowNumber++; }
	 */

	@AfterClass
	public void closeConn() {
		// close db
		try {
			ExcelDataBaseConnector.CloseTheConnection(listPriceConnection);
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
