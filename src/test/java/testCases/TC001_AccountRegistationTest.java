package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBases.BaseClass;

public class TC001_AccountRegistationTest extends BaseClass {

  

    @Test(groups ={"Regression","Master"})
    public void verify_account_registration() {
        logger.info("application is starting");
     
try {
        // Navigate to the home page
        HomePage homepage = new HomePage(driver);
        homepage.clickMyaccount();
        homepage.clickRegistartion();

        // Fill out the registration form
        AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
        accountRegistrationPage.setFirstName(randomeString().toUpperCase());
        accountRegistrationPage.setLastName(randomeString().toUpperCase());
        accountRegistrationPage.setEmail(randomeString()+"@gmail.com"); // Use the generated email
        String pwd= randomePassword();
        
        accountRegistrationPage.setPassword(pwd);
        accountRegistrationPage.setConfirmPassword(pwd);
        accountRegistrationPage.setTelephone(randomeNumber());
        accountRegistrationPage.setPrivacyPolicy();
        accountRegistrationPage.clickContinue();

        // Get the confirmation message and validate
        String confirmationMsg = accountRegistrationPage.getConfirmationMsg();
        Assert.assertEquals(confirmationMsg, "Your Account Has Been Created!");
    }catch(Exception e) {
    	logger.error("test failed");
    	logger.debug("debug logs..");
    	Assert.fail();}logger.info("finish TC001_AccountRegistationTest");
    	}
    
    

    // Static method to generate a random Gmail ID
    public String randomeString() {
        String generatedstring=RandomStringUtils.randomAlphabetic(5);
        return generatedstring;
    }
    
    public String randomeNumber() {
        String generatedNumber=RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }
    
    public String randomePassword() {
        String generatedNumber=RandomStringUtils.randomNumeric(3);
        String generatedString=RandomStringUtils.randomAlphabetic(3);
        
        return (generatedNumber+"@"+generatedString);
    }
    }
    
