package com.kpmg.webAutomation.common;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.kpmg.webAutomation.controllers.DriverClass;
import com.kpmg.webAutomation.controllers.DriverManager;
import com.kpmg.webAutomation.controllers.SetUpTest;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import java.time.Duration;
import java.util.HashMap;


public class Listeners implements ISuiteListener, ITestListener, IInvokedMethodListener, IAnnotationTransformer {

    private ExtentTest extLogger;
    public int totalpassed, totaltcs, totalskipped, totalfailed;
    Logger log = Log4jUtil.loadLogger(Listeners.class);

    public void onStart(ISuite suite) {
        SetUpTest.suiteName = suite.getName();
        totalpassed = 0;
        totaltcs = 0;
        totalskipped = 0;
        totalfailed = 0;
    }

    public void onFinish(ISuite suite) {
        log.info("Total test cases   :" + (totalpassed + totalskipped + totalfailed));
        log.info("Total passed cases :" + totalpassed);
        log.info("Total failed cases :" + totalfailed);
        log.info("Total skipped cases:" + totalskipped);
    }
    public void onStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
    }

    public void onFinish(ITestContext test) {
        log.info("All Tests Execution Completed");
    }

    public synchronized void onTestStart(ITestResult test) {
        try {
            totaltcs++;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized void onTestSuccess(ITestResult test) {
        totalpassed++;
        extLogger.log(Status.PASS, "Test case passed: " + test.getName());
    }

    public synchronized void onTestFailure(ITestResult test) {
        log.info("Test case failed: " + test.getName() + " --- Exception : " + test.getThrowable());
        extLogger.log(Status.FAIL, "Test case failed: " + test.getName() + " --- Exception : " + test.getThrowable());
        test.getThrowable().printStackTrace();
        totalfailed++;
    }

    public synchronized void onTestSkipped(ITestResult test) {
        log.info("Listener If test case skipped:" + test.getName() + " --- Exception : " + test.getThrowable());
        extLogger.log(Status.SKIP, "Test case skipped: " + test.getName() + " --- Exception : " + test.getThrowable());
        test.getThrowable().printStackTrace();
        totalskipped++;
    }


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
            log.info("Browser launched");
            SetUpTest.scenarioName = method.getTestMethod().getMethodName();
            Reporter.getCurrentTestResult().getTestContext()
                    .setAttribute("methodName" + Thread.currentThread().hashCode(), SetUpTest.scenarioName);
            SetUpTest.blnPortFlag = false;
            Reporter.getCurrentTestResult().getTestContext()
                    .setAttribute("portFlag" + Thread.currentThread().hashCode(), SetUpTest.blnPortFlag);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (DriverManager.getDriver() != null) {
                log.info("Thread id = " + Thread.currentThread().threadId());
                log.info("Hashcode of webDriver instance = " + DriverManager.getDriver().hashCode());
                this.extLogger = (ExtentTest) Reporter.getCurrentTestResult().getTestContext()
                        .getAttribute("extLogger" + Thread.currentThread().hashCode());
            }
        }
    }

}
