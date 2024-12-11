package testBases;
// Defines the package where the class belongs, helping in organizing the code.

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
// Imports Java utility and IO classes needed for file reading, date formatting, and handling properties.

import org.apache.commons.lang3.RandomStringUtils;
// Importing a utility for generating random strings and numbers.

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
// Log4j classes for logging messages during the execution of the code.

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
// Selenium WebDriver and related classes for browser automation.

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
// Selenium-specific browser drivers for Chrome, Edge, and Firefox.

import org.openqa.selenium.remote.DesiredCapabilities;
// For setting browser and OS capabilities in remote execution.
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
// TestNG annotations for specifying setup and teardown methods, and passing parameters.

public class BaseClass {
// Defines a public class named `BaseClass`.

    // A static WebDriver instance to control the browser across the class.
    public static WebDriver driver;

    // Logger instance for recording logs for debugging or tracking.
    public Logger logger;

    // Properties object to load and access configuration values.
    public Properties p;

    @BeforeClass(groups={"Sanity","Regression","Master"})
    @Parameters({"os","browser"})
    // Setup method executed before test class, taking OS and browser as parameters.
    public void setUp(String os, String br) throws IOException {

        // To get the configuration data
        FileReader fileReader = new FileReader("./src//test//resources//config.properties");

        // Loads the properties from the file into the `p` object.
        p = new Properties();
        p.load(fileReader);

        // Initializes the logger for the current class.
        logger = LogManager.getLogger(this.getClass());

        // Checks if the execution environment is set to remote.
        if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {

            // Creates a new DesiredCapabilities object to define browser and OS settings.
            DesiredCapabilities capabilities = new DesiredCapabilities();

            // Sets the platform based on the OS parameter.
            if(os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN10);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            }else if (os.equalsIgnoreCase("Linux")) {
                capabilities.setPlatform(Platform.LINUX);}
            else {
                System.out.println("No matching os");
                return;
            }

            // Configures the browser name for remote execution.
            switch(br.toLowerCase()) {
                case "chrome": capabilities.setBrowserName("chrome"); break;
                case "edge": capabilities.setBrowserName("edge"); break;
                default: System.out.println("no matching browser"); return;
            }
            driver=new RemoteWebDriver(new URL(" http://localhost:4444"), capabilities);
        }

        // Initializes the WebDriver instance for the specified browser.
        if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
        switch (br.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser name");
                return;
        }
        
        }

        // Deletes all cookies from the browser.
        driver.manage().deleteAllCookies();

        // Sets an implicit wait time of 10 seconds for locating elements.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Opens the URL specified in the properties file.
        driver.get(p.getProperty("appURL1"));

        // Maximizes the browser window.
        driver.manage().window().maximize();
    }

    @AfterClass(groups={"Sanity","Regression","Master"})
    // Closes the browser and ends the WebDriver session after tests are completed.
    public void tearDown() {
        driver.quit();
    }

    // Generates a random alphabetic string of 5 characters.
    public String randomeString() {
        String generatedstring = RandomStringUtils.randomAlphabetic(5);
        return generatedstring;
    }

    // Generates a random numeric string of 10 digits.
    public String randomeNumber() {
        String generatedNumber = RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }

    // Generates a password with a random 3-digit number, "@" symbol, and a 3-character alphabetic string.
    public String randomePassword() {
        String generatedNumber = RandomStringUtils.randomNumeric(3);
        String generatedString = RandomStringUtils.randomAlphabetic(3);
        return (generatedNumber + "@" + generatedString);
    }

    // Captures a screenshot of the current browser window and saves it as a file.
    public String captureScreen(String tname) throws IOException {

        // Captures the current date and time in the specified format.
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        // Takes a screenshot of the current browser window and saves it as a file.
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        // Defines the path and filename for the screenshot.
        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        // Renames or moves the source screenshot file to the target location.
        sourceFile.renameTo(targetFile);

        // Returns the full path of the saved screenshot.
        return targetFilePath;
    }
}
