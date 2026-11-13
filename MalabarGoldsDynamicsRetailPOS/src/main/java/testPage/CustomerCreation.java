
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



	
	//<summary>
		// Test Case Title : Creating retail customer with existing mobile number
		// Automation ID : TC129
		// Author : Aiswarya
		// </summary>
		public void TC129_CustomerCreation() throws Exception
		{
			//Step 1: Login to POS
			login.loginToApplication(CommonData.UserName1, CommonData.PassWord1);
			base.setZoom(driver, 60);		
			appUtils.HamBurgerButtonClick("iconShop");
	 
			//Step 2: Click on the Transaction Button
			//Step 3: Click on add customer
			//Step 4:Click on create new customer
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("addCustomerButton"));
			Thread.sleep(3000);
			base.buttonClick(LoginPageObj.Btn_Login("Create new customer"));
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerFirstNameInput"),CustomerCreation_TestData.TC129_FirstName.toUpperCase());
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerLastNameInput"),CustomerCreation_TestData.TC129_LastName.toUpperCase());
			base.scrollToElement(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
	 
			//Step 5: Enter Exsiting customer Mobile Number
			base.buttonClick(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"));
			base.setData(NormalSaleGoldAndSilverObj.Ele_Name("customerPhoneNumberInput"),UtilityTestData.TC129_PhoneNumber); // Mobile Number:The phone number being added is already associated with an existing Retail customer
			System.out.print("TC129_PhoneNumber");
			base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("customerLanguageOptions"),CustomerCreation_TestData.TC129_CustomerLanguageOptions);
			base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("RegionCode"),CustomerCreation_TestData.TC129_RegionCode);
			base.scrollToElement(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"));
			base.selectorByVisibleText(NormalSaleGoldAndSilverObj.Sel_DepositType("PreferredLanguage"),CustomerCreation_TestData.TC129_PreferredLanguage);
			Thread.sleep(3000);
			base.excuteJsClick(LoginPageObj.Btn_SingnIn("cmdSaveCustomerDetails"));
	 
			//Step 6: Click save button and check if validation appears saying mobile number already exists.
			//Expected: Should not be able to create new customer
			asrt.assertTrue(base.isExists(LoginPageObj.Ele_ErrorMessage("h4 wrapTextWithoutHyphen wrapTextWithLineBreaks")),"Validation saying 'Mobile number already exists' is not displayed ");
	 
		}


}