package com.kpmg.webAutomation.dao;

import com.kpmg.webAutomation.controllers.DriverManager;
import com.kpmg.webAutomation.controllers.SetUpTest;
import com.kpmg.webAutomation.objectRepository.projectTask.HomePageLocators;
import com.kpmg.webAutomation.utils.CommonUtilities;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePageDAO extends SetUpTest {
    private static final Log log = LogFactory.getLog(HomePageDAO.class);
    private WebDriver driverInstance;
    private HomePageLocators homeLocatorsPage;
    private CommonUtilities commonUtilities;
    WebDriverWait explicitWait;
    public HomePageDAO() {
        this.driverInstance = DriverManager.getDriver();
        log.info("DAO DRIVER: "+DriverManager.getDriver());
        this.homeLocatorsPage = PageFactory.initElements(driverInstance, HomePageLocators.class);
        this.commonUtilities = new CommonUtilities();
        explicitWait = new WebDriverWait(driverInstance, Duration.ofSeconds(30));
    }

    public void enterUserData() throws Exception {
        commonUtilities.type(homeLocatorsPage.btnName,"Entering Name","Ram Polaveni");
        commonUtilities.type(homeLocatorsPage.btnPassword,"Entering Password","ram123");
        commonUtilities.type(homeLocatorsPage.btnPhoneNumber,"Entering Phone Number","0404441536");
        commonUtilities.type(homeLocatorsPage.btnAddress,"Entering Address","1 Delay Lane, Craigieburn VIC 3064");
        commonUtilities.click(homeLocatorsPage.btnGender);
    }
}
