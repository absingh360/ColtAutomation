package com.scripts;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.codoid.products.exception.FilloException;
import com.util.DataModelCPQ;
import com.util.DataProviderRepository;
import com.util.DriverTestCase;
import com.util.ExcelDataBaseConnector;
import com.util.Utilities;

public class MaxDataDrivenForEthernet extends DriverTestCase {

	@BeforeClass
	public void doLogin() throws InterruptedException, FileNotFoundException {
		setUp();
		/*
		 * String username = configReader.readApplicationFile("Username"); String
		 * password = configReader.readApplicationFile("Password"); // Open
		 * application URL getWebDriver().navigate().to(application_url); //
		 * reportLog("Navigating Application URl"); waitForAjaxRequestsToComplete();
		 * c4c_Helper.loginToApplication(username, password);
		 */
		listPriceConnection = ExcelDataBaseConnector.createConnection("List_Price_Small");
		test_01_Navigate_From_C4C_To_CPQ();

	}

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

	public void test_01_Navigate_From_C4C_To_CPQ() {
		getWebDriver().navigate().to(c4c_url);
		// reportLog("Navigating C4C Application URl: " + c4c_url);
		c4cappPage._waitForJStoLoad();
		c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		c4cappPage.verifyTitle("Home - SAP Hybris Cloud for Customer");
		c4cappPage.goToOpportunityPage();
		opportunityPage.searchOpportunity("260008");
		opportunityPage.selectParticularOpportunity(260008);
	}

	@Test(dataProviderClass = DataProviderRepository.class, dataProvider = "testDataProvider")
	public void test_03_check_Connectivity_And_Price_Of_sites_On_Basis_Of_UI_Segment(Object obj) throws Exception {

		opportunityPage.switchWindow("SAP Hybris Cloud for Customer");
		opportunityPage.addNewQuoteFromOpportunity();
		opportunityPage.switchWindow("Transaction");
		reportLog("Switching on CQP tab");

		String Description = "Quote_Desc_" + Utilities.getRandomInteger(1, 9999);
		DataModelCPQ cpqModel = (DataModelCPQ) obj;

		c4cappPage.verifyTitle("Transaction");
		reportLog("Verifying the title 'Transaction'");

		transactionPage.sendKeys(transactionPage.quoteName, cpqModel.getQuoteName());
		reportLog("Type QuoteName: " + cpqModel.getQuoteName());

		transactionPage.sendKeys(transactionPage.quoteDescription, Description);
		reportLog("Enter description: " + Description);

		String currencyType = transactionPage.getUICurrencyType();
		String segment = transactionPage.getUISegment();
		cpqModel.setSegment(segment);

		productListPage.AddproductType("Ethernet");
		reportLog("Adding EtherNet Product");

		c4cappPage.verifyTitle("Model Configuration");
		reportLog("Verifying the title 'Model Configuration'");

		modelConfigurationPage.selectBandwidth(cpqModel.getBandWidth());

		modelConfigurationPage.enterAddresses(cpqModel.getSite_A_Add(), cpqModel.getSite_B_Add());

		modelConfigurationPage.click(modelConfigurationPage.update);
		reportLog("Click on to Update button");

		modelConfigurationPage.click(modelConfigurationPage.checkConnectivityButton);
		reportLog("Click on to CheckConnectivity button");
		
		modelConfigurationPage.enterResiliency(cpqModel);

		modelConfigurationPage.verifyConnectivity();

		modelConfigurationPage.verifyBlankPricesMessage();

		String coverage = modelConfigurationPage.getUICoverage();
		cpqModel.setCoverage(coverage);

		String mrc_Net_Price = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Zone 1 MRC");
		String _mrc_Net_Price = Utilities.mrcPriceAsPerContractTerm(cpqModel.getContract_Term(), mrc_Net_Price);
		System.out.println(_mrc_Net_Price);

		String nrc_Net_Price = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Zone 1 NRC");
		String _nrc_Net_Price = Utilities.nrcPriceAsPerContractTerm(cpqModel.getContract_Term(), nrc_Net_Price);
		System.out.println(_nrc_Net_Price);

		String currency = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, cpqModel, "Currency");
		System.out.println(currency);

		_mrc_Net_Price = Utilities.currencyConvertor(_mrc_Net_Price, currency, currencyType);
		reportLog("MRC_Net Price after conversion: " + _mrc_Net_Price);
		
		_nrc_Net_Price = Utilities.currencyConvertor(_mrc_Net_Price, currency, currencyType);
		reportLog("MRC_Net Price after conversion: " + _nrc_Net_Price);
		
		String contractTerm = cpqModel.getContract_Term();
		modelConfigurationPage.enterContractTerm("Ethernet", contractTerm);
		modelConfigurationPage.click(modelConfigurationPage.addToTransaction);

		boolean flag = modelConfigurationPage.isElementDisplayed(modelConfigurationPage.addToTransaction);
		if (flag) {
			modelConfigurationPage.click(modelConfigurationPage.addToTransaction);
			modelConfigurationPage._waitForJStoLoad();
			flag = false;
		}
		scrollDown("500");
		transactionPage.verify_NRC_MRC_Prices(_nrc_Net_Price, _mrc_Net_Price);

		transactionPage.clickOnSaveButton();
		getWebDriver().close();

	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException, InterruptedException {

		if (result.getStatus() == ITestResult.FAILURE) {
			captureScreenshot(result);
		}
		getWebDriver().close();
		extent.endTest(test);
	}
}
