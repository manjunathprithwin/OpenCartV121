package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBases.BaseClass;
import utilities.DataProviders;


public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups="DataDriven")
	public void verify_loginDDT(String email, String pwd, String exp) {
	    try {
	        // HomePage
	        HomePage hp = new HomePage(driver);
	        hp.clickMyaccount();
	        hp.clickLogin();

	        // LoginPage
	        LoginPage lp = new LoginPage(driver);
	        lp.setEmail(email);
	        lp.setPassword(pwd);
	        lp.clickLogin();

	        // MyAccountPage
	        MyAccountPage macc = new MyAccountPage(driver);

	        boolean targetPage = macc.isMyAccountPageExists();

	        /*
	         * Data is valid - login success - test pass - logout
	         * Data is valid - login failed - test fail
	         * Data is invalid - login success - test fail - logout
	         * Data is invalid - login failed - test pass
	         */
	        if (exp.equalsIgnoreCase("Valid")) {
	            if (targetPage) {
	                macc.logout();
	                Assert.assertTrue(true, "Login success for valid data.");
	            } else {
	                Assert.fail("Login failed for valid data.");
	            }
	        } else if (exp.equalsIgnoreCase("Invalid")) {
	            if (!targetPage) {
	                Assert.assertTrue(true, "Login failed for invalid data as expected.");
	            } else {
	                macc.logout();
	                Assert.fail("Login success for invalid data.");
	            }
	        } else {
	            Assert.fail("Unexpected value for 'exp' parameter: " + exp);
	        }

	    } catch (Exception e) {
	        Assert.fail("Test encountered an exception: " + e.getMessage());
	    }
	}}
