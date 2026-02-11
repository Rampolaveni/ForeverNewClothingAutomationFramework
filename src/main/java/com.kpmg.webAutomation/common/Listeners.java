package com.kpmg.webAutomation.common;

import com.kpmg.webAutomation.controllers.DriverClass;
import com.kpmg.webAutomation.controllers.DriverManager;
import com.kpmg.webAutomation.controllers.SetUpTest;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import java.time.Duration;


public class Listeners implements ISuiteListener, ITestListener, IInvokedMethodListener, IAnnotationTransformer {

    Logger log = Log4jUtil.loadLogger(Listeners.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            String browserName = System.getProperty("browser").toLowerCase();
            WebDriver driver = DriverClass.createInstance(browserName);
            DriverManager.setDriver(driver);
            WebDriver driverInstance = DriverManager.getDriver();
            driverInstance.manage().deleteAllCookies();
            driverInstance.manage().window().maximize();
            driverInstance.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driverInstance.get(SetUpTest.strUrlVal);
            log.info("Browser launched + URL opened");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }
}
