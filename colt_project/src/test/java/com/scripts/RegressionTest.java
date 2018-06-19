package com.scripts;

import java.util.List;

import org.testng.annotations.Test;

import com.util.DriverTestCase;
import com.util.ExcelReader;
import com.util.Utilities;

public class RegressionTest extends DriverTestCase {

	String oppName = "";
	String aEndAddress = "3, Julius-Tandler-Platz, Vienna, Austria, 1090";
	String bEndAddress = "27, Bahnsteggasse, Vienna, Austria, 1210";

	public void test_01_Navigate_From_C4C_To_CPQ(String oppID) throws Exception {

		setUp();
		// getWebDriver().navigate().to(c4c_url);
		getWebDriver().navigate().to(c4c_testurl);
		c4cappPage._waitForJStoLoad();
		c4cappPage.waitForAjaxRequestsToComplete();
		c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		c4cappPage.verifyTitle("SAP Hybris Cloud for Customer");
		c4cappPage.goToOpportunityPage();
		opportunityPage.searchOpportunity(oppID);
		opportunityPage.selectParticularOpportunity(Integer.parseInt(oppID));
		oppName = opportunityPage.getOpportunityName();

		opportunityPage.changeOpportunityStatus();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount between 0-5%
	 * On_Net-On_Net
	 */
	@Test(priority = 1)
	public void regressionFlow_1() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		 modelConfigurationPage.selectBandwidth("10 Gbps");
		//modelConfigurationPage.selectRandomBandwidth();

		 reportLog("Verify available UI Elements");
		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		//modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.clicOnSubmitButton();
		reportLog("Submit Quote");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount between 5-10%
	 * On_Net-On_Net
	 */
	@Test(priority = 2)
	public void regressionFlow_2() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		 modelConfigurationPage.selectBandwidth("10 Gbps");
		//modelConfigurationPage.selectRandomBandwidth();

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		//modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount between 10-15%
	 * On_Net-On_Net
	 */
	@Test(priority = 3)
	public void regressionFlow_3() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		 modelConfigurationPage.selectBandwidth("10 Gbps");
		//modelConfigurationPage.selectRandomBandwidth();

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		//modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "12", "13");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount is greater than 15%
	 * On_Net-On_Net (IGMAD- 0-25%)
	 */
	@Test(priority = 4)
	public void regressionFlow_4() throws Exception {

		String oppID = "261511";

		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		 modelConfigurationPage.selectBandwidth("10 Gbps");
		//modelConfigurationPage.selectRandomBandwidth();

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		//modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.addRequiredDetails();

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount is greater than 15%
	 * On_Net-On_Net (IGMAD- 25-30%)
	 */
	@Test(priority = 5)
	public void regressionFlow_5() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		 modelConfigurationPage.selectBandwidth("10 Gbps");
		//modelConfigurationPage.selectRandomBandwidth();

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
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

	/*
	 * Add Ethernet Line product and apply percentage discount is greater than 15%
	 * On_Net-On_Net (IGMAD > 30%)
	 */
	@Test(priority = 6)
	public void regressionFlow_6() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);
		
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

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		 modelConfigurationPage.selectBandwidth("10 Gbps");
		//modelConfigurationPage.selectRandomBandwidth();

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "35");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
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

	/*
	 * Add Ethernet Line product and apply percentage discount between 0-5%
	 * On_Net-Off_Net
	 */
	@Test(priority = 7)
	public void regressionFlow_7() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Chemin Latéral, Alfortville, France, Île-de-France, 94140");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.selectConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}

	/*
	 * Add Ethernet Line product and apply percentage discount between 5-10%
	 * On_Net-Off_Net
	 */
	@Test(priority = 8)
	public void regressionFlow_8() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Chemin Latéral, Alfortville, France, Île-de-France, 94140");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.selectConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}

	/*
	 * Add Ethernet Line product and apply percentage discount between 10-15%
	 * On_Net-Off_Net
	 */
	@Test(priority = 9)
	public void regressionFlow_9() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Chemin Latéral, Alfortville, France, Île-de-France, 94140");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		// modelConfigurationPage.selectConnectivity();

		modelConfigurationPage.selectConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "13", "12");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount > 15% On_Net-Off_Net
	 * and IGMAD = 0-25
	 */
	@Test(priority = 10)
	public void regressionFlow_10() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Chemin Latéral, Alfortville, France, Île-de-France, 94140");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.selectConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "19");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount > 15% On_Net-Off_Net
	 * and IGMAD = 25-30
	 */
	@Test(priority = 11)
	public void regressionFlow_11() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Chemin Latéral, Alfortville, France, Île-de-France, 94140");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.selectConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "19");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount > 15% On_Net-Off_Net
	 * and IGMAD > 30
	 */
	@Test(priority = 12)
	public void regressionFlow_12() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Chemin Latéral, Alfortville, France, Île-de-France, 94140");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.selectConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "19");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "35");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	// Near Net bug - 2349
	/*
	 * Add Ethernet Line product and apply percentage discount between 0-5%
	 * On_Net-Near_Net
	 */
	@Test(priority = 13)
	public void regressionFlow_13() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		// modelConfigurationPage.selectNearNetConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}

	/*
	 * Add Ethernet Line product and apply percentage discount between 5-10%
	 * On_Net-Near_Net
	 */
	@Test(priority = 14)
	public void regressionFlow_14() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}

	/*
	 * Add Ethernet Line product and apply percentage discount between 10-15%
	 * On_Net-Near_Net
	 */
	@Test(priority = 15)
	public void regressionFlow_15() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "12", "13");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}

	/*
	 * Add Ethernet Line product and apply percentage discount > 15% On_Net-Near_Net
	 * and IGMAD = 0-25
	 */
	@Test(priority = 16)
	public void regressionFlow_16() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount > 15% On_Net-Near_Net
	 * and IGMAD = 25-30
	 */
	@Test(priority = 17)
	public void regressionFlow_17() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "23", "19");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Line product and apply percentage discount > 15% On_Net-Near_Net
	 * and IGMAD > 30
	 */
	@Test(priority = 18)
	public void regressionFlow_18() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014",
				"19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

		transactionPage.submitQuote();
		reportLog("Submit Quote");
		;

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "34");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet hub product and apply percentage discount between 0-5% On_Net
	 */
	@Test(priority = 19)
	public void regressionFlow_19() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		reportLog("Select bandwidth");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.clicOnSubmitButton();
		reportLog("Submit Quote");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Hub product and apply percentage discount between 5-10%
	 * On_Net
	 */
	@Test(priority = 20)
	public void regressionFlow_20() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		reportLog("Select bandwidth");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Hub product and apply percentage discount between 10-15%
	 * On_Net
	 */
	@Test(priority = 21)
	public void regressionFlow_21() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		reportLog("Select bandwidth");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "12", "13");
		reportLog("Apply Discount On Quote");

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Hub product and apply percentage discount is greater than 15%
	 * On_Net-On_Net (IGMAD- 0-25%)
	 */
	@Test(priority = 22)
	public void regressionFlow_22() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		reportLog("Select bandwidth");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");
		
		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	 * Add Ethernet Hub product and apply percentage discount is greater than 15%
	 * On_Net-On_Net (IGMAD- 25-30%)
	 */
	@Test(priority = 23)
	public void regressionFlow_23() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}

	/*
	 * Add Ethernet Hub product and apply percentage discount is greater than 15%
	 * On_Net (IGMAD > 30%)
	 */
	@Test(priority = 24)
	public void regressionFlow_24() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		//modelConfigurationPage.selectRandomBandwidth();

		modelConfigurationPage.selectBandwidth("10 Gbps");
		
		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "34");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}

	
	/*
	 * Add Ethernet Hub product and apply percentage discount between 0 - 5%
	 * NearNet 
	 */ 
	
	@Test(priority = 25)
	public void regressionFlow_25() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub product and apply percentage discount between 5 - 10%
	 * NearNet 
	 */ 
	
	@Test(priority = 26)
	public void regressionFlow_26() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	
	/*
	 * Add Ethernet Hub product and apply percentage discount between 10 - 15%
	 * NearNet 
	 */ 
	
	@Test(priority = 27)
	public void regressionFlow_27() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "12", "13");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub product and apply percentage discount > 15%
	 * NearNet (IGMAD = 0 - 25)
	 */ 
	
	@Test(priority = 28)
	public void regressionFlow_28() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub product and apply percentage discount > 15%
	 * NearNet (IGMAD = 25 - 30)
	 */ 
	
	@Test(priority = 29)
	public void regressionFlow_29() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	

	/*
	 * Add Ethernet Hub product and apply percentage discount > 15%
	 * NearNet (IGMAD > 30)
	 */ 
	
	@Test(priority = 30)
	public void regressionFlow_30() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "34");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 0 - 5%
	 * OnNet - OnNet
	 */ 
	
	@Test(priority = 31)
	public void regressionFlow_31() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress(bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "4");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "34");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 5 - 10%
	 * OnNet - OnNet
	 */ 
	
	@Test(priority = 32)
	public void regressionFlow_32() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress(bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "8", "7");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 10 - 15%
	 * OnNet - OnNet
	 */ 
	
	@Test(priority = 33)
	public void regressionFlow_33() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress(bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "12", "13");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - OnNet(IGMAD = 20 - 25)
	 */ 
	
	@Test(priority = 34)
	public void regressionFlow_34() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress(bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - OnNet(IGMAD = 25 - 30)
	 */ 
	
	@Test(priority = 35)
	public void regressionFlow_35() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress(bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	

	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - OnNet(IGMAD > 30)
	 */ 
	
	@Test(priority = 36)
	public void regressionFlow_36() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress(aEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress(bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "34");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 0 - 5%
	 * OnNet - OffNet
	 */ 
	
	@Test(priority = 37)
	public void regressionFlow_37() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.selectSpokeOffNetConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 5 - 10%
	 * OnNet - OffNet
	 */ 
	
	@Test(priority = 38)
	public void regressionFlow_38() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.selectSpokeOffNetConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 10 - 15%
	 * OnNet - OffNet
	 */ 
	
	@Test(priority = 39)
	public void regressionFlow_39() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.selectSpokeOffNetConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "13", "12");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - OffNet(IGMAD = 20-25)
	 */ 
	
	@Test(priority = 40)
	public void regressionFlow_40() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		// reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.selectSpokeOffNetConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - OffNet(IGMAD = 25-30)
	 */ 
	
	@Test(priority = 41)
	public void regressionFlow_41() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.selectSpokeOffNetConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - OffNet(IGMAD > 30)
	 */ 
	
	@Test(priority = 42)
	public void regressionFlow_42() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.selectSpokeOffNetConnectivity();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "34");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 0 - 5%
	 * OnNet - NearNet
	 */ 
	
	@Test(priority = 43)
	public void regressionFlow_43() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 5 - 10%
	 * OnNet - NearNet
	 */ 
	
	@Test(priority = 44)
	public void regressionFlow_44() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount between 10 - 15%
	 * OnNet - NearNet
	 */ 
	
	@Test(priority = 45)
	public void regressionFlow_45() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "13", "12");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - NearNet(IGMAD = 20-25)
	 */ 
	
	@Test(priority = 46)
	public void regressionFlow_46() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - NearNet(IGMAD = 25-30)
	 */ 
	
	@Test(priority = 47)
	public void regressionFlow_47() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	/*
	 * Add Ethernet Hub & Spoke product and apply percentage discount > 15%
	 * OnNet - NearNet(IGMAD > 30)
	 */ 
	
	@Test(priority = 48)
	public void regressionFlow_48() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Hub");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterHubAddress("16, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");

		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("10 Gbps");

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");
		
		productListPage.AddproductType("Spoke");
		reportLog("Add Spoke Product");

		modelConfigurationPage.enterSpokeAddress("19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.enterProductResiliency("Protected");

		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectBandwidth("1 Gbps");
		
		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "17", "18");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "34");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");
		
		transactionPage.logOutFromCPQ();
		
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();
	}
	
	
	/*
	 * Add Wave product and apply percentage discount between 0-5%
	 * On_Net-On_Net
	 */
	@Test(priority = 49)
	public void regressionFlow_49() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.clicOnSubmitButton();
		reportLog("Submit Quote");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	  Add Wave product and apply percentage discount between 5 - 10%
	 * On_Net-On_Net
	 */
	@Test(priority = 50)
	public void regressionFlow_50() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	   Add Wave product and apply percentage discount between 10 - 15%
	 * On_Net-On_Net
	 */
	@Test(priority = 51)
	public void regressionFlow_51() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "12", "13");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	  Add Wave product and apply percentage discount > 15%
	 * On_Net-On_Net(IGMAD = 20-25)
	 */
	@Test(priority = 52)
	public void regressionFlow_52() throws Exception {

		String oppID = "261511";

		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.addRequiredDetails();

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	   Add Wave product and apply percentage discount > 15%
	 * On_Net-On_Net(IGMAD = 25 - 30)
	 */
	@Test(priority = 53)
	public void regressionFlow_53() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	   Add Wave product and apply percentage discount > 15%
	 * On_Net-On_Net(IGMAD >30)
	 */
	@Test(priority = 54)
	public void regressionFlow_54() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses(aEndAddress, bEndAddress);

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "35");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}
	
	
	
	/*
	 * Add Wave product and apply percentage discount between 0-5%
	 * On_Net-NearNet
	 */
	@Test(priority = 55)
	public void regressionFlow_55() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014", "19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");

		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "2", "3");
		reportLog("Apply Discount On Quote");

		transactionPage.clicOnSubmitButton();
		reportLog("Submit Quote");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	  Add Wave product and apply percentage discount between 5 - 10%
	 * On_Net-Near_Net
	 */
	@Test(priority = 56)
	public void regressionFlow_56() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014", "19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "7", "8");
		reportLog("Apply Discount On Quote");

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	   Add Wave product and apply percentage discount between 10 - 15%
	 * On_Net-Near_Net
	 */
	@Test(priority = 57)
	public void regressionFlow_57() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014", "19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "12", "13");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

		transactionPage.submitQuote();
		reportLog("Submit Quote");

		transactionPage.logOutFromCPQ();
		reportLog("Logout From CPQ");

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	  Add Wave product and apply percentage discount > 15%
	 * On_Net-Near_Net(IGMAD = 20-25)
	 */
	@Test(priority = 58)
	public void regressionFlow_58() throws Exception {

		String oppID = "261511";

		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014", "19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.addRequiredDetails();

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "22");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	   Add Wave product and apply percentage discount > 15%
	 * On_Net-Near_Net(IGMAD = 25 - 30)
	 */
	@Test(priority = 59)
	public void regressionFlow_59() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014", "19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
		//reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "28");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user1, financeapprover_password1);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote as Sales User");

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}

	/*
	   Add Wave product and apply percentage discount > 15%
	 * On_Net-Near_Net(IGMAD >30)
	 */
	@Test(priority = 60)
	public void regressionFlow_60() throws Exception {

		String oppID = "261511";
		test_01_Navigate_From_C4C_To_CPQ(oppID);

		opportunityPage.switchWindow(oppName);

		opportunityPage.addNewQuoteFromOpportunity();
		reportLog("Add new Quote from opportunity having id :" + oppID);

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

		productListPage.AddproductType("Wave");
		reportLog("Adding EtherNet Line Product");

		modelConfigurationPage.enterAddresses("16, Rue Friant, Paris, France, Île-de-France, 75014", "19, Rue Friant, Paris, France, Île-de-France, 75014");

		modelConfigurationPage.checkConnectivity();
		reportLog("Click on Update button then CheckConnectivity button");
		
		modelConfigurationPage.navigateToSiteDetailPage();
		
		modelConfigurationPage.selectInterface("Ethernet");
		
	//	reportLog("Select Random bandwidth from the dropdown");
		modelConfigurationPage.selectWaveBandwidth("1 Gbps");

		modelConfigurationPage.enterProductResiliency("Protected");		

		modelConfigurationPage.verifyButtonsPresent();

		modelConfigurationPage.saveQuoteButton();
		reportLog("Click on save to quote button");


		modelConfigurationPage.providePoAPrice(quoteName);

		transactionPage.applyDiscount("PERCENTAGE", "16", "17");
		reportLog("Apply Discount On Quote");

		transactionPage.addRequiredDetails();

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

		transactionPage.uploadMarginAndSubmit("Namita Singh", "35");
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);
		reportLog("Login to CPQ application as Governance user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.clickOnDefaultView();
		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Pending Governance Approval");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);
		reportLog("Open Quote For Governance approval");

		transactionPage.clickApproveButton();
		reportLog("Approve the quote");

		transactionPage.logOutFromCPQ();

		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(sales_user, salesuser_pass);
		reportLog("Login to CPQ as Sales user");

		productListPage.clickOnOrderManagerLink();

		commerceManagementPage.verifyQuoteStatusInApproverView(quoteName, "Approved");
		commerceManagementPage.openQuoteForReviewApproverView(quoteName);

		transactionPage.sendProposal("Email");
		reportLog("Generate and Send Proposal");

		transactionPage.acceptQuote();
		reportLog("Apply BCN and Accept the Quote");

		reportLog("Create Order");
		transactionPage.createOrder();

	}


}
