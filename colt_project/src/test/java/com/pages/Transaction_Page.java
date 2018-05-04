package com.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.constants.GlobalConstant;
import com.util.BasePage;
import com.util.DriverTestCase.BuildingType;
import com.util.Utilities;

public class Transaction_Page extends BasePage {

	public Transaction_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "readonly_1_currency_t")
	public WebElement currency;

	@FindBy(xpath = "//*[@id='field_wrapper_1_pricingSegment_t']/div[1]")
	public WebElement segment;

	@FindBy(id = "quoteName_t")
	public WebElement quoteName;

	@FindBy(id = "title_t")
	public WebElement quoteDescription;

	@FindBy(id = "add_product")
	public WebElement addProductButton;

	@FindBy(id = "save")
	public WebElement saveButton;

	@FindBy(id = "//*[@name='pricingSegment_t']")
	public WebElement smeSegment;

	@FindBy(xpath = "//input[@name='_line_item_list_all']")
	public WebElement lineitem;

	@FindBy(xpath = "//*[text()='Copy Line Items']")
	public WebElement copyLineItems;

	@FindBy(name = "num_of_copies2")
	public WebElement numofcopies2;

	@FindBy(xpath = "//*[text()='Copy']")
	public WebElement copyPopup;

	@FindBy(xpath = "//*[@*='Reconfigure']")
	public WebElement reconfigure;

	@FindBy(id = "serviceBandwidth")
	public WebElement serviceBandwidth;

	@FindBy(id = "resiliency")
	public WebElement resiliency;

	@FindBy(id = "siteAddressAEnd")
	public WebElement siteAddressAEnd;

	@FindBy(id = "siteAddressBEnd")
	public WebElement siteAddressBEnd;

	@FindBy(id = "update")
	public WebElement update;

	@FindBy(xpath = "//a[@name='next']")
	public WebElement checkConnectivityButton;

	@FindBy(id = "save")
	public WebElement savebutton;

	@FindBy(id = "return_to_c4c")
	public WebElement returnToC4CButton;

	@FindBy(id = "download_quote_excel")
	public WebElement quoteDownloadButton;

	@FindBy(xpath = "//td[text()='Get Contact Details']")
	public List<WebElement> getContactDetailButton;

	@FindBy(xpath = "//*[text()='Delete Line Items']")
	public WebElement delete;

	@FindBy(xpath = "//table[@id='myTable']//tr/td[1]/input")
	public WebElement contactOptions;

	@FindBy(xpath = "//input[@value='Submit']")
	public WebElement submitButton;

	@FindBy(xpath = "//div[@title='Reconfigure']//parent::td/preceding-sibling::td/div")
	public List<WebElement> editLineItem;

	@FindBy(xpath = "//table[@id='line-item-grid']/tbody/tr")
	public List<WebElement> lineItemType;

	public static By getLineItemType(int temp) {
		return By.xpath("//table[@id='line-item-grid']/tbody/tr[" + temp + "]/td[5]");
	}

	@FindBy(id = "aEndSiteID_l")
	public WebElement asiteId;

	@FindBy(id = "aEndCompanyName")
	public WebElement acompanyName;

	@FindBy(id = "aendDepartmentBranchAEnd_l")
	public WebElement branchId;

	@FindBy(id = "registrationNumberSIRETISRAAEnd_l")
	public WebElement asiretId;

	@FindBy(id = "bEndSiteID_l")
	public WebElement bsiteId;

	@FindBy(id = "bEndCompanyName")
	public WebElement bcompanyName;

	@FindBy(id = "bendDepartmentBranch")
	public WebElement department;

	@FindBy(id = "registrationNumberSIRETISRABEND_l")
	public WebElement bsiretID;

	@FindBy(name = "aEndCabinetType_l")
	public WebElement acabinetType;

	@FindBy(id = "aEndCABIDDDFOrFrameID_l")
	public WebElement cabinetText;

	@FindBy(name = "bEndCabinetType_l")
	public WebElement bcabinetType;

	@FindBy(id = "bEndCABIDDDFOrFrameID_l")
	public WebElement bcabinetText;

	@FindBy(name = "contactTitleAEnd_l")
	public WebElement contactTitle;

	@FindBy(id = "aEndContactFirstName")
	public WebElement firstContactName;

	@FindBy(name = "aEndContactLastName")
	public WebElement contactLastName;

	@FindBy(id = "contactTitleBEnd_l")
	public WebElement bcontactTitle;

	@FindBy(name = "bEndContactFirstName")
	public WebElement bFirstName;

	@FindBy(id = "bEndContactLastName")
	public WebElement bcontactLastName;

	@FindBy(id = "aEndContactEmail")
	public WebElement aemailContact;

	@FindBy(name = "bEndContactMobileNumber")
	public WebElement bmobileNumber;

	@FindBy(id = "aEndContactMobileNumber")
	public WebElement amobileNumber;

	@FindBy(name = "aEndContactTelephone")
	public WebElement atelephone;

	@FindBy(id = "bEndContactEmail")
	public WebElement bcontactEmail;

	@FindBy(id = "bEndContactTelephone")
	public WebElement bTelephone;

	@FindBy(xpath = "(//*[@title='Delete'])[1]")
	public WebElement deleteIconLineItems;

	@FindBy(xpath = "//*[text()='OK']")
	public WebElement okButton;

	@FindBy(xpath = "//*[@*='Log out']")
	public WebElement logout;

	@FindBy(xpath = "//*[text()='Quote']")
	public WebElement gotoquote;

	@FindBy(name = "nRCDiscountType_t")
	public WebElement discount;

	@FindBy(xpath = "//*[text()='Percentage Off']")
	public WebElement percentageoff;

	@FindBy(id = "discountNRCPerc_t")
	public WebElement nrcdiscount;

	@FindBy(id = "discountMRCPerc_t")
	public WebElement mrcdiscount;

	@FindBy(xpath = "//*[text()='Deal Pricing']")
	public WebElement dealPricing;

	@FindBy(name = "dealPricingTeamMembers_t")
	public WebElement dealPricingTeamMember;

	@FindBy(xpath = "//*[text()='Assign Quote']")
	public WebElement assignQuote;

	@FindBy(name = "_file_uploadFile_t")
	public WebElement fileupload;

	@FindBy(name = "upload_margin")
	public WebElement uploadmargin;

	@FindBy(name = "dealBackground_t")
	public WebElement dealBackground;

	@FindBy(name = "technicalSolution_t")
	public WebElement technicalSolution;

	@FindBy(name = "competitors_t")
	public WebElement competitors;

	@FindBy(name = "pricePositioning_t")
	public WebElement pricePositioning;

	@FindBy(name = "commercialRisk_t")
	public WebElement commercialRisk;

	@FindBy(xpath = "//*[text()='Approval']")
	public WebElement approval;

	@FindBy(xpath = "//*[text()='General Information']")
	public WebElement generalInfo;

	@FindBy(xpath = "//a[@class='approve-reason show-loading-dialog']")
	public WebElement clickapproval;

	@FindBy(xpath = "(//a[text()='Submit'])[2]")
	public WebElement submit;

	@FindBy(id = "submit_to_approval")
	public WebElement submitApproval;

	@FindBy(xpath = "//a[@title='Approve']")
	public WebElement approveDealQuote;

	@FindBy(xpath = "//*[text()='Submit']")
	public WebElement submitDealPrice;

	@FindBy(xpath = "//*[@*='Log out']")
	public WebElement logout1;

	@FindBy(name = "username")
	public WebElement username;

	@FindBy(name = "psword")
	public WebElement password;

	@FindBy(name = "log_in")
	public WebElement logIn;

	@FindBy(xpath = "//*[text()='Approval']")
	public WebElement approvaltab;

	@FindBy(xpath = "//*[@*='Approve']")
	public WebElement approve;

	@FindBy(xpath = "(//span[@data-varname='discountMRC_l'])[1]")
	public WebElement quoteDiscountMRC;

	@FindBy(xpath = "(//span[@data-varname='discountNRC_l'])[1]")
	public WebElement quoteDiscountNRC;

	@FindBy(css = ".bm-approval-history ul li")
	public WebElement approvalMsg;

	@FindBys(@FindBy(css = ".bm-approval-status ul li"))
	public List<WebElement> pendingApprovalMsg;

	@FindBy(id = "assign_quote")
	public WebElement assignQuoteButton;
	
	@FindBy(id = "submit_poa_prices")
	public WebElement submitPOAPricess;
	
	
	@FindBy(xpath = "//table[@id='line-item-grid']/tbody//tr[2]//input[@class=' form-input-number ']")
	public List<WebElement> poaField;
	

	@FindBy(id = "_file_uploadFile_t")
	public WebElement browseFileButton;

	@FindBy(id = "_file_acceptanceDocument_t")
	public WebElement proposalFile;

	@FindBy(id = "customerSignedDate_t-button")
	public WebElement signDate;

	@FindBy(id = "confirm_quote_action")
	public WebElement confirmAction;

	@FindBy(xpath = "//a[@class='calendar-today']")
	public WebElement todayDate;

	@FindBy(xpath = "//*[text()='Proposal']")
	public WebElement proposalTab;

	@FindBy(name = "quoteActionAcceptOrReject_t")
	public WebElement accept;

	@FindBy(name = "reasonForStatusWon_t")
	public WebElement acceptReason;

	@FindBy(id = "upload_margin")
	public WebElement uploadMarginButton;

	@FindBy(xpath = "//span[text()='Deal Pricing']")
	public WebElement dealPricingTab;

	@FindBy(xpath = "//span[text()='P& L']")
	public WebElement p_L_Tab;

	@FindBy(xpath = "//a[contains(@href,'/logout.jsp')]")
	public WebElement logOutBtn;

	@FindBy(id = "dealBackgroundTextArea_t")
	public WebElement dealBackgroundInput;

	@FindBy(id = "technicalSolutionTextArea_t")
	public WebElement technicalInput;

	@FindBy(id = "competitorsTextArea_t")
	public WebElement competitorInput;

	@FindBy(id = "pricePositioningTextArea_t")
	public WebElement priceInput;

	@FindBy(id = "commercialRiskTextArea_t")
	public WebElement commercialInput;

	@FindBy(xpath = "//input[@name='submitForReviewCheckbox_t']")
	public WebElement dealPriceReviewCheckBox;

	@FindBy(id = "readonly_1_opportunityName_t")
	public WebElement opportunityName;

	@FindBy(id = "readonly_1_accountName_t")
	public WebElement accountName;

	@FindBy(name = "workflow_t")
	public WebElement workflow;

	@FindBy(name = "toRecepient_t")
	public WebElement emailName;

	@FindBy(name = "_line_item_list_all")
	public WebElement productCheckBox;

	@FindBy(name = "bCNCustomerRefNumberQuote_t")
	public WebElement bcnName;

	@FindBy(name = "bCNPOReferenceQuote_t")
	public WebElement bcnReference;

	@FindBy(id = "bcnRadio_new_bcn")
	public WebElement bcnType;

	@FindBy(id = "apply")
	public WebElement apply;

	@FindBy(css = "#field_wrapper_1_opportunityID_t div")
	public WebElement opportunityId;

	@FindBy(xpath = "//span[text()='Approval History[Submit]']")
	public WebElement afterSubmitMsg;

	@FindBy(xpath = "//*[@id=\"field_wrapper_1_opportunityID_t\"]/div[1]/span")
	public WebElement opportunityIdText;

	@FindBy(xpath = "//*[@id=\"field_wrapper_1_oCN_t\"]/div[1]/span")
	public WebElement accoundIdText;

	@FindBy(id = "billing_information")
	public WebElement billingInfoButton;

	@FindBy(id = "reset_discount")
	public WebElement resetDiscount;

	@FindBy(id = "calculate_discount")
	public List<WebElement> calculateDiscountButton;

	@FindBy(id = "change_currency")
	public WebElement changeCurrencyButton;

	public static By getLinkElement(String text) {
		return By.xpath("//a[text()='" + text + "']");
	}

	public void enterDetailsWhileUploadMargin(String data) {
		sendKeys(transactionPage.dealBackgroundInput, data);
		sleepExecution(1);
		sendKeys(transactionPage.technicalInput, data);
		sleepExecution(1);
		sendKeys(transactionPage.competitorInput, data);
		sleepExecution(1);
		sendKeys(transactionPage.priceInput, data);
		sleepExecution(1);
		sendKeys(transactionPage.commercialInput, data);
		sleepExecution(2);
	}

	public void enterQuoteNameDescription(String name, String desc) {
		verifyElementPresent(quoteName);
		sendKeys(transactionPage.quoteName, name);
		reportLog("Enter QuoteName: " + name);
		verifyElementPresent(quoteDescription);
		sendKeys(transactionPage.quoteDescription, desc);
		reportLog("Enter Description: " + desc);
	}

	public String getUICurrencyType() {
		String type = currency.getText();
		return type;
	}

	public String getUISegment() {
		String type = segment.getText();
		return type;
	}

	public void clickOnAddProductBtn() {
		javascriptScrollIntoView(addProductButton);
		click(addProductButton);
	}

	public Transaction_Page verifyQuoteDetailsElements() {

		Assert.assertEquals(opportunityIdText.isDisplayed(), true, "Opportunity id is not present in quote detail");
		Assert.assertEquals(accoundIdText.isDisplayed(), true, "Accound id is not present in quote detail page");
		Assert.assertEquals(savebutton.isDisplayed(), true, "Save button is not present in quote detail page");
		Assert.assertEquals(returnToC4CButton.isDisplayed(), true,
				"Return To C4CButton is not present in quote detail page");
		Assert.assertEquals(quoteDownloadButton.isDisplayed(), true,
				"Quote download button is not present in quote detail page.");
		scrollDownToBottom();
		Assert.assertEquals(billingInfoButton.isDisplayed(), true,
				"Billing info button is not present in quote detail page");
		Assert.assertEquals(addProductButton.isDisplayed(), true,
				"Add product button is not present in quite detail page");
		Assert.assertEquals(calculateDiscountButton.size(), 2,
				"calculate Discount Buttonis not present in quote detail page");
		Assert.assertEquals(resetDiscount.isDisplayed(), true,
				"Reset Discount button is not present in quite detail page");
		Assert.assertEquals(getContactDetailButton.size(), 2, "Get Contact details button is not present");

		return PageFactory.initElements(getWebDriver(), Transaction_Page.class);
	}
	
	
	public String getQuoteID()
	{
		return driver.findElement(By.xpath("//span[text()='Quote ID:']/parent::label/following-sibling::div/div/span")).getText();
	}

	public void navigateFromCopyLineItemsToDelete() throws InterruptedException {

		// Click on SquareBox for Line Item
		javascriptScrollIntoView(lineitem);
		waitAndClick(lineitem);
		reportLog("Click on Line Item button");

		// Click on SquareBox for Line Item
		_waitForJStoLoad();
		copyLineItems.click();
		reportLog("Click on Copy Line Item button");

		_waitForJStoLoad();
		sendKeys(numofcopies2, "2");
		reportLog("Enter 2 ");

		// click on button in popup
		copyPopup.click();
		reportLog("Click on Copy Dialog button");

		// Click on Reconfigure icon
		javascriptScrollIntoView(reconfigure);
		javascriptButtonClick(reconfigure);

		// Select ServiceBandwidth dropdown arrow
		serviceBandwidth.click();
		reportLog("Click on Bandwidth button");

		selectDropDownByText(modelConfigurationPage.serviceBandwidth, "20 Gbps");
		reportLog("Select BandWidth: 20 Gbps");
		_waitForJStoLoad();
		update.click();
		reportLog("Click Update");

		// Click on Check Connectivity Button
		_waitForJStoLoad();
		checkConnectivityButton.click();
		reportLog("Click on Check Connecivity Button");

		// Click on Save Button
		_waitForJStoLoad();
		savebutton.click();
		reportLog("Click on Save");

		// Click on SquareBox for Line Item
		// Click On Delete icon
		_waitForJStoLoad();
		javascriptScrollIntoView(deleteIconLineItems);
		deleteIconLineItems.click();
		reportLog("Click on Deleteline Items icon");
		_waitForJStoLoad();
		okButton.click();
		reportLog("Click on OK");
		_waitForJStoLoad();

	}

	public void applyDiscount(String type, String mrc, String nrc) {
		waitForAjaxRequestsToComplete();
		gotoquote.click();
		reportLog("Click on to Add to Quote button");
		DiscountType choice = DiscountType.valueOf(type);
		// discount.click();
		reportLog("Click on to Discount Tab");

		switch (choice) {
		case PERCENTAGE:
			// selectDropDownByIndex(discount, 2);
			reportLog("Select Percentage Discount");
			_waitForJStoLoad();
			waitForAjaxRequestsToComplete();
			nrcdiscount.click();
			nrcdiscount.clear();
			sleepExecution(2);
			nrcdiscount.sendKeys(nrc);
			sleepExecution(2);
			reportLog("Enter NRC " + nrc + "%  Percentage Discount");
			mrcdiscount.click();
			mrcdiscount.clear();
			sleepExecution(2);
			mrcdiscount.sendKeys(mrc);
			sleepExecution(3);
			reportLog("Enter NRC " + mrc + "%  Percentage Discount");
			break;

		case TARGET:
			break;

		case AMOUNT:
			break;
		}
		javascriptButtonClick(calculateDiscountButton.get(0));
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		javascriptButtonClick(savebutton);
		reportLog("Click Save Button");
		_waitForJStoLoad();

	}

	public String getQuoteDiscount(String choice) {
		String discount = null;
		if (choice.equals("NRC"))
			discount = quoteDiscountNRC.getText();
		if (choice.equals("MRC"))
			discount = quoteDiscountMRC.getText();
		return discount;
	}

	public void submitQuote() {
		javascriptButtonClick(approval);
		reportLog("Click Approve Button");
		// verifyElementPresent(dealPriceReviewCheckBox);
		// dealPriceReviewCheckBox.click();
		reportLog("Select Submit Review CheckBox Button");
		javascriptButtonClick(submitApproval);
		reportLog("Select Submit Button");
		_waitForJStoLoad();
		javascriptButtonClick(submitDealPrice);
		_waitForJStoLoad();
	}

	public void clicOnSubmitButton()
	{
		javascriptButtonClick(approval);
		reportLog("Click Approve Button");
		
		javascriptButtonClick(submitApproval);
		reportLog("Click Submit Button");
		
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		
	}
	
	public String getApprovalMsg() {
		verifyElementPresent(afterSubmitMsg);
		return afterSubmitMsg.getText();
	}

	public List<String> getPendingApproverNames() {
		int c = pendingApprovalMsg.size();
		List<String> approvers = new ArrayList<String>();
		reportLog("Numnber of Approver are: " + c);
		for (WebElement tmp : pendingApprovalMsg) {
			approvers.add(tmp.getText());
		}
		return approvers;
	}

	public void uploadMarginAndSubmit(String dealMemeber) {
		javascriptButtonClick(p_L_Tab);
		reportLog("Click on P & L Tab");

		reportLog("Select Deal Pricing Team Member");
		selectDropDownByValue(dealPricingTeamMember, dealMemeber);

		javascriptButtonClick(assignQuoteButton);
		reportLog("Click on Assign Button");
		waitForAjaxRequestsToComplete();
		System.out.println(System.getProperty("user.dir"));
		System.out.println(GlobalConstant.DEAL_PRICE_UPLOAD_SHEET);
		browseFileButton.sendKeys(GlobalConstant.DEAL_PRICE_UPLOAD_SHEET);
		reportLog("Browse Margin File");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();

		javascriptButtonClick(uploadMarginButton);
		reportLog("Click on Upload Button");

		enterDetailsWhileUploadMargin("Test_" + Utilities.randomString(4));

		clickApproveButton();

	}
	
	
	
	public void assignQuoteAndProvidePricess(String dealMemeber) throws IOException, InterruptedException
	{
		javascriptButtonClick(p_L_Tab);
		reportLog("Click on P & L Tab");

		reportLog("Select Deal Pricing Team Member");
		selectDropDownByValue(dealPricingTeamMember, dealMemeber);

		javascriptButtonClick(assignQuoteButton);
		reportLog("Click on Assign Button");
		waitForAjaxRequestsToComplete();
		
		poaField.get(0).sendKeys("100");
		poaField.get(1).sendKeys("100");
		
		javascriptButtonClick(submitPOAPricess);
		alertOK();
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		
		
	}

	public void applyBcn() {
		javascriptButtonClick(productCheckBox);
		waitForAjaxRequestsToComplete();
		javascriptButtonClick(driver.findElement(getLinkElement("Billing Information")));
		_waitForJStoLoad();
		sleepExecution(2);
		sendKeys(bcnName, "test");
		sendKeys(bcnReference, "ttt");
		javascriptButtonClick(bcnType);
		javascriptButtonClick(apply);
		waitForAjaxRequestsToComplete();

	}

	public void clickButtonWithPopup(String text) {
		try {
			javascriptButtonClick(driver.findElement(getLinkElement(text)));
		} catch (Throwable e) {
			System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
		}
	}

	public void sendProposal(String type) {
		applyBcn();
		javascriptButtonClick(proposalTab);
		waitForAjaxRequestsToComplete();
		clickButtonWithPopup("Generate Proposal");
		acceptPopup();
		waitForAjaxRequestsToComplete();
		/*switchNewWindow();
		driver.close();*/
		waitForAjaxRequestsToComplete();
		driver.switchTo().defaultContent();
		javascriptButtonClick(driver.findElement(getLinkElement("Save Document")));
		selectDropDownByText(workflow, type);
		waitForAjaxRequestsToComplete();
		emailName.sendKeys("Keshavanand.Upadhyay@colt.net");
		sleepExecution(2);
		clickButtonWithPopup("Send Proposal");
		acceptPopup();
		waitForAjaxRequestsToComplete();
		_waitForJStoLoad();

	}

	public void acceptQuote() {
		selectDropDownByText(accept, "Accept");
		waitForAjaxRequestsToComplete();
		selectDropDownByIndex(acceptReason, 2);
		proposalFile.sendKeys(GlobalConstant.proposalDocument);
		reportLog("Browse Margin File");
		_waitForJStoLoad();
		waitForAjaxRequestsToComplete();
		javascriptButtonClick(signDate);
		waitForAjaxRequestsToComplete();
		javascriptButtonClick(todayDate);
		javascriptButtonClick(confirmAction);
		waitForAjaxRequestsToComplete();

	}

	public void enterLineItemDetails() {

		System.out.println(lineItemType.size());
		for (int i = 1; i < lineItemType.size(); i++) {

			String type[] = driver.findElement(getLineItemType(i)).getText().split(",");
			String produtType = type[0];
			if (produtType.equals("Ethernet Line")) {
				javascriptButtonClick(editLineItem.get(0));
				waitForAjaxRequestsToComplete();
				sleepExecution(3);
				sendKeys(asiteId, "1234");
				sendKeys(acompanyName, "Saksoft");
				sendKeys(branchId, "12");
				sendKeys(asiretId, "1111");
				sendKeys(bsiteId, "1222");
				sendKeys(bcompanyName, "Saksoft");
				sendKeys(department, "testing");
				sendKeys(bsiretID, "222");
				selectDropDownByValue(acabinetType, "New Colt Cabinet");
				waitForAjaxRequestsToComplete();
				sendKeys(cabinetText, "1111");
				selectDropDownByValue(bcabinetType, "New Colt Cabinet");
				waitForAjaxRequestsToComplete();
				sendKeys(bcabinetText, "444");
				_waitForJStoLoad();
				javascriptButtonClick(driver.findElement(getLinkElement("Save")));
				waitForAjaxRequestsToComplete();
				javascriptButtonClick(driver.findElement(getLinkElement("Back")));
				waitForAjaxRequestsToComplete();

			}
			if (produtType.equals("Ethernet Hub1")) {
				javascriptButtonClick(editLineItem.get(1));
				waitForAjaxRequestsToComplete();
				sleepExecution(3);
				sendKeys(asiteId, "1234");
				sendKeys(acompanyName, "Saksoft");
				sendKeys(branchId, "12");
				sendKeys(asiretId, "1111");
				_waitForJStoLoad();
				javascriptButtonClick(driver.findElement(getLinkElement("Save")));
				waitForAjaxRequestsToComplete();
				javascriptButtonClick(driver.findElement(getLinkElement("Back")));
				waitForAjaxRequestsToComplete();
			}

			if (produtType.equals("Ethernet Spoke")) {
				javascriptButtonClick(editLineItem.get(2));
				waitForAjaxRequestsToComplete();
				sendKeys(asiteId, "1234");
				sendKeys(acompanyName, "Saksoft");
				sendKeys(branchId, "12");
				sendKeys(asiretId, "1111");
				_waitForJStoLoad();
				javascriptButtonClick(driver.findElement(getLinkElement("Save")));
				waitForAjaxRequestsToComplete();
				javascriptButtonClick(driver.findElement(getLinkElement("Back")));
				waitForAjaxRequestsToComplete();
			}
			
			i++;

		}

	}

	public void createOrder() {
		_waitForJStoLoad();
		javascriptButtonClick(generalInfo);
		waitForAjaxRequestsToComplete();
		enterLineItemDetails();
		waitForAjaxRequestsToComplete();
		javascriptButtonClick(getContactDetailButton.get(1));
 		switchNewWindow();
		clickOn(contactOptions);
		clickOn(submitButton);
		sleepExecution(3);
		switchWindow("Transaction");
		clickOn(driver.findElement(getLinkElement("Create Order")));
		waitForAjaxRequestsToComplete();
		reportLog("Ordered created successfully");
	}

	public void switchNewWindow() {
		sleepExecution(2);
		waitForAjaxRequestsToComplete();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public void clickApproveButton() {
		javascriptButtonClick(approval);
		reportLog("Click on Approval Tab");
		waitForAjaxRequestsToComplete();
		javascriptButtonClick(approveDealQuote);
		reportLog("Click on Submit button");

	}

	public void approveQuote() {
		javascriptButtonClick(approval);
		reportLog("Click on Approval Tab");
		_waitForJStoLoad();

		clickapproval.click();
		reportLog("Click on Approval Icon");
		_waitForJStoLoad();

	}

	public void clickOnSaveButton() {
		javascriptScrollIntoView(savebutton);
		// waitForElementVisible(oracleQuoteToOrderManagerLink);
		waitAndClick(savebutton);
		_waitForJStoLoad();
		reportLog("Click on Save Button");
	}

	public String getProductPricingValues(String product, String priceColumn) {

		String locator = null;
		if (priceColumn.equals("NRC (net)")) {
			locator = "//*[text()='" + product + "']/../../../following-sibling::td[5]";
		}

		if (priceColumn.equals("MRC (net)")) {
			locator = "//*[text()='" + product + "']/../../../following-sibling::td[9]";
		}
		String price = getText(locator);
		/*
		 * if (price.contains("$")) price = price.replace("$", ""); else if
		 * (price.contains("€")) // Alt 0128 price = price.replace("€", ""); else if
		 * (price.contains("£")) // Alt 156 price = price.replace("£", "");
		 */
		return price;
	}

	public void verify_NRC_MRC_PricesForHubSpoke(String nrcHub, String mrcHub, String mrcSpoke, String nrcSpoke) {
		String nrc_Net_Price_Hub = getProductPricingValues("Connectivity Charge Hub1", "NRC (net)");
		String mrc_Net_Price_Hub = getProductPricingValues("Connectivity Charge Hub1", "MRC (net)");
		String nrc_Net_Price_Spoke = getProductPricingValues("Connectivity Charge Spoke1", "NRC (net)");
		String mrc_Net_Price_Spoke = getProductPricingValues("Connectivity Charge Spoke1", "MRC (net)");

		Assert.assertTrue(nrc_Net_Price_Hub.contains(nrcHub),
				"Prices Error. Actual: " + nrc_Net_Price_Hub + " **** Expected: " + nrcHub);
		reportLog("NRC(net) Price: Actual: " + nrc_Net_Price_Hub + " <br /> Expected: " + nrcHub);
		Assert.assertTrue(mrc_Net_Price_Hub.contains(mrcHub),
				"Prices Error. Actual: " + mrc_Net_Price_Hub + " **** Expected: " + mrcHub);
		reportLog("MRC(net) Price: Actual: " + mrc_Net_Price_Hub + "  <br /> Expected: " + mrcHub);
		Assert.assertTrue(nrc_Net_Price_Spoke.contains(nrcSpoke),
				"Prices Error. Actual: " + nrc_Net_Price_Spoke + " **** Expected: " + nrcSpoke);
		reportLog("NRC(net) Price: Actual: " + nrc_Net_Price_Spoke + " <br /> Expected: " + nrcSpoke);
		Assert.assertTrue(mrc_Net_Price_Spoke.contains(mrcSpoke),
				"Prices Error. Actual: " + mrc_Net_Price_Spoke + " **** Expected: " + mrcSpoke);
		reportLog("MRC(net) Price: Actual: " + mrc_Net_Price_Spoke + "  <br /> Expected: " + mrcSpoke);

	}

	public void verify_NRC_MRC_PricesForHub(String nrcHub, String mrcHub) {
		String nrc_Net_Price_Hub = getProductPricingValues("Connectivity Charge Hub1", "NRC (net)");
		String mrc_Net_Price_Hub = getProductPricingValues("Connectivity Charge Hub1", "MRC (net)");

		Assert.assertTrue(nrc_Net_Price_Hub.contains(nrcHub),
				"Prices Error. Actual: " + nrc_Net_Price_Hub + " **** Expected: " + nrcHub);
		reportLog("NRC(net) Price: Actual: " + nrc_Net_Price_Hub + " <br /> Expected: " + nrcHub);
		Assert.assertTrue(mrc_Net_Price_Hub.contains(mrcHub),
				"Prices Error. Actual: " + mrc_Net_Price_Hub + " **** Expected: " + mrcHub);
		reportLog("MRC(net) Price: Actual: " + mrc_Net_Price_Hub + "  <br /> Expected: " + mrcHub);

	}

	public void verifyOpportunity_And_AccountInfoInCPQ(String oppId, String nameOpp, String accName) {
		verifyElementText(opportunityName, nameOpp);
		reportLog("Opportunity Name [" + nameOpp + "] is showing correct In CPQ");
		verifyElementText(accountName, accName);
		reportLog("Account Name [" + accName + "] is showing correct In CPQ");
		verifyElementText(opportunityId, oppId);
		reportLog("Opportunity ID [" + oppId + "] is showing correct In CPQ");
	}

	public void verify_NRC_MRC_Prices(String nrc, String mrc) {
		String nrc_Net_Price = getProductPricingValues("Connectivity Charge", "NRC (net)");
		String mrc_Net_Price = getProductPricingValues("Connectivity Charge", "MRC (net)");
		Assert.assertTrue(nrc_Net_Price.contains(nrc),
				"Prices Error. Actual: " + nrc_Net_Price + " **** Expected: " + nrc);
		reportLog("NRC(net) Price: Actual: " + nrc_Net_Price + " <br /> Expected: " + nrc);
		Assert.assertTrue(mrc_Net_Price.contains(mrc),
				"Prices Error. Actual: " + mrc_Net_Price + " **** Expected: " + mrc);
		reportLog("MRC(net) Price: Actual: " + mrc_Net_Price + "  <br /> Expected: " + mrc);

	}

}
