package com.scripts;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.util.DataModelCPQ;
import com.util.DataProviderRepository;
import com.util.DriverTestCase;
import com.util.ExcelDataBaseConnector;
import com.util.Utilities;

public class SanityTest  extends DriverTestCase{

	
private String oppID;
private String oppName;
	@BeforeClass
	public void doLogin() throws Exception {
		setUp();
		oppID = c4cpropertyReader.readApplicationData("opportutnityID");
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
		c4cappPage._waitForJStoLoad();
		c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		c4cappPage.verifyTitle("SAP Hybris Cloud for Customer");
		c4cappPage.goToOpportunityPage();
		opportunityPage.searchOpportunity(oppID);
		opportunityPage.selectParticularOpportunity(Integer.parseInt(oppID));
		oppName = opportunityPage.getOpportunityName();
	}
	
	
	@Test(dataProviderClass = DataProviderRepository.class, dataProvider = "testDataProvider")
	public void test_EthernetLine(Object obj) throws Exception {
		
		opportunityPage.switchWindow(oppName);
		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote having Ethernet Line");
		
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
		
		transactionPage.verifyQuoteDetailsElements();
		reportLog("Verify All elements of quote detail page");
		
		productListPage.AddproductType("Ethernet");
		reportLog("Adding EtherNet Product");
		
		c4cappPage.verifyTitle("Model Configuration");
		reportLog("Verifying the title 'Model Configuration'");
		
		modelConfigurationPage.selectBandwidth(cpqModel.getBandWidth());

		modelConfigurationPage.enterAddresses(cpqModel.getSite_A_Add(), cpqModel.getSite_B_Add());
		
		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterResiliency(cpqModel);

		modelConfigurationPage.verifyButtonsPresent();
		reportLog("Verify map is present and buttons available in 'model configuration' page");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		modelConfigurationPage.verifyProductAddedAndAvailableButtons();
		reportLog("Verify product added or not");
		
		
		
}
	
	
	@Test(dataProviderClass = DataProviderRepository.class, dataProvider = "testDataProvider")
	public void test_EthernetHubAndSpoke(Object obj) throws Exception {
		
		opportunityPage.switchWindow(oppName);
		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote having Ethernet Hub and Spoke");
		
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
		
		transactionPage.verifyQuoteDetailsElements();
		reportLog("Verify All elements of quote detail page");
	
		productListPage.AddproductType("Hub");

		modelConfigurationPage.enterHubAddress(cpqModel);

		modelConfigurationPage.click(modelConfigurationPage.update);
		reportLog("Click on to Update button");
		
		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterResiliency(cpqModel);

		modelConfigurationPage.verifyButtonsPresent();
		reportLog("Verify map is present and buttons available in 'model configuration' page");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");

		modelConfigurationPage.enterSpokeAddress(cpqModel);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterResiliency(cpqModel);
		
		modelConfigurationPage.verifyButtonsPresent();
		reportLog("Verify map is present and buttons available in 'model configuration' page");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		modelConfigurationPage.verifyProductAddedAndAvailableButtons();
		reportLog("Verify product added or not");
		
	}
	
	
	
	
}
