package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBases.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"})
	public void verify_Login() {
		
		logger.info("starting TC002_LoginTest");
		try {
		HomePage homePage = new HomePage(driver);
		homePage.clickMyaccount();
		homePage.clickLogin();
		
		LoginPage loginpage = new LoginPage(driver);
		loginpage.setEmail(p.getProperty("email"));
		loginpage.setPassword(p.getProperty("password"));
		loginpage.clickLogin();
		
				MyAccountPage myAccountPage = new MyAccountPage(driver);
				boolean myAccountPageExists = myAccountPage.isMyAccountPageExists();
				
				Assert.assertEquals(myAccountPageExists, true,"login failed");
		}catch(Exception e) {
		Assert.fail();
		}
				logger.info("finsih TC002_LoginTest");
	}

}
