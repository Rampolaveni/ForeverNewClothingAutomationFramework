package com.kpmg.webAutomation.controllers;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.kpmg.webAutomation.common.Log4jUtil;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SetUpTest extends DriverManager {

    protected static Logger log = Log4jUtil.loadLogger(SetUpTest.class);
    public static String strUrlVal;
    protected static String strEnv;
    protected static Properties props;
    protected static String path;
    protected static String UTILS_FILE_PATH;
    protected static String reportPath;
    protected static String successimagespath;
    protected static String failureimagespath;
    public static String suiteName;
    public static String strClassName;
    public static String strFlow;
    public static String scenarioName;
    public static boolean blnPortFlag;

    @BeforeMethod(alwaysRun = true)
    public void startOfTest(ITestContext context) throws IOException {
        log.info("**************** START TEST ****************");
        strUrlVal = getURL(strEnv);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestContext context){
        ExtentTest extLogger;
        extLogger = (ExtentTest) Reporter.getCurrentTestResult().getTestContext()
                .getAttribute("extLogger" + Thread.currentThread().hashCode());

        ExtentReports extentReport = (ExtentReports) Reporter.getCurrentTestResult().getTestContext()
                .getAttribute("extentReport" + Thread.currentThread().hashCode());
        extentReport.flush();
        DriverManager.getDriver().quit();
    }

    @BeforeSuite(alwaysRun = true)
    public void launchApplication() throws Exception {
        setPath();
        try {
            strClassName = this.getClass().getSimpleName();
            strFlow = strClassName;
            strFlow = this.getClass().getSimpleName().replaceAll("Test.*", "").trim().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getURL(String env) throws FileNotFoundException, IOException {
        Properties envProps = new Properties();
        if (env.equalsIgnoreCase("PROD")) {
            envProps.load(new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/webConfig/environment.properties"));
            strUrlVal = envProps.getProperty("PROD");
        }
        else if (env.equalsIgnoreCase("qa")) {
            envProps.load(new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/webConfig/environment.properties"));
            strUrlVal = envProps.getProperty("qa");
        }
        else if (env.equalsIgnoreCase("UAT")) {
            envProps.load(new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/webConfig/environment.properties"));
            strUrlVal = envProps.getProperty("UAT");
        }
        else if (env.equalsIgnoreCase("GOOGLE")) {
            envProps.load(new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/webConfig/ENV.properties"));
            strUrlVal = envProps.getProperty("GOOGLE");
        }
        else {
            strUrlVal = "https://samplewebautomation.com/";
        }
        return strUrlVal;
    }

    private void setPath() {

        props = new Properties();
        path = System.getProperty("user.dir");

        try {
            UTILS_FILE_PATH = path + "/src/main/resources/webConfig/environment.properties";
            props.load(new FileInputStream(UTILS_FILE_PATH));
            reportPath = props.getProperty("reportPath");
            successimagespath = path + props.getProperty("successimagespath");
            failureimagespath = path + props.getProperty("failureimagespath");
            strEnv = getEnv();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getEnv() {

        String env = System.getProperty("TestEnv");

        if (env == null) {
            env = props.getProperty("defaultenv");
        }
        else log.info("Environment: " + env);
        return env.toLowerCase();
    }

}
