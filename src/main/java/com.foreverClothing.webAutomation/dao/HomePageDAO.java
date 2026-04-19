package com.kpmg.webAutomation.dao;

import com.aventstack.extentreports.ExtentTest;
import com.kpmg.webAutomation.controllers.DriverManager;
import com.kpmg.webAutomation.controllers.SetUpTest;
import com.kpmg.webAutomation.objectRepository.projectTask.HomePageLocators;
import com.kpmg.webAutomation.utils.CommonUtilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.time.Duration;

public class HomePageDAO extends SetUpTest {
    private static final Log log = LogFactory.getLog(HomePageDAO.class);
    private WebDriver driverInstance;
    private HomePageLocators homeLocatorsPage;
    private CommonUtilities commonUtilities;
    private ExtentTest extLogger;
    private String scenario;
    WebDriverWait explicitWait;
    public HomePageDAO(String scenario) {
        super();
        this.driverInstance = DriverManager.getDriver();
        log.info("DAO DRIVER: "+DriverManager.getDriver());
        this.homeLocatorsPage = PageFactory.initElements(driverInstance, HomePageLocators.class);
        this.commonUtilities = new CommonUtilities();
        explicitWait = new WebDriverWait(driverInstance, Duration.ofSeconds(30));
        this.scenario = scenario;
        this.extLogger = (ExtentTest) Reporter.getCurrentTestResult().getTestContext()
                .getAttribute("extLogger" + Thread.currentThread().hashCode());
    }

    public void enterUserData() throws Exception {
        commonUtilities.type(homeLocatorsPage.btnName,"Entering Name","Ram Polaveni");
        commonUtilities.type(homeLocatorsPage.btnPassword,"Entering Password","ram123");
        commonUtilities.type(homeLocatorsPage.btnPhoneNumber,"Entering Phone Number","0404441536");
        commonUtilities.type(homeLocatorsPage.btnAddress,"Entering Address","1 Delay Lane, Craigieburn VIC 3064");
        commonUtilities.click(homeLocatorsPage.btnGender);
    }

    public void enterUserDataTwo() throws Exception {
        commonUtilities.type(homeLocatorsPage.btnName,"Entering Name","Ram Polaveni");
        commonUtilities.type(homeLocatorsPage.btnPassword,"Entering Password","ram123");
        commonUtilities.type(homeLocatorsPage.btnPhoneNumber,"Entering Phone Number","0404441536");
        commonUtilities.type(homeLocatorsPage.btnAddress,"Entering Address","1 Delay Lane, Craigieburn VIC 3064");
        commonUtilities.click(homeLocatorsPage.btnFail);
    }
}
