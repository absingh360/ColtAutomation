package com.scripts;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;

import com.util.DataModelCPQ;
import com.util.DriverTestCase;
import com.util.Utilities;

public class Opportunity extends DriverTestCase {

	String oppName = "Opp_" + Utilities.getRandomInteger(1, 999999);
	String aEndAddress = "3, Julius-Tandler-Platz, Vienna, Austria, 1090";
	String bEndAddress = "27, Bahnsteggasse, Vienna, Austria, 1210";

	
	public void createOpp() throws FileNotFoundException {

		setUp();
		//getWebDriver().navigate().to(c4c_url);
		getWebDriver().navigate().to(c4c_testurl);
		c4cappPage._waitForJStoLoad();
		c4cappPage.waitForAjaxRequestsToComplete();
		c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		c4cappPage.verifyTitle("SAP Hybris Cloud for Customer");
		c4cappPage.navigateToHomeTabAndAddNew("100117316", oppName);

	}

	@Test
	public void sanity_1() throws InterruptedException, IOException {
		
		createOpp();

		opportunityPage.switchWindow(oppName);
		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote From Created Opportunity");

		opportunityPage.switchWindow("Transaction");
		reportLog("Switching on CQP tab");

		String Description = "Quote_Desc_" + Utilities.getRandomInteger(1, 9999);
		String quoteName = "Quote_" + Utilities.getRandomInteger(1, 9999);

		c4cappPage.verifyTitle("Transaction");
		reportLog("Verifying the title 'Transaction'");

		reportLog("Enter Quote Details");
		transactionPage.sendKeys(transactionPage.quoteName, quoteName);
		reportLog("Type QuoteName: " + quoteName);
		
		String quoteID = transactionPage.getQuoteID();
		reportLog("Quote ID: " + quoteID);
		
		transactionPage.sendKeys(transactionPage.quoteDescription, Description);
		reportLog("Enter description: " + Description);

		/*String currencyType = transactionPage.getUICurrencyType();
		String segment = transactionPage.getUISegment();*/

		transactionPage.verifyQuoteDetailsElements();
		reportLog("Verify All elements of quote detail page");

		productListPage.AddproductType("Ethernet");
		reportLog("Adding EtherNet Line Product");

		/*
		 * c4cappPage.verifyTitle("Model Configuration");
		 * reportLog("Verifying the title 'Model Configuration'");
		 */

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		//reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");
		
		reportLog("Select bandwidth as : "+ "10 Gbps" );
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();
		//reportLog("Verify map is present and buttons available in 'model configuration' page");

		modelConfigurationPage.saveQuoteButton();
		//reportLog("Click on save to quote button");

		productListPage.AddproductType("Hub");
		reportLog("Add Ethernet Hub Product");
		
		modelConfigurationPage.enterHubAddress("10 Gbps", aEndAddress);

		modelConfigurationPage.checkConnectivity();
		//reportLog("Click on Update button then CheckConnectivity button");

		// modelConfigurationPage.enterProductResiliency("Protected");

		modelConfigurationPage.navigateToSiteDetailPage();
		
		reportLog("Select bandwidth as : "+ "10 Gbps" );
		modelConfigurationPage.selectBandwidth("10 Gbps");
		
		modelConfigurationPage.saveQuoteButton();
		//reportLog("Click on save to quote button");

		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress(bEndAddress, "100 Mbps");

		modelConfigurationPage.checkConnectivity();
		//reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");
		
		reportLog("Select bandwidth as : "+ "100 Mbps" );
		modelConfigurationPage.selectBandwidth("100 Mbps");

		modelConfigurationPage.saveQuoteButton();
		//reportLog("Click on save to quote button");

		transactionPage.applyDiscount("PERCENTAGE", "30", "40");
		reportLog("Apply Discount On Quote");
		
		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(deal_user, deal_user_password);
		reportLog("Login to CPQ using deal pricing user credentials.");

		productListPage.clickOnOrderManagerLink();

		c4cappPage.verifyTitle("Commerce Management");
		reportLog("Verifying the title Commerce Management");

		commerceManagementPage.verifyQuoteStatus(quoteName, "Deal Pricing Review");
		commerceManagementPage.openQuoteForReview(quoteName);
		reportLog("Open Quote For Deal Pricing Review");

		transactionPage.uploadMarginAndSubmit("Namita Singh","27");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");
		
		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatus(quoteName, "Pending governance approval");
		commerceManagementPage.openQuoteForReview(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatus(quoteName, "Approved");
		commerceManagementPage.openQuoteForReview(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
		

	}
	
	@Test
	public void sanity_2() throws InterruptedException, IOException
	{
		createOpp();
		
		opportunityPage.switchWindow(oppName);
		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote From Created Opportunity");

		opportunityPage.switchWindow("Transaction");
		reportLog("Switching on CQP tab");

		String Description = "Quote_Desc_" + Utilities.getRandomInteger(1, 9999);
		String quoteName = "Quote_" + Utilities.getRandomInteger(1, 9999);

		c4cappPage.verifyTitle("Transaction");
		reportLog("Verifying the title 'Transaction'");

		reportLog("Enter Quote Details");
		transactionPage.sendKeys(transactionPage.quoteName, quoteName);
		reportLog("Type QuoteName: " + quoteName);
		
		String quoteID = transactionPage.getQuoteID();
		reportLog("Quote ID: " + quoteID);
		
		transactionPage.sendKeys(transactionPage.quoteDescription, Description);
		reportLog("Enter description: " + Description);

		transactionPage.verifyQuoteDetailsElements();
		reportLog("Verify All elements of quote detail page");

		productListPage.AddproductType("Ethernet");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		//reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		modelConfigurationPage.verifyButtonsPresent();
		//reportLog("Verify map is present and buttons available in 'model configuration' page");

		//modelConfigurationPage.selectAllAddons();
	}
}
