
package testPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import objectRepository.CustomerCreation_Obj;
import objectRepository.LoginPage_Obj;
import objectRepository.NormalSaleGoldandSilver_Obj;
import testData.CommonData;
import testData.CustomerCreation_TestData;
import testData.Utility_TestData;
import utilPack.AppUtilities;
import utilPack.BasePge;


public class CustomerCreation extends BasePge {

	BasePge base;
	Assert asrt;
	AppUtilities appUtils;



	Login login = new Login(driver); 
	//POC_TestData poctestdata = new POC_TestData();
	LoginPage_Obj LoginPageObj=new LoginPage_Obj();
	Utility_TestData UtilityTestData = new Utility_TestData();
	CustomerCreation_Obj CustomerCreationObj=new CustomerCreation_Obj();
	CustomerCreation_TestData CustomerCreationTestData = new CustomerCreation_TestData();
	NormalSaleGoldandSilver_Obj NormalSaleGoldAndSilverObj = new NormalSaleGoldandSilver_Obj();

	public CustomerCreation(WebDriver driver) {
		super(driver);
		base = new BasePge(driver);
		appUtils = new AppUtilities(base); 

	}


	// <summary>
	// Test Case Title : Creating retail customer with mandatory fields
	// Automation ID : TC128
	//Author: Nandagopan 
	// </summary>

	public void TC128_CustomerCreation() throws Exception
	{
		//Step 1: Login to POS
		login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);

		Thread.sleep(5000);

		//Step 2: Navigate to the Transaction page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		appUtils.HamBurgerButtonClick("iconShop");


		//Step 3: Click on add customer
		base.buttonClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
		Thread.sleep(3000);

		// Step 4:Click on create new customer
		base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreationTestData.TC128_FirstName );
		String CustomerFirstName = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"), CustomerCreationTestData.TC128_LastName );
		String CustomerLastName =  base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),CustomerCreationTestData.TC128_PhoneNumber);
		String CustomerPhoneNumber =  base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));

		//Select the Preferred Language, Religion Code, Receipt language Preference
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreationTestData.TC128_CustomerReceiptlanguage);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreationTestData.TC128_CustomerReligion);
		base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"));
		base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreationTestData.TC128_CustomerPrefLanguage);

		// Step 6: Click Save button
		//Expected Condition:Verify whether the details given is reflected in ERP & POS
		base.scrollToElement(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
		base.buttonClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails")); 
		CustomerPhoneNumber = appUtils.RetryGenerateNewPhoneNumber(CustomerPhoneNumber);
		List<String> CustomerData= Arrays.asList(CustomerCreationTestData.TC128_FirstName,CustomerCreationTestData.TC128_LastName,CustomerCreationTestData.TC128_PhoneNumber,CustomerCreationTestData.TC128_CustomerReceiptlanguage,CustomerCreationTestData.TC128_CustomerReligion,CustomerCreationTestData.TC128_CustomerPrefLanguage);
		System.out.println(CustomerData);

		// Wait until the element is present AND visible
		WebElement customerIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
				CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount")));
		base.scrollToElement(CustomerCreationObj.Ele_CustomerIDLabel("text: customerAccount"));
		Thread.sleep(3000);
		String NewCustomerId = customerIdElement.getText().trim();
		System.out.println("Customer ID: " + NewCustomerId);

		//6.1 Check whether if Reflected in POS.
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
		appUtils.HamBurgerButtonClick("iconShop");
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Edt_Name("Search"));
		Thread.sleep(2000);
		base.setData(NormalSaleGoldAndSilverObj.Edt_Name("Search"),NewCustomerId);
		Thread.sleep(3000);
		base.excuteJsClick(NormalSaleGoldAndSilverObj.Ele_SearchList("Customers"));
		base.Hover(CustomerCreationObj.Ele_Customer(NewCustomerId));
		base.buttonClick(CustomerCreationObj.Ele_Customer(NewCustomerId));
		Thread.sleep(2000);
		base.buttonClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions(Utility_TestData.MenuBarOptionsCustomerProfile[3]));
		Thread.sleep(3000);
		String ExpectedCustomerFirstName    = base.GetValue(CustomerCreationObj.Ele_Name("customerFirstNameInput"));
		String ExpectedCustomerLastName     = base.GetValue(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
		String ExpectedCustomerPhoneNumber  = base.GetText(CustomerCreationObj.Ele_Phone());
		String ExpectedCustomerReceiptLang  = base.GetText(CustomerCreationObj.Ele_RecieptLang("customerLanguageOptions","en-IN"));
		String ExpectedCustomerReligion     = base.GetText(CustomerCreationObj.Ele_RecieptLang("RegionCode","HND"));
		String ExpectedCustomerPrefLang     = base.GetText(CustomerCreationObj.Ele_RecieptLang("PreferredLanguage","109"));

		//for Getting validating in ERP
		String ERPactualReceiptLangCode = base.GetValue(CustomerCreationObj.Ele_RecieptLang("customerLanguageOptions","en-IN"));
		String ERPactualReligionCode    = base.GetValue(CustomerCreationObj.Ele_RecieptLang("RegionCode","HND"));
		String ERPactualPrefLangCode    = base.GetValue(CustomerCreationObj.Ele_RecieptLang("PreferredLanguage","109"));

		Assert.assertEquals(CustomerFirstName,ExpectedCustomerFirstName, "First Name on Customer creation page Of POS "+CustomerFirstName+"is mismatch with first name "+ExpectedCustomerFirstName+" in customer Edit page of POS");
		Assert.assertEquals(CustomerLastName,ExpectedCustomerLastName, "Last Name on Customer Creation page of POS"+CustomerLastName+"is mismatch with Last Name in Customer Edit page of POS"+ExpectedCustomerLastName+"");
		Assert.assertEquals(CustomerPhoneNumber,ExpectedCustomerPhoneNumber,"Phone Number on Customer Creation page of POS"+CustomerPhoneNumber+"is mismatch with Phone Number in Customer Edit page"+ExpectedCustomerPhoneNumber+"");
		Assert.assertEquals(CustomerCreationTestData.TC128_CustomerReceiptlanguage,ExpectedCustomerReceiptLang,"Receipt Language on customer creation page");
		Assert.assertEquals(CustomerCreationTestData.TC128_CustomerReligion,ExpectedCustomerReligion,"Religion mismatch!");
		Assert.assertEquals(CustomerCreationTestData.TC128_CustomerPrefLanguage,ExpectedCustomerPrefLang,"Preferred Language mismatch!");
		System.out.println(" Customer data successfully validated in POS");

		//6.2:Check if it is Reflected in ERP.
		base.newWindow(1);
		driver.get(UtilityTestData.ERPURL);

		//Search All Customers
		//base.buttonClick(NormalSaleGoldAndSilverObj.Btn_ViewMore("button-commandRing Find-symbol"));
		base.setData(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), CustomerCreationTestData.TC128_CustomerSearch);
		Thread.sleep(2000);
		base.pressKey(NormalSaleGoldAndSilverObj.Ele_Name("NavigationSearchBox_searchBoxInput_input"), "ENTER");
		Thread.sleep(4000);
		base.ClickCondition(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"));
		Thread.sleep(6000);	

		//Filter Customerid
		base.setData(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input"), NewCustomerId);
		Thread.sleep(10000);
		base.ClickCondition(CustomerCreationObj.Ele_ERPAccField("custtablelistpage", "Account"));
		Thread.sleep(10000);
		driver.findElement(NormalSaleGoldAndSilverObj.Ele_WarehouseName("QuickFilterControl_Input")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);

		//Open Account
		base.actionClick(CustomerCreationObj.Ele_ERPCustid("Account"));
		base.setZoom(driver, 50);
		Thread.sleep(2000);
		if (base.isPresent(CustomerCreationObj.Ele_General("TabGeneral_caption","false")))
		{
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("TabGeneral_caption"));  
		}
		//Getting Required Data from ERP Customer Page
		String ERPFirstName   = base.GetText(CustomerCreationObj.Ele_LangID("Name_FirstName"));
		String ERPLastName    = base.GetText(CustomerCreationObj.Ele_LangID("Name_LastName"));
		String ERPReceiptLang = base.GetText(CustomerCreationObj.Ele_LangID("Person_LanguageId"));
		String ERPReligion    = base.GetText(CustomerCreationObj.Ele_LangID("CustTable_MGDReligionCode"));
		String ERPPrefLang    = base.GetText(CustomerCreationObj.Ele_LangID("CustTable_MGDPreferredlanguage"));

		Thread.sleep(5000);

		//Getting Phone number to Contact Information
		//base.scrollToElement(CustomerCreationObj.Ele_ERPCustomerAgeGroupCode("Preferred language"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("TabCommunication_caption"));
		base.scrollToElement(NormalSaleGoldAndSilverObj.Btn_return("Contact information"));
		if (base.isPresent(CustomerCreationObj.Ele_General("TabCommunication_caption","false")))
		{
			base.excuteJsClick(NormalSaleGoldAndSilverObj.Btn_MenuBarOptions("TabCommunication_caption"));  
		}
		Thread.sleep(3000);
		//String ERPphoneNumber = base.GetAttribte(CustomerCreationObj.Ele_ERPCustid("Contact number/address"), "value");
		String ERPphoneNumber = base.GetValue(CustomerCreationObj.Ele_ErpEmailContact("Phone"));
		Thread.sleep(5000);

		asrt.assertEquals(ExpectedCustomerFirstName,ERPFirstName, "First Name from POS Customer Page "+ExpectedCustomerFirstName+" is not match with First name in ERP "+ERPFirstName+"");
		asrt.assertEquals(ExpectedCustomerLastName,ERPLastName, "Last Name from POS Customer Page"+ExpectedCustomerLastName+"is not match with Last name in ERP page"+ERPLastName+"");
		asrt.assertEquals(ERPactualReceiptLangCode,ERPReceiptLang,"Receipt language from POS Customer Page"+ERPactualReceiptLangCode+"is not match  with the Receipt language in ERP Customer Page"+ERPReceiptLang+"");
		asrt.assertEquals(ERPactualReligionCode,ERPReligion,"Religion Code in POS Customer page"+ERPactualReligionCode+"is not match with Religion code in ERP Customer Page"+ERPReligion+"");
		asrt.assertEquals(ERPactualPrefLangCode,ERPPrefLang,"Preferd Language in POS Customer Page"+ERPactualPrefLangCode+"is not match with the Prefered Langauge in ERP Customer Page"+ERPPrefLang+"");
		asrt.assertEquals(ExpectedCustomerPhoneNumber,ERPphoneNumber,"Phone Number in POS Customer Page"+ExpectedCustomerPhoneNumber+"is not match with Phone number in ERP Customer Page"+ERPphoneNumber+"");

		System.out.println(" Customer data successfully validated in ERP ");

	}


}