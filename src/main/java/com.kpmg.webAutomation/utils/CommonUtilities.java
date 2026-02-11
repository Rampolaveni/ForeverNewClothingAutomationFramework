package com.kpmg.webAutomation.utils;

import com.kpmg.webAutomation.common.Log4jUtil;
import com.kpmg.webAutomation.controllers.DriverManager;
import com.kpmg.webAutomation.controllers.SetUpTest;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.testng.AssertJUnit.assertTrue;

public class CommonUtilities extends SetUpTest {

    Logger log = Log4jUtil.loadLogger(CommonUtilities.class);
    protected WebDriver driverInstance = DriverManager.getDriver();

    public WebDriverWait driverWait() {
        WebDriverWait explicitWait = new WebDriverWait(driverInstance, Duration.ofSeconds(30));
        return explicitWait;
    }

    public boolean isElementPresent(WebElement ele) {
        try {
            driverWait().until(ExpectedConditions.visibilityOf(ele));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean click(WebElement locator) throws Exception {
        boolean blnVal = false;
        try {
            driverWait().until(ExpectedConditions.elementToBeClickable(locator));
            if (locator.isDisplayed()) {
                locator.click();
                log.info("Clicked on element :" + locator);
                blnVal = true;
            }

        } catch (Exception e) {
            log.info("Unable to click on element :" + locator);
            e.printStackTrace();
            assertTrue(blnVal);
            throw (e);
        }
        return blnVal;
    }

    public boolean type(WebElement locator, String message, String strData) throws Exception {
        boolean blnVal = false;
        try {
            driverWait().until(ExpectedConditions.elementToBeClickable(locator));
            if (locator.isDisplayed()) {
                if (locator.getText() != null || !locator.getText().equalsIgnoreCase("")) {
                    locator.clear();
                }
                locator.sendKeys(strData);
                log.info("Text entered in the textbox is: " + strData);
                blnVal = true;
            }
        } catch (RuntimeException localRuntimeException) {
            log.info("Unable to Enter the value in the Textbox :" + strData);
            throw (localRuntimeException);
        }
        return blnVal;
    }
}
