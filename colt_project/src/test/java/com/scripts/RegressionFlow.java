package com.scripts;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.codoid.products.exception.FilloException;
import com.util.DriverTestCase;
import com.util.ExcelDataBaseConnector;
import com.util.Utilities;

public class RegressionFlow extends DriverTestCase {

	private String quoteName;
	private String oppID;

	@BeforeClass
	public void doLogin() throws Exception {
		setUp();
		listPriceConnection = ExcelDataBaseConnector.createConnection("List_Price_Small");
		oppID = c4cpropertyReader.readApplicationData("opportutnityID");

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

	@Test
	public void test_01_Navigate_From_C4C_To_CPQ() {
		getWebDriver().navigate().to(c4c_url);
		reportLog("Navigating C4C Application URl: " + c4c_url);
		reportLog("**************"+c4c_userName+c4cappPage);
		c4cappPage._waitForJStoLoad();
		c4cappPage.loginInToC4CApplication(c4c_userName, c4c_Password);
		c4cappPage.verifyTitle("Home - SAP Hybris Cloud for Customer");
	}

	@Test
	public void test_02_Add_Edit_Delete_EthernetLine() throws Exception {
		// colt_DemoHelper.verifyTitle("Home - SAP Hybris Cloud for Customer");

		quoteName = "Auto_Regression_" + Utilities.getRandomInteger(1, 9999);

		c4cappPage.goToOpportunityPage();

		opportunityPage.searchOpportunity(oppID);
		String opportunityName = opportunityPage.getNameOfOpportunity(Integer.parseInt(oppID));
		String accountName = opportunityPage.getAccountName(Integer.parseInt(oppID));
		opportunityPage.selectParticularOpportunity(Integer.parseInt(oppID));
		opportunityPage.addNewQuoteFromOpportunity();

		opportunityPage.switchWindow("Transaction");
		reportLog("Switching on CQP tab");
		transactionPage.verifyOpportunity_And_AccountInfoInCPQ(oppID, opportunityName, accountName);

		transactionPage.sendKeys(transactionPage.quoteName, quoteName);
		reportLog("Enter QuoteName: " + quoteName);

		productListPage.AddproductType("Ethernet");
		modelConfigurationPage.addDetailsfromEthernetLineTositedetails();
		transactionPage.navigateFromCopyLineItemsToDelete();

	}

	@Test
	public void test_03__AddingHubSpoke_And_Apply_Discount() throws InterruptedException {

		reportLog("Click on to AddProduct button");

		reportLog("Verifying the title 'Product List'");

		productListPage.AddproductType("Hub");
		reportLog("Add Hub Product");

		c4cappPage.verifyTitle("Model Configuration");
		reportLog("Verifying the title 'Model Configuration'");

		modelConfigurationPage.AddDetailsHubSpokeTositedetails();

		transactionPage.applyDiscount(DiscountType.PERCENTAGE.toString(), "40", "40");

		reportLog("After applying discount QuoteNRC Discount is: " + transactionPage.getQuoteDiscount("NRC"));
		reportLog("After applying discount QuoteMRC Discount is: " + transactionPage.getQuoteDiscount("MRC"));

		transactionPage.submitQuote();
		reportLog("After Submitting Quote Approval Msg is: " + transactionPage.getApprovalMsg());

	}

	@Test
	public void test_04_ApproveQuoteByDealPriceUser() {

		transactionPage.logOutFromCPQ();
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(deal_user, deal_user_password);

		productListPage.clickOnOrderManagerLink();

		c4cappPage.verifyTitle("Commerce Management");
		reportLog("Verifying the title Commerce Management");

		commerceManagementPage.verifyQuoteStatus(quoteName,"Deal Pricing Review");
		commerceManagementPage.openQuoteForReview(quoteName);
		reportLog("Open Quote For Deal Pricing Review");

		transactionPage.uploadMarginAndSubmit(deal_user);
		List<String> names = transactionPage.getPendingApproverNames();
		reportLog("Approvers Name: " + names);

	}

	@Test
	public void test_05_ApproveQuoteBySalesApprover() {

		transactionPage.logOutFromCPQ();
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(salesapprover_user, salesapprover_password);

		productListPage.clickOnOrderManagerLink();

		c4cappPage.verifyTitle("Commerce Management");
		reportLog("Verifying the title Commerce Management");

		//commerceManagementPage.verifyQuoteStatus(quoteName,"Deal Pricing Review");
		commerceManagementPage.openQuoteForReview(quoteName);
		reportLog("Open Quote For Deal Pricing Review");

		transactionPage.approveQuote();
		reportLog("Approve quote by Sales Approver");
	}

	@Test
	public void test_06_ApproveQuoteByFinanceApprover() {

		transactionPage.logOutFromCPQ();
		getWebDriver().navigate().to(application_url);

		c4cappPage.loginInToCPQApplication(financeapprover_user, financeapprover_password);

		productListPage.clickOnOrderManagerLink();

		c4cappPage.verifyTitle("Commerce Management");
		reportLog("Verifying the title Commerce Management");

		commerceManagementPage.openQuoteForReview(quoteName);
		reportLog("Open Quote For Deal Pricing Review");

		transactionPage.approveQuote();
		reportLog("Approve quote by Finance Approver");

	}

}
