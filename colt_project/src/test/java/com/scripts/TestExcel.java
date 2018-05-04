package com.scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.util.DataModelCPQ;
import com.util.DataProviderRepository;
import com.util.DriverTestCase;
import com.util.ExcelDataBaseConnector;
import com.util.Utilities;

public class TestExcel extends DriverTestCase {

	int rowNumber = 2;
	FileInputStream fsIP;

	@BeforeClass
	public void doLogin() throws Exception {
		setUp();
		fsIP = new FileInputStream(new File("D:\\swapnil\\colt_project\\ExcelFile\\JavascriptData.xlsx"));
		FileOutputStream output_file;
		// oppID = c4cpropertyReader.readApplicationData("opportutnityID");
		listPriceConnection = ExcelDataBaseConnector.createConnection("List_Price_Small");
		// c4cappPage.loginInToCPQApplication(username, password);
		// test_01_Navigate_From_C4C_To_CPQ();
	}

	@Test
	public void test_01_Navigate_From_C4C_To_CPQ() throws InterruptedException, IOException {
		// getWebDriver().navigate().to(cpq_url);
		getWebDriver().navigate().to(cpq_url);
		// c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		// reportLog("Navigating C4C Application URl: " + c4c_url);

		c4cappPage._waitForJStoLoad();
		c4cappPage.loginInToCPQApplication(username, password);

		c4cappPage.click(productListPage.oracleQuoteToOrderManagerLink);
		c4cappPage.click(commerceManagementPage.newTransactionButton);
		c4cappPage.click(commerceManagementPage.selectButton);

		// c4cappPage.verifyTitle("Home - SAP Hybris Cloud for Customer");
		/*c4cappPage.goToOpportunityPage();
		opportunityPage.searchOpportunity("260165");
		opportunityPage.selectParticularOpportunity(Integer.parseInt("260165"));
		opportunityPage.switchWindow("SAP Hybris Cloud for Customer");
		opportunityPage.addNewQuoteFromOpportunity();
		opportunityPage.switchWindow("Transaction");*/
		productListPage.AddproductType("Ethernet");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.enterAddresses("11, Museumstraat, Antwerp, Belgium, 2000",
				"3, Schalienstraat, Antwerp, Belgium, 2000");

		modelConfigurationPage.click(modelConfigurationPage.update);
		modelConfigurationPage.click(modelConfigurationPage.checkConnectivityButton);
		modelConfigurationPage.enterProductResiliency("Protected");
	}

	@Test(dataProviderClass = DataProviderRepository.class, dataProvider = "javascriptInjection")
	void test_02_checkJavascript(Object obj) throws InterruptedException, Exception {
		// test_01_Navigate_From_C4C_To_CPQ();
		DataModelCPQ cpqModel = (DataModelCPQ) obj;

		String mrc_Net_Price = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Zone 1 MRC");
		String _mrc_Net_Price = Utilities.mrcPriceAsPerContractTerm(cpqModel.getContract_Term(), mrc_Net_Price);
		System.out.println(_mrc_Net_Price);

		String nrc_Net_Price = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Zone 1 NRC");
		String _nrc_Net_Price = Utilities.nrcPriceAsPerContractTerm(cpqModel.getContract_Term(), nrc_Net_Price);
		System.out.println(_nrc_Net_Price);

		long startTime = System.currentTimeMillis();

		modelConfigurationPage.enterSiteADetailByJavascript(cpqModel);

		modelConfigurationPage.enterSiteBDetailByJavascript(cpqModel);

		modelConfigurationPage.click(modelConfigurationPage.update);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);

		// modelConfigurationPage.enterWriteProductPrices();
		updateDataInExcel();

	}

	/*
	 * @AfterMethod public void afterMethod(ITestResult result) throws IOException,
	 * InterruptedException {
	 * 
	 * if (result.getStatus() == ITestResult.FAILURE) { captureScreenshot(result); }
	 * getWebDriver().close(); extent.endTest(test); }
	 */

	public void updateDataInExcel() throws IOException {
		// FileInputStream fsIP= new FileInputStream(new
		// File("D:\\swapnil\\colt_project\\ExcelFile\\JavascriptDataUpdated.xlsx"));
		// //Read the spreadsheet that needs to be updated
		if (rowNumber > 2)
			inputExcel = new FileInputStream(new File(System.getProperty("user.dir")+reportExcelPath));
		XSSFWorkbook wb = new XSSFWorkbook(inputExcel); // Access the workbook

		XSSFSheet worksheet = wb.getSheet("Sheet1"); // Access the worksheet, so that we can update / modify it.

		Cell cell = null; // declare a Cell object

		cell = worksheet.getRow(rowNumber).getCell(27); // Access the second cell in second row to update the value

		cell.setCellValue("OverRide Last Name 27"); // Get current cell value value and overwrite the value

		cell = worksheet.getRow(rowNumber).getCell(26);
		cell.setCellValue("OverRide Last Name 26");

		fsIP.close(); // Close the InputStream

		// FileOutputStream output_file =new FileOutputStream(new
		// File("D:\\JavascriptDataUpdated.xlsx")); //Open FileOutputStream to write
		// updates

		wb.write(outputExcel); // write changes

		outputExcel.close(); //
		rowNumber++;
	}

}
