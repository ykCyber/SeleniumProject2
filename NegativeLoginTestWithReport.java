package day14_extent_reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utilities.Driver;
import utilities.configurationReader;

import java.util.concurrent.TimeUnit;

public class NegativeLoginTestWithReport extends TestBase{
    @Test
    public void wrongPasswordTest(){
        //name of the test
        extentLogger = report.createTest("Wrong Password Test");

        LoginPage loginPage = new LoginPage();

        extentLogger.info("Enter Username: user1");
        loginPage.usernameInput.sendKeys("user1");

        extentLogger.info("Enter Password: somepassword");
        loginPage.passwordInput.sendKeys("somepassword");
        extentLogger.info("Click login Button");
        loginPage.loginBtn.click();

        extentLogger.info("Verify Page url");
        Assert.assertEquals(driver.getCurrentUrl(),"https://qa1.vytrack.com/user/login");

        extentLogger.pass("Wrong Password Test is passed");
    }

    @Test
    public void wrongUserNameTest(){
        extentLogger = report.createTest("Wrong Username Test");
        LoginPage loginPage = new LoginPage();
        extentLogger.info("Enter: username");
        loginPage.usernameInput.sendKeys("someusername");
        extentLogger.info("Enter Password: UserUser123");
        loginPage.passwordInput.sendKeys("UserUser123");
        extentLogger.info("Click login Button");
        loginPage.loginBtn.click();
        extentLogger.info("verify page url");
        Assert.assertEquals(driver.getCurrentUrl(),"https://qa1.vytrack.com/user/login");
        extentLogger.pass("PASSED");
    }

}
class TestBase{
    protected WebDriver driver;
    protected ExtentReports report;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentTest extentLogger;
    @BeforeTest
    public void setUpTest(){
        //initialize the class
        report = new ExtentReports();
        //create a report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath+"/test-output/report.html";
        //initialize the html reporter with the report path
        htmlReporter = new ExtentHtmlReporter(path);
        //attach the html report object
        report.attachReporter(htmlReporter);
        //title in report
        htmlReporter.config().setReportName("Vytrack Smoke Test");
        //set environment information
        report.setSystemInfo("Environment","QA");
        report.setSystemInfo("Browser", configurationReader.get("browser"));
        report.setSystemInfo("OS",System.getProperty("os.name"));
    }
    @BeforeMethod
    public void setUp(){
        driver = Driver.get();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterMethod
    public void tearDown()throws InterruptedException{
        Thread.sleep(2000);
        Driver.closeDriver();
    }
    @AfterTest
    public void tearDownTest(){
        report.flush();
    }
}