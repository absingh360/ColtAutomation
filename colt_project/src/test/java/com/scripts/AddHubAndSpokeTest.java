package com.scripts;

import java.io.FileNotFoundException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.codoid.products.exception.FilloException;
import com.util.DataModelCPQ;
import com.util.DataProviderRepository;
import com.util.DriverTestCase;
import com.util.ExcelDataBaseConnector;
import com.util.Utilities;

public class AddHubAndSpokeTest extends DriverTestCase {

	@BeforeClass
	public void doLogin() throws InterruptedException, FileNotFoundException {
		setUp();
		/*String username = configReader.readApplicationFile("Username");
		String password = configReader.readApplicationFile("Password");
		// Open application URL
		getWebDriver().navigate().to(application_url);
		waitForAjaxRequestsToComplete();
		c4c_Helper.loginToApplication(username, password);*/
		listPriceConnection = ExcelDataBaseConnector.createConnection("List_Price_HubSpoke");
	}

	@Test
	public void test_01_Navigate_From_C4C_To_CPQ() {
		getWebDriver().navigate().to(c4c_url);
		reportLog("Navigating C4C Application URl: " + c4c_url);
		c4cappPage._waitForJStoLoad();
		c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		c4cappPage.verifyTitle("SAP Hybris Cloud for Customer");
		c4cappPage.goToOpportunityPage();
		
		opportunityPage.searchOpportunity("260396");		
		opportunityPage.selectParticularOpportunity(260396);		
		opportunityPage.addNewQuoteFromOpportunity();
		
		opportunityPage.switchWindow("Transaction");
		reportLog("Switching on CQP tab");
	}
	
	@Test(dataProviderClass = DataProviderRepository.class, dataProvider = "spokeData")
	public void test_02_check_Connectivity_and_price_of_sites_for_HubAndSpoke(Object obj) throws Exception {

		String Description = "Quote_Desc_" + Utilities.getRandomInteger(1, 9999);
		DataModelCPQ cpqModel = (DataModelCPQ) obj;
/*
		productListPage.clickOnOrderManagerLink();

		c4cappPage.verifyTitle("Commerce Management");
		reportLog("Verifying the title Commerce Management");

		commerceManagementPage.clickOnAddTransactionButton();
		reportLog("Click on to NewTransaction link");

		c4cappPage.verifyTitle("Transaction");
		reportLog("Verifying the title 'Transaction'");*/

		transactionPage.sendKeys(transactionPage.quoteName, cpqModel.getQuoteName());
		reportLog("Type QuoteName: " + cpqModel.getQuoteName());

		transactionPage.sendKeys(transactionPage.quoteDescription, Description);
		reportLog("Enter description: " + Description);

		String currencyType = transactionPage.getUICurrencyType();
		reportLog("Currency Type On UI: " + currencyType);
		reportLog("Segment  On UI: " + cpqModel.getSegment());

		productListPage.AddproductType("Hub");

		c4cappPage.verifyTitle("Model Configuration");
		reportLog("Verifying the title 'Model Configuration'");

		modelConfigurationPage.enterHubAddress(cpqModel);

		modelConfigurationPage.click(modelConfigurationPage.update);
		reportLog("Click on to Update button");

		// modelConfigurationPage.enterAddressManually(cpqModel.getBuilding_Type());

		modelConfigurationPage.click(modelConfigurationPage.checkConnectivityButton);
		reportLog("Click on to CheckConnectivity button");
		
		//modelConfigurationPage.verifyBlankPricesMessage();

		/*cpqModel.setSiteABuildingType(modelConfigurationPage.getBuildingTypeForSiteA());
		// ========================================DB Operations ================================
		String query = ExcelDataBaseConnector.getSQLQuery("Hub", cpqModel);
		String mrc_Net_Price_Hub = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, query, "Zone 1 MRC");
		String _mrc_Net_Price_Hub = Utilities.mrcPriceAsPerContractTerm(cpqModel.getContract_Term(), mrc_Net_Price_Hub);
		System.out.println(_mrc_Net_Price_Hub);

		String nrc_Net_Price_Hub = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, query, "Zone 1 NRC");
		String _nrc_Net_Price_Hub = Utilities.nrcPriceAsPerContractTerm(cpqModel.getContract_Term(), nrc_Net_Price_Hub);
		System.out.println(_nrc_Net_Price_Hub);

		String currency = ExcelDataBaseConnector.executeSQLQuery(listPriceConnection, query, "Currency");
		System.out.println(currency);

		if (!(mrc_Net_Price_Hub.equals("NA") || mrc_Net_Price_Hub == null)) {
			String str = Utilities.currencyConvertor(_mrc_Net_Price_Hub, currency, currencyType);
			_mrc_Net_Price_Hub = str;
			System.out.println(str);
			reportLog("MRC_Net Price after conversion: " + str);
		}

		if (!(nrc_Net_Price_Hub.equals("NA") || nrc_Net_Price_Hub == null)) {
			String str = Utilities.currencyConvertor(_nrc_Net_Price_Hub, currency, currencyType);
			_nrc_Net_Price_Hub = str;
			System.out.println(str);
			reportLog("NRC_Net Price after conversion: " + str);
		}

		scrollDown("350");
		String contractTerm = cpqModel.getContract_Term();
		modelConfigurationPage.enterContractTerm("Ethernet", contractTerm);
		modelConfigurationPage.enterContractTerm("Hub", contractTerm);
		modelConfigurationPage.click(modelConfigurationPage.update);
		modelConfigurationPage.click(modelConfigurationPage.addToTransaction);

		// modelConfigurationPage.selectHubAndSpokeAddOns(cpqModel);

		boolean flag = modelConfigurationPage.isElementDisplayed(modelConfigurationPage.addToTransaction);
		System.out.println("*********************" + flag);
		if (flag) {
			modelConfigurationPage.click(modelConfigurationPage.addToTransaction);
			modelConfigurationPage._waitForJStoLoad();
			flag = false;
		}
		scrollDown("500");

		transactionPage.verify_NRC_MRC_PricesForHub(_nrc_Net_Price_Hub, _mrc_Net_Price_Hub);
*/
		Thread.sleep(4000);
		modelConfigurationPage.click(modelConfigurationPage.addToTransaction);
		
		transactionPage.clickOnSaveButton();

		productListPage.clickOnOrderManagerLink();

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
}
